/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.controller;

import com.securescm.shipment.payload.AssignTransporterRequest;
import com.securescm.shipment.entities.Provider;
import com.securescm.shipment.entities.Shipment;
import com.securescm.shipment.entities.Transporter;
import com.securescm.shipment.model.ShipmentModel;
import com.securescm.shipment.payload.ApproveTransporterRequest;
import com.securescm.shipment.payload.ShipmentRequest;
import com.securescm.shipment.repos.ShipmentDao;
import com.securescm.shipment.security.ApiPrincipal;
import com.securescm.shipment.security.CurrentUser;
import com.securescm.shipment.service.ShipmentService;
import com.securescm.shipment.util.AppConstants;
import com.securescm.shipment.util.PagedResponse;
import com.securescm.shipment.util.Response;
import com.securescm.shipment.util.SingleItemResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
    public ResponseEntity createShipment(@RequestBody ShipmentRequest request, @CurrentUser ApiPrincipal apiPrincipal){   
        boolean providerExists = shipmentDao.existsByName(request.getName());
        request.setId(null);
        if(providerExists == true){
        
          return ResponseEntity.status(HttpStatus.CONFLICT).body(
                 new SingleItemResponse(Response.SHIPMENT_NAME_EXISTS.status(), null ));
        }
        return ResponseEntity.ok().body(shipmentService.createUpdateShipment(request, apiPrincipal.getUser()));
    }
    
    @PutMapping
    public ResponseEntity updateShipment(@RequestBody ShipmentRequest request,  @CurrentUser ApiPrincipal apiPrincipal){   
        if(request.getId() == null){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new SingleItemResponse(Response.EMPTY_ID_SUPPLIED.status(), null));
        }
        
        if(!shipmentDao.existsById(request.getId())){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SingleItemResponse(Response.SHIPMENT_NOT_FOUND.status(), null));
        }
        return ResponseEntity.ok().body(shipmentService.createUpdateShipment(request, apiPrincipal.getUser()));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity getOneShipment(@PathVariable("id") Integer id,
            @CurrentUser ApiPrincipal principal){   
        boolean exists =  shipmentDao.existsById(id);
        if(!exists){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SingleItemResponse(Response.SHIPMENT_NOT_FOUND.status(), null));
        }
        return ResponseEntity.ok().body(shipmentService.findOneShipment(principal.getUser(), id));
    }
    
    @GetMapping
    public PagedResponse<ShipmentModel> getAllShipments(
            @RequestParam(value = "direction", defaultValue = AppConstants.DEFAULT_ORDER_DIRECTION) String direction,
            @RequestParam(value = "oderBy", defaultValue = AppConstants.DEFAULT_ORDER_BY)  String orderBy,
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value= "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @CurrentUser ApiPrincipal apiPrincipal){
       return shipmentService.getAllShipments(apiPrincipal.getUser(),direction, orderBy, page, size);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity deleteShipment(@PathVariable("id") Integer id){     
        return ResponseEntity.ok().body(shipmentService.deleteShipment(id));
    }
    
    @PostMapping("/transporter")
    public ResponseEntity assignTransporter(@RequestBody AssignTransporterRequest request, 
            @CurrentUser ApiPrincipal apiPrincipal){
    // List<Shipment> shipments=  shipmentDao.findByTransporterAndCreatedBy(
//             new Transporter(request.getTransporter()), 
//             new Provider(apiPrincipal.getUser().getStakeholder().getId()));
      if(!apiPrincipal.getUser().getRole().getName().equalsIgnoreCase("Provider")){
         return ResponseEntity.status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS).body(
                 new SingleItemResponse(Response.PROVIDER_ONLY.status(), null));
     }
     
//    //Check if transporter is already assigned to transporter for given provider
//     if(!shipments.isEmpty()){
//         Shipment ship =  shipmentDao.getOneShipment(request.getShipment());
//        for (Shipment shipment: shipments){
//       
//        LocalDateTime date1 =  LocalDateTime.parse(shipment.getShipmentDate().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
//        LocalDateTime date2 =  LocalDateTime.parse(ship.getShipmentDate().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
//        
//           boolean isBefore =   date1.isBefore(date2);
//           if(!isBefore){
//              return ResponseEntity.status(HttpStatus.CONFLICT).body(new SingleItemResponse(Response.TRANSPORTER_ASSIGNED.status(), null));
//           }
//        }
//         //Get date of the shipment and compare with date of the shipemnt been modified
//         
//     }
      return ResponseEntity.ok(shipmentService.assignTransporter(request)); 
    }
    
    //Approve Shipment
    @PostMapping("/transporter/approve")
    public ResponseEntity upholdShipment(@RequestBody ApproveTransporterRequest request,
            @CurrentUser ApiPrincipal apiPrincipal){
        if (!apiPrincipal.getUser().getRole().getName().equalsIgnoreCase("Transporter")) {
            return ResponseEntity.status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS).body(
                    new SingleItemResponse(Response.TRANSPORTER_ONLY.status(), null));
        }
       
       return ResponseEntity.ok().body(shipmentService.approveShipment(apiPrincipal.getUser(), request));
    }
   
    //Close Shipment
    @PostMapping("/close/{id}")
    public ResponseEntity closeShipment(@CurrentUser ApiPrincipal apiPrincipal, @PathVariable ("id") Integer id){
    
     boolean exists =  shipmentDao.existsByIdAndCreatedBy(id,
             new Provider(apiPrincipal.getUser().getStakeholder().getId()));
     if(!apiPrincipal.getUser().getRole().getName().equalsIgnoreCase("Provider")){
         return ResponseEntity.status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS).body(
                 new SingleItemResponse(Response.PROVIDER_ONLY.status(), null));
     }
     if(!exists){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SingleItemResponse(Response.SHIPMENT_NOT_FOUND.status(), null));

     }
    return ResponseEntity.ok().body(shipmentService.closeShipment(id));
    }
}
