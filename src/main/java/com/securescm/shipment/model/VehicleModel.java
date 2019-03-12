/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.model;

import com.securescm.shipment.entities.Vehicle;
import com.securescm.shipment.entities.VehicleType;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author david
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleModel {
    private Integer id; 
    private String registrationNo;
    private ItemName transporter;
    private DriverModel driver;
    private VehicleType type;
    private Date dateCreated;
    
    public static VehicleModel map(Vehicle vehicle){
        VehicleModel model=  new VehicleModel();
        model.setId(vehicle.getId());  
        model.setType(vehicle.getType());
        model.setDateCreated(vehicle.getDateCreated());
        model.setRegistrationNo(vehicle.getRegistrationNo());
        model.setTransporter(new ItemName(vehicle.getTransporter().getId(),
                vehicle.getTransporter().getName()));
        
    return model;    
              
    }
   
}
