/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.service.Impl;

import com.securescm.shipment.entities.OrderItem;
import com.securescm.shipment.entities.Provider;
import com.securescm.shipment.entities.Retailer;
import com.securescm.shipment.entities.Shipment;
import com.securescm.shipment.entities.ShipmentItem;
import com.securescm.shipment.entities.ShipmentItemStatus;
import com.securescm.shipment.entities.ShipmentStatus;
import com.securescm.shipment.model.ItemName;
import com.securescm.shipment.model.ShipmentItemModel;
import com.securescm.shipment.model.Status;
import com.securescm.shipment.model.UserModel;
import com.securescm.shipment.payload.SecurityCheckRequest;
import com.securescm.shipment.payload.ShipmentItemRequest;
import com.securescm.shipment.repos.OrderDao;
import com.securescm.shipment.repos.OrderItemDao;
import com.securescm.shipment.repos.ShipmentDao;
import com.securescm.shipment.repos.ShipmentItemDao;
import com.securescm.shipment.service.ShipmentItemService;
import com.securescm.shipment.util.AppConstants;
import com.securescm.shipment.util.PagedResponse;
import com.securescm.shipment.util.Response;
import com.securescm.shipment.util.SingleItemResponse;
import com.securescm.shipment.util.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author david
 */
@Service
public class ShipmentItemServiceImpl implements ShipmentItemService {

    Logger log = LoggerFactory.getLogger(ShipmentItemServiceImpl.class);

    @Autowired
    private ShipmentItemDao shipmentItemDao;

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ShipmentDao shipmentDao;

    @Override
    public SingleItemResponse createUpdateShipmentItem(ShipmentItemRequest request) {
        ShipmentItem item = new ShipmentItem();

        if (request.getId() != null) {
            item = shipmentItemDao.getOneShipmentItem(request.getId());
        }
        item.setOrderItem(new OrderItem(request.getOrderItem()));

        OrderItem orderItem = orderItemDao.getOne(request.getOrderItem());

        item.setStatus(new ShipmentItemStatus(AppConstants.ITEM_PENDING));
        item.setProduct(orderItem.getProduct());
        item.setShipment(new Shipment(request.getShipment()));
        item.setQuantity(orderItem.getQuantity());
        item.setProvider(orderItem.getProvider());
        
        item.setRetailer(orderItem.getRetailer());

        shipmentItemDao.save(item);

        return new SingleItemResponse(Response.SUCCESS.status(), new ItemName(item.getId()));

    }

    @Override
    public SingleItemResponse deleteShipmentItem(Integer id) {
        ShipmentItem item = shipmentItemDao.getOneShipmentItem(id);
        SingleItemResponse singleItemResponse;
        if (item != null) {
            shipmentItemDao.deleteById(id);
            singleItemResponse = new SingleItemResponse(Response.SUCCESS.status(), new ItemName(item.getId()));
        } else {
            singleItemResponse = new SingleItemResponse(Response.SHIPMENT_ITEM_NOT_FOUND.status(), null);
        }

        return singleItemResponse;
    }

    @Override
    public PagedResponse<ShipmentItemModel> getAllShipmentItems(UserModel userModel, String direction, String orderBy, int page, int size) {
        
         Pageable pageable = Util.getPageable(page, size, direction, orderBy);
         
          Page<ShipmentItem> shipmentItems =null;
         if(userModel.getStakeholder().getType().getName().equalsIgnoreCase("Provider")){
              shipmentItems = shipmentItemDao.findByProvider(new Provider(userModel.getStakeholder().getId()), pageable);
         }else if(userModel.getStakeholder().getType().getName().equalsIgnoreCase("Retailer")){
              shipmentItems =  shipmentItemDao.findByRetailer(new Retailer(userModel.getStakeholder().getId()), pageable);
         }
 
        return Util.getResponse(shipmentItems, shipmentItems.map(shipmentItem -> ShipmentItemModel.map(shipmentItem)).getContent());   
    }

    @Override
    public SingleItemResponse findOneShipmentItem(Integer id) {
        ShipmentItem item = shipmentItemDao.getOneShipmentItem(id);

        return new SingleItemResponse(Response.SUCCESS.status(), ShipmentItemModel.map(item));
    }

    @Override
    public SingleItemResponse approveShipmentItem(UserModel userModel, SecurityCheckRequest request) {

        ShipmentItem item;
        item = shipmentItemDao.getOneShipmentItem(request.getShipmentItem());
        Shipment shipment = shipmentDao.getOneShipment(item.getShipment().getId());
        if (item != null && userModel.getStakeholder().getType().getName().equalsIgnoreCase("Retailer")) {
            if (request.getStatus() == 1) {
                item.setStatus(new ShipmentItemStatus(AppConstants.ITEM_DELIVRED));

            } else if (request.getStatus() == 2) {
                item.setStatus(new ShipmentItemStatus(AppConstants.REJECTED_ON_RETAILER));
            }
            item.setCheckedBy(userModel.getId());
            item.setRemarks(request.getRemarks());
            shipmentItemDao.save(item);

            int [] arr = statusIds(shipment);
            boolean found = IntStream.of(arr).anyMatch(n -> n == 3);
            boolean match = IntStream.of(arr).allMatch(n -> n == 3);

            //Update shipment status
            
            if (match) {
                shipment.setStatus(new ShipmentStatus(AppConstants.DELIVERED));
            } else if (found) {
                shipment.setStatus(new ShipmentStatus(AppConstants.PARTIALLY_DELIVERED));
            }

            shipmentDao.save(shipment);

        } else if (item != null && userModel.getStakeholder().getType().getName().equalsIgnoreCase("Provider")) {
            if (request.getStatus() == 1) {
                item.setStatus(new ShipmentItemStatus(AppConstants.ITEM_ON_DELIVERY));
                shipment.setStatus(new ShipmentStatus(AppConstants.PARTIALLY_ON_DELIVERY));
            } else if (request.getStatus() == 2) {
                item.setStatus(new ShipmentItemStatus(AppConstants.REJECTED_ON_PROVIDER));
            }
            
            item.setCheckedBy(userModel.getId());
            item.setRemarks(request.getRemarks());
            shipmentItemDao.save(item);

            int [] arr = statusIds(shipment);
            boolean found = IntStream.of(arr).anyMatch(n -> n == 2);
            boolean match = IntStream.of(arr).allMatch(n -> n == 2);

            //Update shipment status
            
            if (match) {
                shipment.setStatus(new ShipmentStatus(AppConstants.ON_DELIVERY));
            } else if (found) {
                shipment.setStatus(new ShipmentStatus(AppConstants.PARTIALLY_ON_DELIVERY));
            }

            shipmentDao.save(shipment);

        }

        return new SingleItemResponse(Response.SUCCESS.status(), null);
    }
    
    public int[] statusIds(Shipment shipmentId) {
        List<Integer> statusIds = new ArrayList<>();
        List<ShipmentItem> shipments = shipmentItemDao.findByShipment(shipmentId);
        for (ShipmentItem itm : shipments) {
            statusIds.add(itm.getStatus().getId());
        }
        //Convert Integer array to int array
        return statusIds.stream().mapToInt(i -> i).toArray();
    }

}
