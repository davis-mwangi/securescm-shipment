/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.service.Impl;

import com.securescm.shipment.entities.AssignTransporterRequest;
import com.securescm.shipment.entities.Country;
import com.securescm.shipment.entities.Provider;
import com.securescm.shipment.entities.Shipment;
import com.securescm.shipment.entities.ShipmentItem;
import com.securescm.shipment.entities.ShipmentStatus;
import com.securescm.shipment.entities.Transaction;
import com.securescm.shipment.entities.Transporter;
import com.securescm.shipment.model.ItemName;
import com.securescm.shipment.model.ShipmentModel;
import com.securescm.shipment.model.Status;
import com.securescm.shipment.model.UserModel;
import com.securescm.shipment.payload.ApproveTransporterRequest;
import com.securescm.shipment.payload.ShipmentRequest;
import com.securescm.shipment.repos.ShipmentDao;
import com.securescm.shipment.repos.ShipmentItemDao;
import com.securescm.shipment.repos.TransactionDao;
import com.securescm.shipment.service.ShipmentService;
import com.securescm.shipment.util.AppConstants;
import com.securescm.shipment.util.PagedResponse;
import com.securescm.shipment.util.Response;
import com.securescm.shipment.util.SingleItemResponse;
import com.securescm.shipment.util.Util;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author david
 */
@Service
public class ShipmentServiceImpl implements ShipmentService {

    Logger log = LoggerFactory.getLogger(ShipmentServiceImpl.class);
    @Autowired
    private ShipmentDao shipmentDao;

    @Autowired
    private ShipmentItemDao shipmentItemDao;

    @Autowired
    private TransactionDao transactionDao;

    @Override
    public SingleItemResponse createUpdateShipment(ShipmentRequest request, UserModel userModel) {
        Status status;
        Date now = new Date();
        Shipment shipment = new Shipment();
        Transaction transaction = new Transaction();
        SingleItemResponse singleItemResponse;

        //Generate unique code for new shipment record
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString().toUpperCase();

        String transactionName = randomUUIDString.replace("-", "");
        shipment.setCode(randomUUIDString);
        if (request.getId() != null) {
            shipment = shipmentDao.getOneShipment(request.getId());
            shipment.setCode(request.getCode());
        }

        //Generate Transaction Name
        transaction.setName(transactionName);
        transaction = transactionDao.save(transaction);

        shipment.setTransaction(transaction);

        shipment.setStatus(new ShipmentStatus(AppConstants.OPEN));
        shipment.setShipmentDate(request.getShipmentDate());

        shipment.setRegion(request.getRegion());
        shipment.setAddress(request.getAddress());
        shipment.setCity(request.getCity());
        shipment.setPostalcode(request.getPostalcode());
        shipment.setCountry(new Country(request.getCountry()));

        shipment.setName(request.getName());
        shipment.setShipmentWeight(request.getShipmentWeight());

        shipment.setFreight(request.getFreight());

        shipment.setCreatedBy(new Provider(userModel.getStakeholder().getId()));
        shipment.setDateLastUpdated(now);
        shipment.setDateCreated(now);

        shipmentDao.save(shipment);
        ItemName item = new ItemName(shipment.getId(), shipment.getName());
        singleItemResponse = new SingleItemResponse(Response.SUCCESS.status(), item);
        return singleItemResponse;

    }

    //Soft delete Shipment
    @Override
    public SingleItemResponse deleteShipment(Integer id) {
        Status status;
        SingleItemResponse singleItemResponse;
        Shipment shipment = shipmentDao.findByIdAndDateDeletedIsNull(id);
        if (shipment != null) {
            shipment.setDateDeleted(new Date());
            shipmentDao.save(shipment);
            singleItemResponse = new SingleItemResponse(Response.SUCCESS.status(), null);
        } else {
            singleItemResponse = new SingleItemResponse(Response.SHIPMENT_NOT_FOUND.status(),
                    null);
        }
        return singleItemResponse;
    }

    //Paged response
    @Override
    public PagedResponse<ShipmentModel> getAllShipments(UserModel userModel, String direction, String orderBy, int page, int size) {
        Pageable pageable = Util.getPageable(page, size, direction, orderBy);

        List<ShipmentItem> shipmentItems = null;
        if (userModel.getStakeholder().getType().getName().equalsIgnoreCase("Provider")
                && userModel.getRole().getName().equalsIgnoreCase("Provider")) {
            Page<Shipment> ships = shipmentDao.findByCreatedByAndDateDeletedIsNull(
                    new Provider(userModel.getStakeholder().getId()), pageable);

            List<ShipmentModel> shipResponses = ships.map(shipment -> {
                List<ShipmentItem> shipItms = shipmentItemDao.findByShipment(shipment);
                return ShipmentModel.map(shipment, shipItms);
            }).getContent();

            return Util.getResponse(ships, shipResponses);
        }
        if (userModel.getRole().getName().equalsIgnoreCase("Transporter")) {
            Page<Shipment> ships = shipmentDao.findByTransporterAndDateDeletedIsNull(
                    new Transporter(userModel.getStakeholder().getId()),
                     pageable);

            List<ShipmentModel> shipResponses = ships.map(shipment -> {
                List<ShipmentItem> shipItms = shipmentItemDao.findByShipment(shipment);
                return ShipmentModel.map(shipment, shipItms);
            }).getContent();

            return Util.getResponse(ships, shipResponses);
        }
        if (userModel.getStakeholder().getType().getName().equalsIgnoreCase("Provider")
                && userModel.getRole().getName().equalsIgnoreCase("SecurityAgent")) {
            shipmentItems = shipmentItemDao.findByProviderAndStatus1(userModel.getStakeholder().getId());

        } else if (userModel.getStakeholder().getType().getName().equalsIgnoreCase("Retailer")
                && userModel.getRole().getName().equalsIgnoreCase("SecurityAgent")) {
            shipmentItems = shipmentItemDao.findByRetailerAndStatus2(userModel.getStakeholder().getId());

        } else if (userModel.getStakeholder().getType().getName().equalsIgnoreCase("Retailer")
                && userModel.getRole().getName().equalsIgnoreCase("Retailer")) {
            shipmentItems = shipmentItemDao.findByRetailerAndStatus2(userModel.getStakeholder().getId());
        }
        //Get all shipment ids for user{retailer/provider} 

        List<Integer> shipmentIds = new ArrayList<>();
        for (ShipmentItem shipItem : shipmentItems) {
            shipmentIds.add(shipItem.getShipment().getId());
        }

        //Make list unique
        ArrayList<Integer> uniqueShipmentIds = (ArrayList) shipmentIds.stream().distinct().collect(Collectors.toList());

        List<ShipmentModel> shipments = new ArrayList<>();
        for (Integer shipmentId : uniqueShipmentIds) {

            Shipment shipment = shipmentDao.findByIdAndDateDeletedIsNull(shipmentId);
            if (shipment.getStatus().getId() == 9 && userModel.getStakeholder().getType().getName().equalsIgnoreCase("Retailer")
                && userModel.getRole().getName().equalsIgnoreCase("SecurityAgent")) {
                List<ShipmentItem> shipItms = new ArrayList<>();
                for (ShipmentItem shipItem : shipmentItems) {
                    if (shipment.getId() == shipItem.getShipment().getId()) {
                        shipItms.add(shipItem);
                    }

                }
                shipments.add(ShipmentModel.map(shipment, shipItms));
            } else if(userModel.getStakeholder().getType().getName().equalsIgnoreCase("Provider")
                && userModel.getRole().getName().equalsIgnoreCase("SecurityAgent")){
                List<ShipmentItem> shipItms = new ArrayList<>();
                for (ShipmentItem shipItem : shipmentItems) {
                    if (shipment.getId() == shipItem.getShipment().getId()) {
                        shipItms.add(shipItem);
                    }

                }
                shipments.add(ShipmentModel.map(shipment, shipItms));
            }

        }
        //List for security agents retailer

        Page<ShipmentModel> shipmentResponses = toPage(shipments, pageable);

        return Util.getResponse(shipmentResponses, shipments);

    }

    private Page toPage(List<ShipmentModel> list, Pageable pageable) {
        if (pageable.getOffset() >= list.size()) {
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size()
                ? list.size()
                : pageable.getOffset() + pageable.getPageSize());
        List subList = list.subList(startIndex, endIndex);
        return new PageImpl(subList, pageable, list.size());
    }

    @Override
    public SingleItemResponse findOneShipment(UserModel userModel, Integer id) {
        Shipment shipment = shipmentDao.getOneShipment(id);
        List<ShipmentItem> shipmentItems = null;
        if (userModel.getStakeholder().getType().getName().equalsIgnoreCase("Provider")
                && userModel.getRole().getName().equalsIgnoreCase("SecurityAgent")) {
            shipmentItems = shipmentItemDao.findByProviderAndStatus1(userModel.getStakeholder().getId());

        } else if (userModel.getStakeholder().getType().getName().equalsIgnoreCase("Retailer")
                && userModel.getRole().getName().equalsIgnoreCase("SecurityAgent")) {
            shipmentItems = shipmentItemDao.findByRetailerAndStatus2(userModel.getStakeholder().getId());

        } else if (userModel.getStakeholder().getType().getName().equalsIgnoreCase("Retailer")
                && userModel.getRole().getName().equalsIgnoreCase("Retailer")) {
            shipmentItems = shipmentItemDao.findByRetailerAndStatus2(userModel.getStakeholder().getId());
        } else if (userModel.getStakeholder().getType().getName().equalsIgnoreCase("Provider")
                && userModel.getRole().getName().equalsIgnoreCase("Provider")) {
            shipmentItems = shipmentItemDao.findByProvider(new Provider(userModel.getStakeholder().getId()));
        }

        List<ShipmentItem> shipItms = new ArrayList<>();
        for (ShipmentItem shipItem : shipmentItems) {
            if (shipment.getId() == shipItem.getShipment().getId()) {
                shipItms.add(shipItem);
            }

        }

        return new SingleItemResponse(Response.SUCCESS.status(), ShipmentModel.map(shipment, shipItms));
    }

    // Asign traspoorter to a shipemnt
    @Override
    public SingleItemResponse assignTransporter(AssignTransporterRequest request) {
        Shipment shipment = new Shipment();
        if (request.getShipment() != null) {
            shipment = shipmentDao.getOneShipment(request.getShipment());
        }
        shipment.setStatus(new ShipmentStatus(AppConstants.PENDING));
        shipment.setTransporter(new Transporter(request.getTransporter()));

        shipmentDao.save(shipment);
        return new SingleItemResponse(Response.SUCCESS.status(), new ItemName(shipment.getId(), shipment.getStatus().getName()));
    }

    //Approve shipment 
    @Override
    public SingleItemResponse approveShipment(UserModel userModel, ApproveTransporterRequest request) {
        Shipment shipment;
        SingleItemResponse singleItemResponse = new SingleItemResponse();
        if (request.getShipment() != null && userModel.getRole().getName().equalsIgnoreCase("Transporter")) {
            shipment = shipmentDao.getOneShipment(request.getShipment());
            if (request.getStatus().equals("102")) {
                shipment.setStatus(new ShipmentStatus(AppConstants.ACCEPTED));
                shipment.setTransporter(new Transporter(userModel.getStakeholder().getId()));
            }

            if (request.getStatus().equals("103")) {
                shipment.setStatus(new ShipmentStatus(AppConstants.REJECTED));
                shipment.setTransporter(null);
            }

            shipmentDao.save(shipment);
            singleItemResponse = new SingleItemResponse(Response.SUCCESS.status(), new ItemName(shipment.getId(), shipment.getStatus().getName()));
        }

        return singleItemResponse;
    }

    //Close shipment(Shipments items added and transporter is assigned)
    @Override
    public SingleItemResponse closeShipment(Integer id) {
        Shipment shipment = new Shipment();
        if (id != null) {
            shipment = shipmentDao.getOneShipment(id);

        }
        shipment.setStatus(new ShipmentStatus(AppConstants.CLOSED));
        shipmentDao.save(shipment);
        return new SingleItemResponse(Response.SUCCESS.status(), new ItemName(shipment.getId(), null));

    }

}
