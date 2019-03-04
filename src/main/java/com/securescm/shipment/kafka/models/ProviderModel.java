/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.kafka.models;

import java.util.Date;
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
public class ProviderModel {
 
    
    private Integer id;

    private String name;

    private String code;

    private Date dateCreated;

    private int createdBy;

    private Date dateLastUpdated;

    private Date dateDeleted;

    private String providerType;

    private String providerLogo;

    private String physicalAdress;

    private String city;

    private String country;

    private String providerEmail;

    private String providerPhone;

    private String providerFax;

    private int providerPostalCode;

}
