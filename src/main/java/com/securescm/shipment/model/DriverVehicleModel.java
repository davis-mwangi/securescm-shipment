/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.model;

import com.securescm.shipment.entities.DriverVehicle;
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
public class DriverVehicleModel {
    private Integer id;
    private DriverModel driver;
    private VehicleModel vehicle;
    private Date dateAssigned;
    private Date assigmentEndDate;
    private ItemName transporter;
    
    public static DriverVehicleModel map(DriverVehicle dv){
       DriverVehicleModel model =  new DriverVehicleModel();
       model.setId(dv.getId());
       model.setDriver(new DriverModel(
               dv.getDriver().getId(), 
               dv.getDriver().getFirstName(), 
               dv.getDriver().getLastName(), 
               dv.getDriver().getNationalId(), 
               dv.getDriver().getPhoneNumber(),
               dv.getDriver().getLicenceNo(), null));
      model.setVehicle(new VehicleModel(
              dv.getVehicle().getId(),
              dv.getVehicle().getRegistrationNo(),null,null,
              dv.getVehicle().getType(),
              dv.getVehicle().getDateCreated()));
      model.setDateAssigned(dv.getDateAssigned());
      model.setAssigmentEndDate(dv.getAssignmentEndDate());
      model.setTransporter(new ItemName(
              dv.getTransporter().getId(),
              dv.getTransporter().getName()));
      return model;
    }
}
