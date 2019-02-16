/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.securescm.shipment.entities.TransporterAddress;
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
public class TransporterRequest {
 
  private Integer id;
  
  @JsonProperty("name")
  private String name;
  
  @JsonProperty("type")
  private int type;
  
  @JsonProperty("logo")
  private String logo;
  
  @JsonProperty("address")
  private TransporterAddress address;
}
