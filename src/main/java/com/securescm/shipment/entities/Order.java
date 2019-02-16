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
import javax.persistence.Table;
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
@Table(name="order_tbl")
public class Order {
    @Id
    private Integer id;
    
    @JoinColumn(name = "retailer", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Retailer retailer;
    
    @JoinColumn(name = "provider", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Provider provider;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date orderDate;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date requiredDate;
}
