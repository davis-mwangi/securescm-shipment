/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.kafka.models;

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
public class Inventory {
    private int store;
    private int retailer;
    private int provider;
    private int product;
    private double quantity;
}
