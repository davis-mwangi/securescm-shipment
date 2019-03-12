/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.controller;

import com.securescm.shipment.entities.Driver;
import com.securescm.shipment.entities.Transporter;
import com.securescm.shipment.model.DriverModel;
import com.securescm.shipment.payload.DriverRequest;
import com.securescm.shipment.repos.DriverDao;
import com.securescm.shipment.security.ApiPrincipal;
import com.securescm.shipment.security.CurrentUser;
import com.securescm.shipment.service.DriverService;
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
@RequestMapping("transporter/driver")
public class DriverController {
    
    @Autowired
    private  DriverService driverService;
    
    @Autowired
    private DriverDao driverDao;
    
 @GetMapping
    public ResponseEntity getAll(
            @RequestParam(value = "direction", defaultValue = AppConstants.DEFAULT_ORDER_DIRECTION) String direction,
            @RequestParam(value = "oderBy", defaultValue = AppConstants.DEFAULT_ORDER_BY) String orderBy,
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @CurrentUser ApiPrincipal principal) {
          
           Page<Driver> drivers = driverDao.findByTransporterAndDateDeletedIsNull(
                     new Transporter(principal.getUser().getStakeholder().getId()),
                Util.getPageable(page,size,direction,orderBy));
           
           return  Util.getResponse(Response.SUCCESS.status(), Util.getResponse(drivers, drivers.map(driver -> {
                    return DriverModel.map(driver);
                }).getContent())); 
    
    }
    
    @GetMapping("/{id}")
    public ResponseEntity getDriver(@PathVariable("id") Integer id){  
        if(!driverDao.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ListItemResponse(Response.DRIVER_NOT_FOUND.status(), null));
        }
        return ResponseEntity.ok().body(driverService.findDriver(id));
    }
    
    @PostMapping
    public ResponseEntity createDriver(@RequestBody DriverRequest request,
            @CurrentUser ApiPrincipal principal){   
        if(driverDao.existsByNationalId(request.getNationalId())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ListItemResponse(Response.DRIVER_EXISTS.status(), null));
        }
        return ResponseEntity.ok().body(driverService.createUpdateDriver(request, 
                principal.getUser()));
    }
    
    @PutMapping
    public ResponseEntity updateDriverr( 
            @CurrentUser ApiPrincipal principal,
            @RequestBody DriverRequest request){ 
        boolean exists = driverDao.existsById(request.getId());
        if(!exists){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SingleItemResponse(
                  Response.DRIVER_NOT_FOUND.status(),
                  null));
        } 
        return ResponseEntity.ok().body(driverService.createUpdateDriver(request, 
                principal.getUser()));
                
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity deleteProviderTransporter(@PathVariable("id") Integer id){ 
         boolean exists = driverDao.existsById(id);
        if(!exists){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SingleItemResponse(
                  Response.DRIVER_NOT_FOUND.status(),
                  null));
        } 
        return ResponseEntity.ok().body(driverService.deleteDriver(id));
    }
    
}
