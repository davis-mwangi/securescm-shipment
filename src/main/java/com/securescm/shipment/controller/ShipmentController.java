/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.controller;

import com.securescm.shipment.entities.Shipment;
import com.securescm.shipment.payload.ShipmentRequest;
import com.securescm.shipment.repos.ShipmentDao;
import com.securescm.shipment.service.ShipmentService;
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
@RequestMapping("shipment")
public class ShipmentController {
    @Autowired
    private ShipmentService shipmentService;
    
    @Autowired
    private ShipmentDao shipmentDao;
    
    @PostMapping
    public ResponseEntity createShipment(@RequestBody ShipmentRequest request){   
        boolean providerExists = shipmentDao.existsByName(request.getName());
        request.setId(null);
        if(providerExists == true){
        
          return ResponseEntity.status(HttpStatus.CONFLICT).body(
                 new SingleItemResponse(Response.SHIPMENT_NAME_EXISTS.status(), null ));
        }
        return ResponseEntity.ok().body(shipmentService.createUpdateShipment(request));
    }
    
    @PutMapping
    public ResponseEntity updateShipment(@RequestBody ShipmentRequest request){   
        if(request.getId() == null){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new SingleItemResponse(Response.EMPTY_ID_SUPPLIED.status(), null));
        }
        
        if(!shipmentDao.existsById(request.getId())){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SingleItemResponse(Response.SHIPMENT_NOT_FOUND.status(), null));
        }
        return ResponseEntity.ok().body(shipmentService.createUpdateShipment(request));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity getOneShipment(@PathVariable("id") Integer id){   
        return ResponseEntity.ok().body(shipmentService.findOneShipment(id));
    }
    
    @GetMapping
    public PagedResponse<Shipment> getAllProviders(
            @RequestParam(value = "direction", defaultValue = AppConstants.DEFAULT_ORDER_DIRECTION) String direction,
            @RequestParam(value = "oderBy", defaultValue = AppConstants.DEFAULT_ORDER_BY)  String orderBy,
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value= "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size){
       return shipmentService.getAllShipments(direction, orderBy, page, size);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity deleteShipment(@PathVariable("id") Integer id){     
        return ResponseEntity.ok().body(shipmentService.deleteShipment(id));
    }
}
