/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.controller;

import com.securescm.shipment.entities.ShipmentItem;
import com.securescm.shipment.model.TransporterModel;
import com.securescm.shipment.payload.TransporterRequest;
import com.securescm.shipment.repos.AddressDao;
import com.securescm.shipment.util.ListItemResponse;
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
import org.springframework.web.bind.annotation.RestController;
import com.securescm.shipment.repos.TransporterDao;
import com.securescm.shipment.service.TransporterService;
import com.securescm.shipment.util.AppConstants;
import com.securescm.shipment.util.PagedResponse;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author david
 */
@RestController
@RequestMapping("/transporters")
public class TransporterController {
    @Autowired
    private TransporterService transporterService;
    
    @Autowired
    private TransporterDao serviceProviderDao;
    
    @Autowired
    private AddressDao addressDao;
    
   
    @GetMapping
    public PagedResponse<TransporterModel> getAllTransporters(
            @RequestParam(value = "direction", defaultValue = AppConstants.DEFAULT_ORDER_DIRECTION) String direction,
            @RequestParam(value = "oderBy", defaultValue = AppConstants.DEFAULT_ORDER_BY)  String orderBy,
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value= "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size){
       return transporterService.getAllTransporters(direction, orderBy, page, size);
    }
    @GetMapping("/{id}")
    public ResponseEntity getTransporter(@PathVariable("id") Integer id){  
        if(!serviceProviderDao.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ListItemResponse(Response.TRANSPORTER_NOT_FOUND.status(), null));
        }
        return ResponseEntity.ok().body(transporterService.findOneTransporter(id));
    }
    
    @PostMapping
    public ResponseEntity createTransporter(@RequestBody TransporterRequest request){   
        boolean providerExists = serviceProviderDao.existsByName(request.getName());
        request.setId(null);
        if(providerExists == true){
        
          return ResponseEntity.status(HttpStatus.CONFLICT).body(
                 new SingleItemResponse(Response.TRANSPORTER_NAME_EXIST.status(), null ));
        }
        return ResponseEntity.ok().body(transporterService.createUpdateTransporter(request));
    }
    
    @PutMapping
    public ResponseEntity updateTransporter( 
            @RequestBody TransporterRequest request){ 
        boolean providerExists = serviceProviderDao.existsById(request.getId());
        
        if(!providerExists){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SingleItemResponse(
                  Response.TRANSPORTER_NOT_FOUND.status(),
                  null));
        }
        if (request.getAddress().getId() == null){
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new SingleItemResponse(
                  Response.EMPTY_ADDRESS_ID_SUPPLIED.status(),
                  null));
        }
        if(addressDao.getOneAddress(request.getAddress().getId()) == null){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                   new ListItemResponse(Response.ADDRESS_NOT_FOUND.status(),
                    null));
        }
        
        return ResponseEntity.ok().body(transporterService.createUpdateTransporter(request));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity deleteTransporter(@PathVariable("id") Integer id){     
        return ResponseEntity.ok().body(transporterService.deleteTransporter(id));
    }
    
    @GetMapping("/types")
    public ResponseEntity getAllTransporterTypes(){
       return ResponseEntity.ok().body(transporterService.getAllTransporterTypes());
    }
}

