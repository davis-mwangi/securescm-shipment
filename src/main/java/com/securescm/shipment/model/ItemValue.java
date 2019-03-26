/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author david
 */
@NoArgsConstructor
@Data
public class ItemValue {
    private int id;
    private String value;

    public ItemValue(int id) {
        this.id = id;
    }


    public ItemValue(int id, String value) {
        this.id = id;
        this.value = value;
    }
  
}