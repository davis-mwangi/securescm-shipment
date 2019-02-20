/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.entities;

import java.util.Date;
import javax.persistence.Entity;
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
public class OrderItem {
    @Id
    private Integer id;
    
    @JoinColumn(name = "order_tbl", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Order order;
    
    @JoinColumn(name = "product", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Product product;
    
    @JoinColumn(name = "provider", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Provider provider;
    
    private double quantity;
    
    private double unitPrice;
    
    private String uom;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateRequired;

    public OrderItem(int orderItem) {
       this.id = orderItem;
    }
    
    
}
