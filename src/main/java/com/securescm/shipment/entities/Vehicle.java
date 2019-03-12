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
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; 
    
    private String registrationNo;
    
    @JoinColumn(name = "transporter", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Transporter transporter;
    
    @JoinColumn(name = "driver",
            referencedColumnName = "id",
            nullable = true)
    @ManyToOne
    private Driver driver;
    
    @JoinColumn(name = "type", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private VehicleType type;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateCreated;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateLastUpdated;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDeleted;
 
    public Vehicle(Integer id) {
        this.id = id;
    }
    
    
}
