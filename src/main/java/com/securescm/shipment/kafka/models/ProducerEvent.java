/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.kafka.models;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author david
 */

@AllArgsConstructor
@Data
public class ProducerEvent {
    String event;
    Object data;
}
