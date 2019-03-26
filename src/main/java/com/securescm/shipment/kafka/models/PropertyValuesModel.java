/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.kafka.models;

import com.securescm.shipment.model.ItemValue;
import java.util.List;
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
public class PropertyValuesModel {
    private String property;
    private List<ItemValue>values;
}

