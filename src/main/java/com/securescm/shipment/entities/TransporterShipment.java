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
public class TransporterShipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @JoinColumn(name = "vehicle", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private Vehicle vehicle;
    
    private int trips;

    @JoinColumn(name = "status", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private TransporterShipmentStatus status;
   
    @JoinColumn(name = "transporter", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Transporter transporter;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateLastUpdated;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateDeleted;

    public TransporterShipment(Integer id) {
        this.id = id;
    }
    
    

}
