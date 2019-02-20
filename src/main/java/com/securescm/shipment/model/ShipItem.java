/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.model;

import com.securescm.shipment.entities.Product;
import com.securescm.shipment.entities.Retailer;
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
public class ShipItem {
    private int id;
    
    private  Product product;
    
    private Retailer retailer;
    
    private Date orderDate;
    
    private Date orderRequiredDate;
        
}
