/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.kafka.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.securescm.shipment.model.Item;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author david
 */
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class OrderItemModel {
    private Long id;
    private ProductModel product;
    private long quantity;
    private double price;
    private Item uom;
    private Item supplier;
    private Item retailer;
    private Date dateRequired;
    private Long quantityAvailable;
    private Date outOfStockAvailableDate;
    private Item status;
    private Date dateCreated;
    private Date ackDate;
    private Integer ackBy;
    private String retailerAction;
    private Date retailerAckDate;
    private Integer retailerAckBy;
    List<PropertyValuesModel> properties;
    
}
