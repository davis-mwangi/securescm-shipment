/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.controller;

import com.securescm.shipment.entities.Transporter;
import com.securescm.shipment.entities.TransporterShipment;
import com.securescm.shipment.model.TransporterShipmentModel;
import com.securescm.shipment.payload.TransporterShipmentRequest;
import com.securescm.shipment.repos.TransporterShipmentDao;
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
@RequestMapping("/transporter/shipment")
public class TransporterShipmentController {
    
    @Autowired
    private TransporterShipmentService service;
    
    @Autowired
    private TransporterShipmentDao  transporterShipmentDao;
    
     
    @PostMapping
    public ResponseEntity createTransporterShipment(@RequestBody TransporterShipmentRequest request,
            @CurrentUser ApiPrincipal principal){ 
        return ResponseEntity.ok().body(service.createUpdateTransporterShipment(request, 
                principal.getUser()));
    }  
    @GetMapping
    public ResponseEntity getAllTransporterShipment(
            @RequestParam(value = "direction", defaultValue = AppConstants.DEFAULT_ORDER_DIRECTION) String direction,
            @RequestParam(value = "oderBy", defaultValue = AppConstants.DEFAULT_ORDER_BY) String orderBy,
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @CurrentUser ApiPrincipal principal) {
          
           Page<TransporterShipment> vehicles = transporterShipmentDao.findByTransporter(
                   new Transporter(principal.getUser().getStakeholder().getId()),
                    Util.getPageable(page,size,direction,orderBy));
             
           
           return  Util.getResponse(Response.SUCCESS.status(), Util.getResponse(vehicles, vehicles.map(ts -> {
                    return  TransporterShipmentModel.map(ts);
                }).getContent())); 
    
    }
    
    @GetMapping("/{id}")
    public ResponseEntity getVehicle(@PathVariable("id") Integer id){  
        if(!transporterShipmentDao.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ListItemResponse(Response.TRANSPORTER_SHIPEMENT_NOT_FOUND.status(), null));
        }
        return ResponseEntity.ok().body(service.findTransporterShipment(id));
    }
    
    @PostMapping("/close/{id}")
    public ResponseEntity closeTransporterShipment(@CurrentUser ApiPrincipal apiPrincipal, @PathVariable ("id") Integer id){
    
     boolean exists =  transporterShipmentDao.existsByIdAndTransporter(id,
             new Transporter(apiPrincipal.getUser().getStakeholder().getId()));
     if(!apiPrincipal.getUser().getRole().getName().equalsIgnoreCase("Transporter")){
         return ResponseEntity.status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS).body(
                 new SingleItemResponse(Response.TRANSPORTER_ONLY.status(), null));
     }
     if(!exists){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SingleItemResponse(Response.TRANSPORTER_SHIPEMENT_NOT_FOUND.status(), null));

     }
    return ResponseEntity.ok().body(service.closeTransporterShipment(id));
    }
    
    @PostMapping("/open/{id}")
    public ResponseEntity openTransporterShipment(@CurrentUser ApiPrincipal apiPrincipal, @PathVariable ("id") Integer id){
    
     boolean exists =  transporterShipmentDao.existsByIdAndTransporter(id,
             new Transporter(apiPrincipal.getUser().getStakeholder().getId()));
     if(!apiPrincipal.getUser().getRole().getName().equalsIgnoreCase("Transporter")){
         return ResponseEntity.status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS).body(
                 new SingleItemResponse(Response.TRANSPORTER_ONLY.status(), null));
     }
     if(!exists){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SingleItemResponse(Response.TRANSPORTER_SHIPEMENT_NOT_FOUND.status(), null));

     }
    return ResponseEntity.ok().body(service.openTransporterShipment(id));
    }

}
