/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.securescm.shipment.entities.TransporterAddress;
import com.securescm.shipment.entities.Provider;
import com.securescm.shipment.entities.Transporter;
import com.securescm.shipment.entities.TransporterStatus;
import com.securescm.shipment.entities.TransporterType;
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
public class TransporterModel {
    @JsonProperty("id")  
  private Integer id;
  
  @JsonProperty("name")  
  private String name;
  
  @JsonProperty("type")  
  private TransporterType type;
  
  @JsonProperty("logo")  
  private String logo;
  
  @JsonProperty("address")  
  private TransporterAddress address;
  
  @JsonProperty("status")  
  private TransporterStatus status;
  
   
  public static TransporterModel map(Transporter provider){
     TransporterModel model =  new TransporterModel();
     model.setId(provider.getId());
     model.setLogo(provider.getLogo());
     model.setName(provider.getName());
     model.setAddress(provider.getAddress());
     model.setType(provider.getType());
     model.setStatus(provider.getStatus());
      return model;
  }
}
