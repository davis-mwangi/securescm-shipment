/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.util;

/**
 *
 * @author david
 */
public interface AppConstants {
    //Shipment Status
    int PENDING = 1; //100
    int ACCEPTED = 2; //102
    int REJECTED = 3;//103
    int PARTIALLY_DELIVERED = 4; //104
    int OPEN = 5; //105
    int CLOSED = 6; //106
    int DELIVERED= 7; //107
    int PARTIALLY_ON_DELIVERY = 8; //108
    int ON_DELIVERY =9; //109
    int PARTIALLY_ASSIGNED = 10; //110
    int FULLY_ASSIGNED = 11; //111       
  
    // Pagination default settings
    String DEFAULT_ORDER_DIRECTION = "ASC";
    String DEFAULT_PAGE_NUMBER = "0";
    String DEFAULT_PAGE_SIZE = "30";
    String DEFAULT_ORDER_BY ="id";
    int MAX_PAGE_SIZE = 50;
    
    //Shipment Item Status
    int ITEM_PENDING = 1;
    int ITEM_ON_DELIVERY= 2;
    int ITEM_DELIVERED =3;
    int ITEM_PARTIALLY_ON_DELIVERY =4;
    int REJECTED_ON_RETAILER =5;
    int ITEM_PARTIALLY_ASSIGNED = 6;
    int ITEM_FULY_ASSIGNED =7;
    int ITEM_PARTIALLY_DELIVERED= 8;
    
    
    //Driver Vehicle status
    int DRIVER_ACTIVE = 1;
    int DRIVER_REASSIGNED = 2;
    int DRIVER_TERMINATED = 3;
    
    //Transporter Shipment Status
    int TRANSPORTER_SHIPMENT_OPEN = 1;
    int TRANSPORTER_SHIPMENT_CLOSE = 2;
    int TRANSPORTER_SHIPMENT_PARTIALLY_ON_DELIVERY = 3;
    int TRANSPORTER_SHIPMENT_ON_DELIVERY = 4;
    int TRANSPORTER_SHIPMENT_PARTIALLY_DELIVERED = 5;
    int TRANSPORTER_SHIPMENT_DELIVERED = 6;
    
    //Transport Shipment Item
    int TRANSPORT_ITEM_PENDING = 1;
    int TRANSPORT_ITEM_ON_DELIVERY = 2;
    int TRANSPORT_ITEM_DELIVERED = 3;
    int TRANSPORT_ITEM_REJECTED_ON_PROVIDER = 4;
    int TRANSPORT_ITEM_REJECTED_ON_RETAILER = 5;
    
   
}
    

