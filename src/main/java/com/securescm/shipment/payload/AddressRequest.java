/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author david
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressRequest {
  private Integer id;
 
  private String address;
  
  private String city;
  
  private String region;
  
  private String email;
  
  private int postalcode;
  
  private Integer country;
  
  private String phone;
  
  private String fax;
  
  private  String url;
}
