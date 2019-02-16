/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.util;

import com.securescm.shipment.model.Status;

/**
 *
 * @author david
 */
public enum Response {
    SUCCESS(0,"success"),
    TRANSPORTER_NOT_FOUND(1, "Service Provider not found"),
    TRANSPORTER_NAME_EXIST(2, "Transporter name already exists"),
    RANSPORTER_TYPE_NOT_FOUND(3,"Provider type not found"),
    
    SHIPMENT_NAME_EXISTS(4,"Shipment name exists"),
    SHIPMENT_NOT_FOUND(5,"Shipment not found"),
    OPERATION_NOT_ALLOWED(6,"Operation not allowed"),
    PRODUCT_ALREADY_DELETED (7,"Product does not exist or was deleted"),
    EMPTY_ID_SUPPLIED(8,"Please provide a valid `id`"),
    ADDRESS_NOT_FOUND(9, "Address not found"), 
    EMPTY_ADDRESS_ID_SUPPLIED(10,"Please provide transporter address `id`"),
    EMPTY_ITEM_ID_SUPPLIED(11,"Please provide a valid item `id`"),
    SHIPMENT_ITEM_NOT_FOUND(12,"Item not found"),
    SHIPMENT_ITEM_ALREADY_ADDED(13, "Shippment item already exists"),
    
    PROVIDER_TRANSPORTER_NOT_FOUND(14, "Provider transporter not found"),
    PROVIDER_TRANSPORTER_EXISTS(15, "Provider transporter already exists");
    
    private Status status;
    Response(int code, String message){
        this.status = new Status(code,message);
    }

    public Status status(){
        return status;
    }
}

