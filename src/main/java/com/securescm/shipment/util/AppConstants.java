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
  
    // Pagination default settings
    String DEFAULT_ORDER_DIRECTION = "ASC";
    String DEFAULT_PAGE_NUMBER = "0";
    String DEFAULT_PAGE_SIZE = "30";
    String DEFAULT_ORDER_BY ="id";
    int MAX_PAGE_SIZE = 50;
    
    //Shipment Item Status
    int ITEM_PENDING = 1;
    int ITEM_ON_DELIVERY= 2;
    int ITEM_DELIVRED =3;
    int REJECTED_ON_PROVIDER =4;
    int REJECTED_ON_RETAILER =5;
    
   
}
    

