/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.payload;

import java.util.Date;
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
public class ShipmentRequest {

    private Integer id;

    private int order;

    private String code;

    private int transaction;

    private int serviceProvider;

    private Date shipmentDate;

    private double shipmentWeight;

    private String freight;

    private String name;

    private String address;

    private String city;

    private String region;

    private String postalcode;

    private int country;

    private int status;

    private int createdBy;

}
