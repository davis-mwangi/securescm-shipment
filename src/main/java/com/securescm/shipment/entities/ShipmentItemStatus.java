/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author david
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShipmentItemStatus {
    @Id
    private Integer id;
    private String  name;

    public ShipmentItemStatus(int id) {
     this.id = id;
    }
}
