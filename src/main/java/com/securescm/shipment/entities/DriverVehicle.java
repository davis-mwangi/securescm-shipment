/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.entities;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author david
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DriverVehicle {
  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; 
    
    @JoinColumn(name = "driver", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Driver driver;
    
    @JoinColumn(name = "vehicle", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Vehicle vehicle;
    
  @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateAssigned;
  @Temporal(javax.persistence.TemporalType.DATE)
    private Date assignmentEndDate;
   
    @JoinColumn(name = "transporter", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Transporter transporter;
    
    @JoinColumn(name = "status", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private DriverVehicleStatus status;
}
