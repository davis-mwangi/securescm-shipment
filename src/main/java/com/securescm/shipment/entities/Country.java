/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author david
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "apps_countries")
public class Country  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @JsonProperty("code")
    private String countryCode;
      
    @JsonProperty("name")
    private String country_name;

    public Country(Integer id) {
        this.id =  id;
    }

    public Country(int countryId) {
       this.id = countryId;
    }
   
}