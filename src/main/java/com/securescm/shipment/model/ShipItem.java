/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.model;

import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author david
 */
@NoArgsConstructor
@Data
public class ShipItem {
    private Integer id;
    private String code;
    private String name;
    private String deliveryAddress;
    private Date shipmentDate;

    public ShipItem(Integer id) {
        this.id = id;
    }

    public ShipItem(Integer id, String code) {
        this.id = id;
        this.code = code;
    }

    public ShipItem(Integer id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public ShipItem(Integer id, String code, String name, String deliveryAddress) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.deliveryAddress = deliveryAddress;
    }

    public ShipItem(Integer id, String code, String name, String deliveryAddress, Date shipmentDate) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.deliveryAddress = deliveryAddress;
        this.shipmentDate = shipmentDate;
    }
    
    

}
