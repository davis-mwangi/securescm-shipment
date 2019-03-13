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
    PROVIDER_TRANSPORTER_EXISTS(15, "Provider transporter already exists"),
    SHIPMENT_CLOSED(16, "Cannot add shipment item, Shipment is closed"),
    TRANSPORTER_ASSIGNED(17,"Transporter is already assigned to a Shipment"),
    PROVIDER_ONLY(18, "Only provider allowed to carry out the process"),
    TRANSPORTER_ONLY(18, "Only transporter allowed to carry out this process"),
    DRIVER_EXISTS(19, "Driver already registered"),
    DRIVER_NOT_FOUND(20,"Driver not found"),
    VEHICLE_NOT_FOUND(21,"Vehicle not found"),
    VEHICLE_EXISTS(22,"Vehicle already registered"),
    DRIVER_ASSIGNED(23, "Driver is already assigned to a vehicle"),
    TRANSPORTER_SHIPEMENT_NOT_FOUND(24, "Transporter Shipment not found"),
    TRANSPORTER_SHIPEMENT_EXIST(25, "Transporter Shipment already exists"),
    TRANSPORTER_SHIPEMENT_ITEM_NOT_FOUND(26, "Transporter Shipment item not found"),
    TRANSPORTER_SHIPEMENT_ITEM_EXIST(27, "Transporter Shipment item already exists"),
    SHIPMENT_ITEM_FULY_ASSIGNED(28, "Shipment item is fully assigned"),
    SHIPMENT_ITEM_EXCEEDED(28, "Quantity exceeds the shipment item quantity"),
    TRANSPORTER_SHIPMENT_CLOSED(29, "Cannot add items, Transporter shipment is closed"),
    TRANSPORTER_SHIPEMENT_ITEM_FULLY_ASSIGNED(30, "Item is fully assigned to store(s) "),
    TRANSPORTER_SHIPEMENT_ITEM_EXCEEDED(31, "Quantity supplied exceeds the initial quantity");
    
    
    
    private Status status;
    Response(int code, String message){
        this.status = new Status(code,message);
    }

    public Status status(){
        return status;
    }
}

