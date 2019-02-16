/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author david
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransporterAddress {
  
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;
 
  private String address;
  
  private String email;
  
  private String city;
  
  private String region;
  
  private int postalcode;
  
  @JoinColumn(name = "country", referencedColumnName = "id")
  @ManyToOne(optional = false)
  private Country country;
  
  private String phone;
  
  private String fax;
  
  private  String url;

  public TransporterAddress(int id) {
    this.id = id;
  }
  
}
