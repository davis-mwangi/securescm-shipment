/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.controller;

import com.securescm.shipment.entities.Driver;
import com.securescm.shipment.entities.DriverVehicle;
import com.securescm.shipment.entities.DriverVehicleStatus;
import com.securescm.shipment.entities.Transporter;
import com.securescm.shipment.entities.Vehicle;
import com.securescm.shipment.model.DriverVehicleModel;
import com.securescm.shipment.model.VehicleModel;
import com.securescm.shipment.payload.DriverVehicleRequest;
import com.securescm.shipment.repos.DriverVehicleDao;
import com.securescm.shipment.security.ApiPrincipal;
import com.securescm.shipment.security.CurrentUser;
import com.securescm.shipment.service.VehicleService;
import com.securescm.shipment.util.AppConstants;
import com.securescm.shipment.util.ListItemResponse;
import com.securescm.shipment.util.Response;
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
@RequestMapping("/transporter/vehicle/driver")
public class DriverVehicleController {
    @Autowired
    private VehicleService vehicleService;
    
    @Autowired
    private DriverVehicleDao driverVehicleDao;
     
    @PostMapping
    public ResponseEntity createDriver(@RequestBody DriverVehicleRequest request,
            @CurrentUser ApiPrincipal principal){ 
        
        boolean exists = driverVehicleDao.existsByDriverAndVehicleAndStatus(
                new Driver(request.getDriver()),
                new Vehicle(request.getVehicle()),
                new DriverVehicleStatus(AppConstants.DRIVER_ACTIVE));
        
        if(exists){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ListItemResponse(Response.DRIVER_ASSIGNED.status(), null));
        }
        return ResponseEntity.ok().body(vehicleService.createUpdateDriverVehicle(request, 
                principal.getUser()));
    }
    
    @PostMapping("/terminate")
    public ResponseEntity terminateDriver(@RequestBody DriverVehicleRequest request,
            @CurrentUser ApiPrincipal principal){ 
        
        boolean exists = driverVehicleDao.existsByDriverAndStatus(
                new Driver(request.getDriver()), new DriverVehicleStatus(1));     
        if(!exists){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ListItemResponse(Response.TRANSPORTER_SHIPEMENT_NOT_FOUND.status(), null));
        }
        return ResponseEntity.ok().body(vehicleService.terminateDriver(request.getDriver()));
    }
    
    @GetMapping
    public ResponseEntity getAllDriverVehicles(
            @RequestParam(value = "direction", defaultValue = AppConstants.DEFAULT_ORDER_DIRECTION) String direction,
            @RequestParam(value = "oderBy", defaultValue = AppConstants.DEFAULT_ORDER_BY) String orderBy,
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @CurrentUser ApiPrincipal principal) {
          
           Page<DriverVehicle> vehicles = driverVehicleDao.findByTransporterAndStatus(
                   new Transporter(principal.getUser().getStakeholder().getId()),
                   new DriverVehicleStatus(AppConstants.DRIVER_ACTIVE), 
                    Util.getPageable(page,size,direction,orderBy));
             
           
           return  Util.getResponse(Response.SUCCESS.status(), Util.getResponse(vehicles, vehicles.map(driverVehicle -> {
                    return  DriverVehicleModel.map(driverVehicle);
                }).getContent())); 
    
    }
    
    @GetMapping("/{id}")
    public ResponseEntity getVehicle(@PathVariable("id") Integer id){  
        if(!driverVehicleDao.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ListItemResponse(Response.TRANSPORTER_SHIPEMENT_NOT_FOUND.status(), null));
        }
        return ResponseEntity.ok().body(vehicleService.findDriverVehicle(id));
    }
}
