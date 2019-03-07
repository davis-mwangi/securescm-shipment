/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.model;


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
public class ProductSupplierModel {
    
    private int id;
    private Item supplier;
    private double price;
    private Item buyUom;
    private int createdBy;
    
}
