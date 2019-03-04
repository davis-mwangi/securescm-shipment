/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.controller;

import com.securescm.shipment.entities.Provider;
import com.securescm.shipment.entities.ProviderTransporter;
import com.securescm.shipment.entities.Transporter;
import com.securescm.shipment.payload.ProviderTransporterRequest;
import com.securescm.shipment.repos.ProviderTransporterDao;
import com.securescm.shipment.security.ApiPrincipal;
import com.securescm.shipment.security.CurrentUser;
import com.securescm.shipment.service.TransporterService;
import com.securescm.shipment.util.AppConstants;
import com.securescm.shipment.util.ListItemResponse;
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
@RequestMapping("/provider/transporters")
public class ProviderTransporterController {
    
    @Autowired
    private TransporterService  transporterService;
    
    @Autowired
    private ProviderTransporterDao providerTransporterDao;
    
    @GetMapping
    public PagedResponse<ProviderTransporter> getAllTransportersForProvider(
            @RequestParam(value = "direction", defaultValue = AppConstants.DEFAULT_ORDER_DIRECTION) String direction,
            @RequestParam(value = "oderBy", defaultValue = AppConstants.DEFAULT_ORDER_BY)  String orderBy,
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value= "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @CurrentUser ApiPrincipal apiPrincipal){
        
       //int id = 2; //To be replaced with provider id nfrom token
       
       return transporterService.getAllTransportersForProvider(apiPrincipal.getUser(), direction, orderBy, page, size);
    }
    
    @GetMapping("/all")
    public PagedResponse<ProviderTransporter> getAllTProviderTransporters(
            @RequestParam(value = "direction", defaultValue = AppConstants.DEFAULT_ORDER_DIRECTION) String direction,
            @RequestParam(value = "oderBy", defaultValue = AppConstants.DEFAULT_ORDER_BY)  String orderBy,
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value= "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size){
       return transporterService.getAllProviderTransporter(direction, orderBy, page, size);
    }
    @GetMapping("/{id}")
    public ResponseEntity getProviderTransporter(@PathVariable("id") Integer id){  
        if(!providerTransporterDao.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ListItemResponse(Response.PROVIDER_TRANSPORTER_NOT_FOUND.status(), null));
        }
        return ResponseEntity.ok().body(transporterService.findOneProviderTransporter(id));
    }
    
    @PostMapping
    public ResponseEntity createProviderTransporter(@RequestBody ProviderTransporterRequest request,
            @CurrentUser ApiPrincipal apiPricipal){   
        boolean exists = providerTransporterDao.existsByProviderAndTransporter(
                new Provider(apiPricipal.getUser().getStakeholder().getId()), 
                new Transporter(request.getTransporter()));
        request.setId(null);
        if(exists == true){
        
          return ResponseEntity.status(HttpStatus.CONFLICT).body(
                 new SingleItemResponse(Response.PROVIDER_TRANSPORTER_EXISTS.status(), null ));
        }
        return ResponseEntity.ok().body(transporterService.createProviderTransporter(apiPricipal.getUser(),request));
    }
    
    @PutMapping
    public ResponseEntity updatePRoviderTransporter( 
            @CurrentUser ApiPrincipal apiPrincipal,
            @RequestBody ProviderTransporterRequest request){ 
        boolean exists = providerTransporterDao.existsById(request.getId());
        
        boolean ptExists = providerTransporterDao.existsByProviderAndTransporter(
                new Provider(apiPrincipal.getUser().getStakeholder().getId()), 
                new Transporter(request.getTransporter()));
       
        if(!exists){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SingleItemResponse(
                  Response.PROVIDER_TRANSPORTER_NOT_FOUND.status(),
                  null));
        }
        //check provider transporter exists
        if(ptExists){
           return ResponseEntity.status(HttpStatus.CONFLICT).body(new SingleItemResponse(
                  Response.PROVIDER_TRANSPORTER_EXISTS.status(),
                  null));
        }
       
        
        return ResponseEntity.ok().body(transporterService.createProviderTransporter(apiPrincipal.getUser(),
                request));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity deleteProviderTransporter(@PathVariable("id") Integer id){     
        return ResponseEntity.ok().body(transporterService.deleteProviderTransporter(id));
    }
    
}
