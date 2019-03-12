/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.service.Impl;

import com.securescm.shipment.entities.Shipment;
import com.securescm.shipment.entities.ShipmentItem;
import com.securescm.shipment.entities.ShipmentItemStatus;
import com.securescm.shipment.entities.ShipmentStatus;
import com.securescm.shipment.entities.Transporter;
import com.securescm.shipment.entities.TransporterShipment;
import com.securescm.shipment.entities.TransporterShipmentItem;
import com.securescm.shipment.entities.TransporterShipmentItemStatus;
import com.securescm.shipment.entities.TransporterShipmentStatus;
import com.securescm.shipment.entities.Vehicle;
import com.securescm.shipment.model.ItemName;
import com.securescm.shipment.model.Status;
import com.securescm.shipment.model.TransporterShipmentModel;
import com.securescm.shipment.model.UserModel;
import com.securescm.shipment.payload.AuditTransporterItemRequest;
import com.securescm.shipment.payload.TransporterShipmentItemRequest;
import com.securescm.shipment.payload.TransporterShipmentRequest;
import com.securescm.shipment.repos.ShipmentDao;
import com.securescm.shipment.repos.ShipmentItemDao;
import com.securescm.shipment.repos.TransporterShipmentDao;
import com.securescm.shipment.repos.TransporterShipmentItemDao;
import com.securescm.shipment.repos.VehicleDao;
import com.securescm.shipment.service.TransporterShipmentService;
import com.securescm.shipment.util.AppConstants;
import com.securescm.shipment.util.Response;
import com.securescm.shipment.util.SingleItemResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author david
 */
@Service
public class TransporterShipmentServiceImpl implements TransporterShipmentService {
    
    Logger log =  LoggerFactory.getLogger(TransporterShipmentServiceImpl.class);
    
    @Autowired
    private TransporterShipmentDao  transporterShipmentDao;
    
    @Autowired
    private VehicleDao vehicleDao;
    
    @Autowired
    private TransporterShipmentItemDao transporterShipmentItemDao;
    
    @Autowired
    private ShipmentItemDao shipmentItemDao;
    
    @Autowired
    private ShipmentDao shipmentDao;

    @Override
    public SingleItemResponse createUpdateTransporterShipment(TransporterShipmentRequest request, UserModel userModel) {
        TransporterShipment ts = new TransporterShipment();
        Date now = new Date();
        Vehicle vehicle = vehicleDao.getOne(request.getVehicle());
        
        List<TransporterShipment> tss = transporterShipmentDao.findByTransporter(new Transporter(userModel.getStakeholder().getId()));
        List<Integer>tripsIds = new ArrayList<>();
        for(TransporterShipment trans: tss){
            tripsIds.add(trans.getTrips());        
        }
        int max = 0;
        if(!tripsIds.isEmpty()){
            max = tripsIds
            .stream()
            .mapToInt(v -> v)
            .max().orElseThrow(NoSuchElementException::new);
        }
        int newtripdId = max + 1;
        String name = userModel.getStakeholder().getName().trim().toUpperCase() + "-" + vehicle.getRegistrationNo() +"-" + newtripdId ;
       
        ts.setName(name);
        ts.setTrips(newtripdId);
        ts.setVehicle(vehicle);
        ts.setStatus(new TransporterShipmentStatus(AppConstants.TRANSPORTER_SHIPMENT_OPEN));
        ts.setDateCreated(now);
        ts.setTransporter(new Transporter(userModel.getStakeholder().getId()));
        transporterShipmentDao.save(ts);
        return new SingleItemResponse(Response.SUCCESS.status(), new ItemName(ts.getId(), ts.getStatus().getName()));
    }
    
    

    @Override
    public SingleItemResponse deleteTransporterShipment(Integer id) {
        Status status;
        SingleItemResponse singleItemResponse;
        TransporterShipment shipment = transporterShipmentDao.getOne(id);
        if (shipment != null) {
            shipment.setDateDeleted(new Date());
            transporterShipmentDao.save(shipment);
            singleItemResponse = new SingleItemResponse(Response.SUCCESS.status(), null);
        } else {
            singleItemResponse = new SingleItemResponse(Response.TRANSPORTER_SHIPEMENT_NOT_FOUND.status(),
                    null);
        }
        return singleItemResponse;
    }


    @Override
    public SingleItemResponse findTransporterShipment(Integer id) {
        TransporterShipment shipment = transporterShipmentDao.getOne(id);
        return new SingleItemResponse(Response.SUCCESS.status(), TransporterShipmentModel.map(shipment));
    }

    
    /////////////////////////// TransporterShipmentItem //////////////////
    @Override
    public SingleItemResponse createUpdateTransporterShipmentItem(TransporterShipmentItemRequest request, UserModel userModel) {
        TransporterShipmentItem tsi = new TransporterShipmentItem();
        ShipmentItem shipmentItem = shipmentItemDao.getOneShipmentItem(request.getShipmentItem());
        int shipmentItemStatus = 0;
        double assignedQuantity = 0;
        if (request.getStatus().equalsIgnoreCase("FA")) {
            assignedQuantity = request.getQuantity();
            shipmentItemStatus = AppConstants.ITEM_FULY_ASSIGNED;
        } else if (request.getStatus().equalsIgnoreCase("PA")) {
            if (shipmentItem.getQuantity() > shipmentItem.getAssignedQuantity()) {
                assignedQuantity = shipmentItem.getAssignedQuantity() + request.getQuantity();
                shipmentItemStatus = AppConstants.ITEM_PARTIALLY_ASSIGNED;
            } 

        }
        shipmentItem.setAssignedQuantity(assignedQuantity);
        shipmentItem.setStatus(new ShipmentItemStatus(shipmentItemStatus));
        shipmentItemDao.save(shipmentItem);
        
        TransporterShipment transporterShipment = transporterShipmentDao.getOne(request.getTransporterShipment());
        tsi.setShipment(shipmentItem.getShipment());
        tsi.setDriver(transporterShipment.getVehicle().getDriver());
        tsi.setVehicle(transporterShipment.getVehicle());
        tsi.setDateCreated(new Date());
        tsi.setQuantity(request.getQuantity());
        tsi.setShipmentItem(new ShipmentItem(request.getShipmentItem()));
        tsi.setStatus(new TransporterShipmentItemStatus(AppConstants.TRANSPORT_ITEM_PENDING));
        tsi.setTransporterShipment(new TransporterShipment(request.getTransporterShipment()));
        
        //Recheck of quantity and quantity assigned match after the last transport shipment item is added
        if (shipmentItem.getAssignedQuantity() == shipmentItem.getQuantity()) {
            shipmentItem.setStatus(new ShipmentItemStatus(AppConstants.ITEM_FULY_ASSIGNED));
            shipmentItemDao.save(shipmentItem);     
        }
        
        transporterShipmentItemDao.save(tsi);
        
        
        //Change shipmentStatus
        Shipment shipment = shipmentDao.getOneShipment(shipmentItem.getShipment().getId());
        //log.info(shipment.getId().toString());
        
            
            int [] arr = statusIds(shipment);
            
          //  log.info(Arrays.toString(arr));
            boolean found = IntStream.of(arr).anyMatch(n -> n == 7);
            boolean match = IntStream.of(arr).allMatch(n -> n == 7);

            //Update shipment status
            //  log.info(Boolean.toString(found));
           //   log.info(Boolean.toString(match));
            if (match) {
                shipment.setStatus(new ShipmentStatus(AppConstants.FULLY_ASSIGNED));
            } else if (found) {
                shipment.setStatus(new ShipmentStatus(AppConstants.PARTIALLY_ASSIGNED));
            }
         shipmentDao.save(shipment);
   
        
        return new SingleItemResponse(Response.SUCCESS.status(), new ItemName(tsi.getId()));
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
    
    //Close Transporter Shipment
     @Override
    public SingleItemResponse closeTransporterShipment(Integer id) {
         TransporterShipment tsi = new   TransporterShipment();
        if (id != null) {
            tsi = transporterShipmentDao.getOne(id);
            tsi.setStatus(new TransporterShipmentStatus(AppConstants.TRANSPORTER_SHIPMENT_CLOSE));
        }
        
        transporterShipmentDao.save(tsi);
        return new SingleItemResponse(Response.SUCCESS.status(), new ItemName(tsi.getId(), tsi.getStatus().getName()));

    }
    
    @Override
    public SingleItemResponse openTransporterShipment(Integer id) {
         TransporterShipment tsi = new   TransporterShipment();
        if (id != null) {
            tsi = transporterShipmentDao.getOne(id);
            tsi.setStatus(new TransporterShipmentStatus(AppConstants.TRANSPORTER_SHIPMENT_OPEN));
        }
       
        transporterShipmentDao.save(tsi);
        return new SingleItemResponse(Response.SUCCESS.status(), new ItemName(tsi.getId(), tsi.getStatus().getName()));

    }

    @Override
    public SingleItemResponse deleteTransporterShipmentItem(Integer id) {
        Status status;
        SingleItemResponse singleItemResponse;
        TransporterShipmentItem shipmentItem = transporterShipmentItemDao.getOne(id);
        if (shipmentItem != null) {
            transporterShipmentItemDao.deleteById(id);
            transporterShipmentItemDao.save(shipmentItem);
            singleItemResponse = new SingleItemResponse(Response.SUCCESS.status(), null);
        } else {
            singleItemResponse = new SingleItemResponse(Response.TRANSPORTER_SHIPEMENT_ITEM_NOT_FOUND.status(),
                    null);
        }
        return singleItemResponse;    }

    @Override
    public SingleItemResponse findTransporterShipmentItem(Integer id) {
       TransporterShipmentItem shipmentItem = transporterShipmentItemDao.getOne(id);  
       return new SingleItemResponse(Response.TRANSPORTER_SHIPEMENT_ITEM_NOT_FOUND.status(),shipmentItem);
    }
    
    @Override
    public SingleItemResponse auditTransporterShipmentItem(AuditTransporterItemRequest request, UserModel userModel) {
        TransporterShipmentItem tsi = transporterShipmentItemDao.getOne(request.getId());        
        int tsiStatus = 0;
        int transporterShipStatus = 0;
        String stakeHolder ="";
        if (userModel.getRole().getName().equalsIgnoreCase("SecurityAgent")
                && userModel.getStakeholder().getType().getName().equalsIgnoreCase("Provider")) {
            int status = 2;
            if (request.getStatus().equalsIgnoreCase("APPROVE")) {
                tsiStatus = AppConstants.TRANSPORT_ITEM_ON_DELIVERY;                
            } else if (request.getStatus().equalsIgnoreCase("REJECT")) {
                tsiStatus = AppConstants.TRANSPORT_ITEM_REJECTED_ON_PROVIDER;
            }else{
               return new SingleItemResponse(Response.OPERATION_NOT_ALLOWED.status(),null);  
            }
           updateStatus("provider",tsi.getShipmentItem().getId(), status, tsi.getQuantity()); 
           stakeHolder = "provider";
           transporterShipStatus = 2;
            
        }
        
        if (userModel.getRole().getName().equalsIgnoreCase("SecurityAgent")
                && userModel.getStakeholder().getType().getName().equalsIgnoreCase("Retailer")) {
             int retailerCheckStatus = 3;
            if (request.getStatus().equalsIgnoreCase("APPROVE")) {
                tsiStatus = AppConstants.TRANSPORT_ITEM_DELIVERED;                
            } else if (request.getStatus().equalsIgnoreCase("REJECT")) {
                tsiStatus = AppConstants.TRANSPORT_ITEM_REJECTED_ON_RETAILER;
            }else{
               return new SingleItemResponse(Response.OPERATION_NOT_ALLOWED.status(),null);  
            }
            updateStatus( "retailer", tsi.getShipmentItem().getId(), retailerCheckStatus, tsi.getQuantity());
            stakeHolder = "retailer";
            transporterShipStatus = 3;
        }
        
        tsi.setStatus(new TransporterShipmentItemStatus(tsiStatus));
        transporterShipmentItemDao.save(tsi);
        
        updateTransporterShipmentStatus(stakeHolder,tsi.getTransporterShipment().getId(), transporterShipStatus);
        
        return new SingleItemResponse(Response.SUCCESS.status(), new ItemName(tsi.getId(), tsi.getStatus().getName()));
    }
    
    public void updateStatus(String stakeholder, Integer shipmentItemId, int statusId, double loadedQuantity) {
       
        ShipmentItem shipmentItem = shipmentItemDao.getOneShipmentItem(shipmentItemId);
        int shipmentItemStatus = 0;
        if (stakeholder.equalsIgnoreCase("provider")) {
            if (shipmentItem.getQuantity() > shipmentItem.getLoadedQuantity()) {
                shipmentItem.setLoadedQuantity(shipmentItem.getLoadedQuantity() + loadedQuantity);
            }
            shipmentItem = shipmentItemDao.save(shipmentItem);

            if ((shipmentItem.getQuantity() > shipmentItem.getLoadedQuantity()) && shipmentItem.getLoadedQuantity() > 0) {
                shipmentItemStatus = AppConstants.ITEM_PARTIALLY_ON_DELIVERY;
            } else if (shipmentItem.getQuantity() == shipmentItem.getLoadedQuantity()) {
                shipmentItemStatus = AppConstants.ITEM_ON_DELIVERY;
            }
            shipmentItem.setStatus(new ShipmentItemStatus(shipmentItemStatus));
            shipmentItemDao.save(shipmentItem);

            //Convert Integer array to int array
            int[] arr = intArr(shipmentItem.getShipment().getId());
           // log.info(Arrays.toString(arr));
            boolean found = IntStream.of(arr).anyMatch(n -> n == statusId);
            boolean match = IntStream.of(arr).allMatch(n -> n == statusId);
           // log.info("found:" + Boolean.toString(found));
            //log.info("match:"+ Boolean.toString(match));
            Shipment shipment = shipmentDao.getOneShipment(shipmentItem.getShipment().getId());
            if (match) {
                shipment.setStatus(new ShipmentStatus(AppConstants.ON_DELIVERY));
            } else if (found) {
                shipment.setStatus(new ShipmentStatus(AppConstants.PARTIALLY_ON_DELIVERY));
            }
           shipmentDao.save(shipment);  
        }else if(stakeholder.equalsIgnoreCase("retailer")){
             if (shipmentItem.getQuantity() > shipmentItem.getReceivedQuantity()) {
                shipmentItem.setReceivedQuantity(shipmentItem.getReceivedQuantity()+ loadedQuantity);
            }
            shipmentItem = shipmentItemDao.save(shipmentItem);

            if ((shipmentItem.getQuantity() > shipmentItem.getReceivedQuantity()) && shipmentItem.getReceivedQuantity()> 0) {
                shipmentItemStatus = AppConstants.ITEM_PARTIALLY_DELIVERED;
            } else if (shipmentItem.getQuantity() == shipmentItem.getReceivedQuantity()) {
                shipmentItemStatus = AppConstants.ITEM_DELIVERED;
            }
            shipmentItem.setStatus(new ShipmentItemStatus(shipmentItemStatus));
            shipmentItemDao.save(shipmentItem);
            
            int[] arr = intArr(shipmentItem.getShipment().getId());
            boolean found = IntStream.of(arr).anyMatch(n -> n == statusId);
            boolean match = IntStream.of(arr).allMatch(n -> n == statusId);

            Shipment shipment = shipmentDao.getOneShipment(shipmentItem.getShipment().getId());
            if (match) {
                shipment.setStatus(new ShipmentStatus(AppConstants.DELIVERED));
            } else if (found) {
                shipment.setStatus(new ShipmentStatus(AppConstants.PARTIALLY_DELIVERED));
            }
           shipmentDao.save(shipment);  
        }
       
    }
    
    public int [] intArr(Integer id){
            List<Integer> statusIds = new ArrayList<>();
            List<ShipmentItem> shipmentItems = shipmentItemDao.findByShipment(new Shipment(id));
            for (ShipmentItem itm : shipmentItems) {
                statusIds.add(itm.getStatus().getId());
            }
            //Convert Integer array to int array
        return   statusIds.stream().mapToInt(i -> i).toArray();  
    }
    
    public void updateTransporterShipmentStatus(String stakeHolder, Integer tsId, int statusId) {
        List<Integer> statusIds = new ArrayList<>();
        List<TransporterShipmentItem> transItems = transporterShipmentItemDao.findByTransporterShipment(new TransporterShipment(tsId));
        for (TransporterShipmentItem itm : transItems) {
            statusIds.add(itm.getStatus().getId());
        }

        //Convert Integer array to int array
        int[] arr = statusIds.stream().mapToInt(i -> i).toArray();
         log.info("TransIds"+Arrays.toString(arr));
        log.info(Integer.toString(statusId));
        boolean found = IntStream.of(arr).anyMatch(n -> n == statusId);
        boolean match = IntStream.of(arr).allMatch(n -> n == statusId);
        TransporterShipment tShipment = transporterShipmentDao.getOne(tsId);

        if (stakeHolder.equalsIgnoreCase("provider")) {
           // log.info("Provider:" + Boolean.toString(found));
           // log.info("Provider:"+Boolean.toString(match));
            if (match) {
                tShipment.setStatus(new TransporterShipmentStatus(AppConstants.TRANSPORTER_SHIPMENT_ON_DELIVERY));
            } else if (found) {
                tShipment.setStatus(new TransporterShipmentStatus(AppConstants.TRANSPORTER_SHIPMENT_PARTIALLY_ON_DELIVERY));
            }
        } else if (stakeHolder.equalsIgnoreCase("retailer")) {
            
            log.info("Retailer:" + Boolean.toString(found));
            log.info("Retailer:"+Boolean.toString(match));
            if (match) {
                tShipment.setStatus(new TransporterShipmentStatus(AppConstants.TRANSPORTER_SHIPMENT_ON_DELIVERY));
            } else if (found) {
                tShipment.setStatus(new TransporterShipmentStatus(AppConstants.TRANSPORTER_SHIPMENT_PARTIALLY_DELIVERED));
            }
        }

        transporterShipmentDao.save(tShipment);
    }
    
    
    
}