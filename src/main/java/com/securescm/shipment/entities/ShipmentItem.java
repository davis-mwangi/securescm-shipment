/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class ShipmentItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @JoinColumn(name = "shipment", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY,optional=true)
//    @NotFound(action = NotFoundAction.IGNORE)
    private Shipment shipment;
    
    @JoinColumn(name = "product", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Product product;
    
    @JoinColumn(name = "provider", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Provider provider;
    
    @JoinColumn(name = "retailer", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Retailer retailer;
    
    private double quantity;
    
    @JoinColumn(name = "order_item", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private OrderItem orderItem;
    
    @JoinColumn(name = "status", referencedColumnName = "id")
    @ManyToOne(optional = false)
    
    private ShipmentItemStatus status;
    
    private int checkedBy;
    private String remarks;
    
    

    
}
