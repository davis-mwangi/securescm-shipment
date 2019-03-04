/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.kafka.models;


import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author david
 */

@Data
@NoArgsConstructor
public class ProviderEvent {
    String event;
    ProviderModel data;
}