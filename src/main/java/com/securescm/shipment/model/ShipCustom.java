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
public class ShipCustom {
    private Integer id;
    private String code;
    private String name;
    private String deliveryAddress;
    private Date shipmentDate;

    public ShipCustom(Integer id) {
        this.id = id;
    }

    public ShipCustom(Integer id, String code) {
        this.id = id;
        this.code = code;
    }

    public ShipCustom(Integer id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public ShipCustom(Integer id, String code, String name, String deliveryAddress) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.deliveryAddress = deliveryAddress;
    }

    public ShipCustom(Integer id, String code, String name, String deliveryAddress, Date shipmentDate) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.deliveryAddress = deliveryAddress;
        this.shipmentDate = shipmentDate;
    }
    
    

}
