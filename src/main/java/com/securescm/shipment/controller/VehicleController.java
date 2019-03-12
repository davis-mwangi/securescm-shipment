/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.controller;

import com.securescm.shipment.entities.Transporter;
import com.securescm.shipment.entities.Vehicle;
import com.securescm.shipment.entities.VehicleType;
import com.securescm.shipment.model.VehicleModel;
import com.securescm.shipment.payload.VehicleRequest;
import com.securescm.shipment.repos.VehicleDao;
import com.securescm.shipment.repos.VehicleTypeDao;
import com.securescm.shipment.security.ApiPrincipal;
import com.securescm.shipment.security.CurrentUser;
import com.securescm.shipment.service.VehicleService;
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
@RequestMapping("transporter/vehicle")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;
    
    @Autowired
    private VehicleDao vehicleDao;
    
    @Autowired
    private VehicleTypeDao vehicleTypeDao;
    
  @GetMapping
    public ResponseEntity getAll(
            @RequestParam(value = "direction", defaultValue = AppConstants.DEFAULT_ORDER_DIRECTION) String direction,
            @RequestParam(value = "oderBy", defaultValue = AppConstants.DEFAULT_ORDER_BY) String orderBy,
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @CurrentUser ApiPrincipal principal) {
          
           Page<Vehicle> vehicles = vehicleDao.findByTransporterAndDateDeletedIsNull(
                     new Transporter(principal.getUser().getStakeholder().getId()),
                Util.getPageable(page,size,direction,orderBy));
           
           return  Util.getResponse(Response.SUCCESS.status(), Util.getResponse(vehicles, vehicles.map(vehicle -> {
                    return VehicleModel.map(vehicle);
                }).getContent())); 
    
    }
    
    @GetMapping("/types")
    public ResponseEntity getAllVehicleTypes(
            @RequestParam(value = "direction", defaultValue = AppConstants.DEFAULT_ORDER_DIRECTION) String direction,
            @RequestParam(value = "oderBy", defaultValue = AppConstants.DEFAULT_ORDER_BY) String orderBy,
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
          
           Page<VehicleType> vehicleTypes = vehicleTypeDao.findAll(
                     
                Util.getPageable(page,size,direction,orderBy));
           
           return  Util.getResponse(Response.SUCCESS.status(), Util.getResponse(vehicleTypes, vehicleTypes.map(vehicleType -> {
                    return vehicleType;
                }).getContent()));    
    }

    @GetMapping("/{id}")
    public ResponseEntity getVehicle(@PathVariable("id") Integer id){  
        if(!vehicleDao.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ListItemResponse(Response.PROVIDER_TRANSPORTER_NOT_FOUND.status(), null));
        }
        return ResponseEntity.ok().body(vehicleService.findVehicle(id));
    }
    
    @PostMapping
    public ResponseEntity createVehicle(@RequestBody VehicleRequest request,
            @CurrentUser ApiPrincipal principal){   
        boolean exists = vehicleDao.existsByRegistrationNo(request.getRegistrationNo());      
        if(exists){
        
          return ResponseEntity.status(HttpStatus.CONFLICT).body(
                 new SingleItemResponse(Response.VEHICLE_EXISTS.status(), null ));
        }
        return ResponseEntity.ok().body(vehicleService.createUpdateVehicle(request, principal.getUser()));
    }
    
    @PutMapping
    public ResponseEntity updateDriver( 
            @CurrentUser ApiPrincipal principal,
            @RequestBody VehicleRequest request){ 
        boolean exists = vehicleDao.existsById(request.getId());
        if(!exists){ 
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SingleItemResponse(
                  Response.VEHICLE_NOT_FOUND.status(),
                  null));
        } 
        return ResponseEntity.ok().body(vehicleService.createUpdateVehicle(request, principal.getUser()));
                
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity deleteVehicle(@PathVariable("id") Integer id){ 
         boolean exists = vehicleDao.existsById(id);
        if(!exists){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SingleItemResponse(
                  Response.VEHICLE_NOT_FOUND.status(),
                  null));
        } 
        return ResponseEntity.ok().body(vehicleService.deleteVehicle(id));
    }
    
}
