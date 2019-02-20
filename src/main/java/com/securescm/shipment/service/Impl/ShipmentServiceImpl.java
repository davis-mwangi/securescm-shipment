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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        shipment.setCode(randomUUIDString);
        String transactionName = randomUUIDString.replace("-", "");

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

        Status status = null;
        Sort sort = null;
        if (direction.equals("ASC")) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, orderBy));
        }
        if (direction.equals("DESC")) {
            sort = new Sort(new Sort.Order(Sort.Direction.DESC, orderBy));
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        // Retrieve shipments
        Page<Shipment> shipments = shipmentDao.findByCreatedByAndDateDeletedIsNull(
                new Provider(userModel.getStakeholder().getId()), pageable);

        if (shipments.getNumberOfElements() == 0) {
            return new PagedResponse<>(Response.SUCCESS.status(), Collections.emptyList(), shipments.getNumber(),
                    shipments.getSize(), shipments.getTotalElements(), shipments.getTotalPages(), shipments.isLast());
        }

        //Map Shipment to Shipment Response
        List<ShipmentModel> shipmentResponses = shipments.map(shipment -> {
            List<ShipmentItem> shipItems = new ArrayList<>();
            return ShipmentModel.map(shipment, shipItems);

        }).getContent();

        return new PagedResponse<>(Response.SUCCESS.status(), shipmentResponses, shipments.getNumber(),
                shipments.getSize(), shipments.getTotalElements(), shipments.getTotalPages(), shipments.isLast());
    }

    @Override
    public SingleItemResponse findOneShipment(Integer id, UserModel userModel) {
        Shipment shipment = shipmentDao.findByIdAndCreatedBy(id, new Provider(userModel.getStakeholder().getId()));
        List<ShipmentItem> shipItems = shipmentItemDao.findByShipment(new Shipment(id));
        return new SingleItemResponse(Response.SUCCESS.status(), ShipmentModel.map(shipment, shipItems));
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
        Shipment shipment = new Shipment();
        if (request.getShipment() != null) {
            shipment = shipmentDao.getOneShipment(request.getShipment());
            if (request.getStatus().equals("102")) {
                shipment.setStatus(new ShipmentStatus(AppConstants.ACCEPTED));
                shipment.setTransporter(new Transporter(userModel.getStakeholder().getId()));
            } else if (request.getStatus().equals("103")) {
                shipment.setStatus(new ShipmentStatus(AppConstants.REJECTED));
                shipment.setTransporter(null);
            }

            
            shipmentDao.save(shipment);
        }

        return new SingleItemResponse(Response.SUCCESS.status(), new ItemName(shipment.getId(), shipment.getStatus().getName()));
    }
    
    //Close shipment(Shipments items added and transporter is assigned)
    @Override
     public SingleItemResponse closeShipment(Integer id){
      Shipment shipment = new Shipment();
        if (id != null) {
            shipment = shipmentDao.getOneShipment(id);
          
        }
        shipment.setStatus(new ShipmentStatus(AppConstants.CLOSED));
        shipmentDao.save(shipment);
        return new SingleItemResponse(Response.SUCCESS.status(), new ItemName(shipment.getId(), null));
     
     }

}
