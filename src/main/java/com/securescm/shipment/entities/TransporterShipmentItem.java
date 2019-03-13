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
public class TransporterShipmentItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
   
    @JoinColumn(name = "shipment", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Shipment shipment;
   
    @JoinColumn(name = "shipment_item", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ShipmentItem shipmentItem;
    
    @JoinColumn(name = "vehicle", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Vehicle vehicle;
   
    @JoinColumn(name = "driver", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Driver driver;
    private double quantity;
    private double storeAssignedQuantity;
    
    @JoinColumn(name = "transporter_shipment", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TransporterShipment transporterShipment;
    
    @JoinColumn(name = "retailer", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Retailer retailer;
   
    @JoinColumn(name = "status", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TransporterShipmentItemStatus status;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateCreated;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateLastUpdated;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDeleted;

    public TransporterShipmentItem(Integer d) {
        this.id = d;
    }

   
    
}
