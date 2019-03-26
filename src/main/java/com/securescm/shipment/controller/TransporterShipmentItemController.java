/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.controller;

import com.securescm.shipment.entities.ShipmentItem;
import com.securescm.shipment.entities.TransporterShipment;
import com.securescm.shipment.entities.TransporterShipmentItem;
import com.securescm.shipment.model.TransporterShipmentItemModel;
import com.securescm.shipment.payload.AssignTransporterItemStoreRequest;
import com.securescm.shipment.payload.AuditTransporterItemRequest;
import com.securescm.shipment.payload.SecurityCheckRequest;
import com.securescm.shipment.payload.TransporterShipmentItemRequest;
import com.securescm.shipment.repos.ShipmentItemDao;
import com.securescm.shipment.repos.TransporterShipmentDao;
import com.securescm.shipment.repos.TransporterShipmentItemDao;
import com.securescm.shipment.security.ApiPrincipal;
import com.securescm.shipment.security.CurrentUser;
import com.securescm.shipment.service.TransporterShipmentService;
import com.securescm.shipment.util.AppConstants;
import com.securescm.shipment.util.ListItemResponse;
import com.securescm.shipment.util.Response;
import com.securescm.shipment.util.SingleItemResponse;
import com.securescm.shipment.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author david
 */
@RestController
@RequestMapping("/transporter/shipment/item")
public class TransporterShipmentItemController {
    @Autowired 
    private TransporterShipmentService service;
    
    @Autowired
    private TransporterShipmentItemDao transporterShipmentItemDao;
    
    @Autowired 
    private ShipmentItemDao shipmentItemDao;
    
    @Autowired
    private TransporterShipmentDao transporterShipmentDao;
    
    @PostMapping
    public ResponseEntity createTransporterShipmentItem(@RequestBody TransporterShipmentItemRequest request,
            @CurrentUser ApiPrincipal principal){
        TransporterShipment transporterShipment = transporterShipmentDao.getOne(request.getTransporterShipment());
        if(transporterShipment.getStatus().getId() != 1){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body( new SingleItemResponse(Response.TRANSPORTER_SHIPMENT_CLOSED.status(), null));
        }
        
        ShipmentItem item  = shipmentItemDao.getOne(request.getShipmentItem());
        //Check  if is fully assigned
        if(item.getQuantity() == item.getAssignedQuantity()){
             return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body( new SingleItemResponse(Response.SHIPMENT_ITEM_FULY_ASSIGNED.status(), null));
        }
        //Check if the quantity been assigned is greater than the current shipment quantity
        else if((item.getAssignedQuantity()+ request.getQuantity()) > item.getQuantity()){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body( new SingleItemResponse(Response.SHIPMENT_ITEM_EXCEEDED.status(), null));
        }
        return ResponseEntity.ok().body(service.createUpdateTransporterShipmentItem(request, 
                principal.getUser()));
    }  
    @GetMapping
    public ResponseEntity getAllTransporterShipmentItem(
            @RequestParam(value = "direction", defaultValue = AppConstants.DEFAULT_ORDER_DIRECTION) String direction,
            @RequestParam(value = "oderBy", defaultValue = AppConstants.DEFAULT_ORDER_BY) String orderBy,
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @CurrentUser ApiPrincipal principal) {
          
           Page<TransporterShipmentItem> transportItems = transporterShipmentItemDao.findAll(
                    Util.getPageable(page,size,direction,orderBy));
             
           
           return  Util.getResponse(Response.SUCCESS.status(), Util.getResponse(transportItems, transportItems.map(transportItem -> {
                    return  TransporterShipmentItemModel.map(transportItem,service.getTransporterItemProperties(transportItem));
                }).getContent())); 
    
    }
    
    @GetMapping("/{id}")
    public ResponseEntity getVehicle(@PathVariable("id") Integer id){  
        if(!transporterShipmentItemDao.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ListItemResponse(Response.TRANSPORTER_SHIPEMENT_ITEM_NOT_FOUND.status(), null));
        }
        return ResponseEntity.ok().body(service.findTransporterShipmentItem(id));
    }
    
     @DeleteMapping("/{id}")
    public ResponseEntity deleteVehicle(@PathVariable("id") Integer id) {
        boolean exists = transporterShipmentItemDao.existsById(id);
        if (!exists) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SingleItemResponse(
                    Response.TRANSPORTER_SHIPEMENT_ITEM_NOT_FOUND.status(),
                    null));
        }
        return ResponseEntity.ok().body(service.deleteTransporterShipmentItem(id));
    }
    
    @PostMapping("/check")
    public ResponseEntity approveTransporterShipmentItem(@CurrentUser ApiPrincipal apiPrincipal,
            @RequestBody AuditTransporterItemRequest request){
        
        return ResponseEntity.ok().body(service.auditTransporterShipmentItem(request, apiPrincipal.getUser()));  
               
   
    }
    
    @PostMapping("/store/assign")
    public ResponseEntity approveTransporterShipmentItem(@CurrentUser ApiPrincipal apiPrincipal,
            @RequestBody AssignTransporterItemStoreRequest request) {
        TransporterShipmentItem item = transporterShipmentItemDao.getOne(request.getTransporterShipmentItem());
        if(item.getQuantity() == item.getStoreAssignedQuantity()){
             return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body( new SingleItemResponse(Response.TRANSPORTER_SHIPEMENT_ITEM_FULLY_ASSIGNED.status(), null));
        }
        //Check if the quantity been assigned is greater than the current transporter shipment item quantity
        else if((item.getStoreAssignedQuantity()+ request.getQuantity()) > item.getQuantity()){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body( new SingleItemResponse(Response.TRANSPORTER_SHIPEMENT_ITEM_EXCEEDED.status(), null));
        }
        return ResponseEntity.ok().body(service.assignTransporterShipmentItemToStore(request, apiPrincipal.getUser()));
    }
}
