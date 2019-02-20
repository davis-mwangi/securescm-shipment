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
    int PENDING = 1;
    int ACCEPTED = 2;
    int REJECTED = 3;
    int DELIVERED = 4;
    int OPEN = 5;
    int CLOSED = 6;
  
    // Pagination default settings
    String DEFAULT_ORDER_DIRECTION = "ASC";
    String DEFAULT_PAGE_NUMBER = "0";
    String DEFAULT_PAGE_SIZE = "30";
    String DEFAULT_ORDER_BY ="id";
    int MAX_PAGE_SIZE = 50;
   
}
    

