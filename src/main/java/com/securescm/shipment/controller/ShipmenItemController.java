/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.controller;

import com.securescm.shipment.entities.OrderItem;
import com.securescm.shipment.entities.Shipment;
import com.securescm.shipment.model.ShipmentItemModel;
import com.securescm.shipment.payload.ShipmentItemRequest;
import com.securescm.shipment.repos.ShipmentItemDao;
import com.securescm.shipment.service.ShipmentItemService;
import com.securescm.shipment.util.AppConstants;
import com.securescm.shipment.util.PagedResponse;
import com.securescm.shipment.util.Response;
import com.securescm.shipment.util.SingleItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author david
 */
@RestController
@RequestMapping("items")
public class ShipmenItemController {

   @Autowired
   private ShipmentItemService  shipmentItemService;
   
   @Autowired
   private ShipmentItemDao shipmentItemDao;
 
   
   @PostMapping
    public ResponseEntity createShipmentItem(@RequestBody ShipmentItemRequest request){   
        
        boolean exists =  shipmentItemDao.existsByShipmentAndOrderItem(new Shipment(
                request.getShipment()), new OrderItem(request.getOrderItem()));
        if(exists){
           return ResponseEntity.status(HttpStatus.CONFLICT).body(new SingleItemResponse(Response.SHIPMENT_ITEM_ALREADY_ADDED.status(), null));
        }
        return ResponseEntity.ok().body(shipmentItemService.createUpdateShipmentItem(request));
    }
    
    @PutMapping
    public ResponseEntity updateShipmentItem(@RequestBody ShipmentItemRequest request){   
        if(request.getId() == null){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new SingleItemResponse(Response.EMPTY_ID_SUPPLIED.status(), null));
        }
        
        if(!shipmentItemDao.existsById(request.getId())){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SingleItemResponse(Response.SHIPMENT_NOT_FOUND.status(), null));
        }
        boolean exists =  shipmentItemDao.existsByShipmentAndOrderItem(new Shipment(
                request.getShipment()), new OrderItem(request.getOrderItem()));
        if(exists){
           return ResponseEntity.status(HttpStatus.CONFLICT).body(new SingleItemResponse(Response.SHIPMENT_ITEM_ALREADY_ADDED.status(), null));
        }
        return ResponseEntity.ok().body(shipmentItemService.createUpdateShipmentItem(request));
    }
    
     @GetMapping
    public PagedResponse<ShipmentItemModel> getAllShipmentItems(
            @RequestParam(value = "direction", defaultValue = AppConstants.DEFAULT_ORDER_DIRECTION) String direction,
            @RequestParam(value = "oderBy", defaultValue = AppConstants.DEFAULT_ORDER_BY)  String orderBy,
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value= "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size){
       return shipmentItemService.getAllShipmentItems(direction, orderBy, page, size);
    }
    
      
   @GetMapping("/{id}")
   public ResponseEntity getShipmentItem(@PathVariable("id") Integer id){
      if(!shipmentItemDao.existsById(id)){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SingleItemResponse(Response.SHIPMENT_NOT_FOUND.status(), null));
        }
    return ResponseEntity.status(HttpStatus.OK).body(shipmentItemService.findOneShipmentItem(id));
   }
   
   @DeleteMapping("/{id}")
    public ResponseEntity deleteShipmentItem(@PathVariable("id") Integer id){     
        return ResponseEntity.ok().body(shipmentItemService.deleteShipmentItem(id));
    }
}
