/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.model;

import com.securescm.shipment.entities.Driver;
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
public class DriverModel {
    private Integer id;
    private String firstName;
    private String lastName;
    private String nationalId;
    private String phoneNumber;
    private String licenceNo;
    private ItemName transporter;
    
    
    public static DriverModel map (Driver driver){
        DriverModel model = new DriverModel();
        model.setId(driver.getId());
        model.setFirstName(driver.getFirstName());
        model.setLastName(driver.getLastName());
        model.setLicenceNo(driver.getLicenceNo());
        model.setNationalId(driver.getNationalId());
        model.setPhoneNumber(driver.getPhoneNumber());
        model.setTransporter(new ItemName(driver.getTransporter().getId(), driver.getTransporter().getName()));
        
        return model;
    }
}
