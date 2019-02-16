/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Shipment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
  
    @JoinColumn(name = "transaction", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Transaction transaction;
    
    @JoinColumn(name = "transporter", referencedColumnName = "id")
    @ManyToOne
    private Transporter transporter;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date shipmentDate;
    
    private String code;
    
    private double shipmentWeight;
    
    private String freight;
    
    private String name;
    
    private String address;
    
    private String city;
    
    private String region;
    
    private String postalcode;
    
    @JoinColumn(name = "country", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Country country;
    
    @JoinColumn(name = "status", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ShipmentStatus status;
    
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Provider createdBy;
    
    @JsonIgnore
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateCreated;
    @JsonIgnore
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateLastUpdated;
    
    @JsonIgnore
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateDeleted;

    public Shipment(int id) {
        this.id =  id;
    }
    
    
}
