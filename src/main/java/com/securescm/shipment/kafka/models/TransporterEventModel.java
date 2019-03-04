/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.kafka.models;

import com.securescm.shipment.entities.Country;
import com.securescm.shipment.entities.Transporter;
import java.util.Date;
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
public class TransporterEventModel {

    private Integer id;
    private String name;
    private String logo;
    private String physicalAddress;
    private String email;
    private String city;
    private String region;
    private int postalCode;
    private String countryCode;
    private String countryName;
    private String phone;
    private String fax;
    private Date dateCreated;
    private Date dateDeleted;
    private Date dateLastUpdated;        
    

    public static TransporterEventModel map(Transporter provider, Country country) {
        TransporterEventModel model = new TransporterEventModel();
        model.setId(provider.getId());
        model.setName(provider.getName());
        model.setLogo(provider.getLogo());
        model.setPhysicalAddress(provider.getAddress().getAddress());
        model.setEmail(provider.getAddress().getEmail());
        model.setCity(provider.getAddress().getCity());
        model.setRegion(provider.getAddress().getRegion());
        model.setPostalCode(provider.getAddress().getPostalcode());
        model.setCountryCode(country.getCountryCode());
        model.setCountryName(country.getCountry_name());
        model.setPhone(provider.getAddress().getPhone());
        model.setFax(provider.getAddress().getFax());
        model.setDateCreated(provider.getDateCreated());
        model.setDateLastUpdated(provider.getDateLastUpdated());
        model.setDateDeleted(provider.getDateDeleted());
        return model;
    }
}


