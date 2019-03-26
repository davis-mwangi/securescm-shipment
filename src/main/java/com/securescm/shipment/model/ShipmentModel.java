/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.model;

import com.securescm.shipment.entities.Country;
import com.securescm.shipment.entities.Provider;
import com.securescm.shipment.entities.Shipment;
import com.securescm.shipment.entities.ShipmentItem;
import com.securescm.shipment.entities.ShipmentStatus;
import com.securescm.shipment.entities.Transaction;
import com.securescm.shipment.entities.Transporter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author david
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentModel {
    private Integer id;
 
    private Transaction transaction;
    
    private Transporter transporter;
    
    private Date shipmentDate;
    
    private String code;
    
    private double shipmentWeight;
    
    private String freight;
    
    private String name;
    
    private String address;
    
    private String city;
    
    private String region;
    
    private Country country;
    
    private String postalcode;
    
    private ShipmentStatus status;
    
    private Provider createdBy;
    
    private List<ShipmentItemModel>shipmentItems;
    
    public static ShipmentModel map(Shipment shipment,List<ShipmentItemModel> shipItmModels){
      ShipmentModel model =  new ShipmentModel();
      model.setId(shipment.getId());
      model.setTransaction(shipment.getTransaction());
      model.setTransporter(shipment.getTransporter());
      model.setShipmentDate(shipment.getShipmentDate());
      model.setCode(shipment.getCode());
      model.setShipmentWeight(shipment.getShipmentWeight());
      model.setFreight(shipment.getFreight());
      model.setName(shipment.getName());
      model.setAddress(shipment.getAddress());
      model.setCity(shipment.getCity());
      model.setCountry(shipment.getCountry());
      model.setRegion(shipment.getRegion());
      model.setPostalcode(shipment.getPostalcode());
      model.setStatus(shipment.getStatus());
      model.setCreatedBy(shipment.getCreatedBy());
      
      model.setShipmentItems(shipItmModels);
     
      return model;
    }
   
    
}
