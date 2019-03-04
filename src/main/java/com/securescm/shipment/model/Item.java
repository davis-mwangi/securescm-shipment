/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author david
 */
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class Item {
    private int id;
    private String code;
    private String name;

    public Item(int id) {
        this.id = id;
    }

    public Item(String code) {
        this.code = code;
    }

    public Item(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Item(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Item(int id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }
    

//    public Item(int id, String code, String name, String description) {
//        this.id = id;
//        this.code = code;
//        this.name = name;
//        this.description = description;
//    }
   
}