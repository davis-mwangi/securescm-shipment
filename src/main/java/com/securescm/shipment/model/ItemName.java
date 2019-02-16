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
public class ItemName {
    private int id;
    private String name;

    public ItemName(int id) {
        this.id = id;
    }

   

    public ItemName(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
