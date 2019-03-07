/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
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
@JsonIgnoreProperties(ignoreUnknown=true)
public class RetailerModel {
 private Integer id;
    private String name;
    private String physicalAddress;
    private String postalAddress;
    private String postalCode;
    private String city;
    private String emailAddress;
    private String phoneNumber;
    private String country;
    private String taxNumber;
    private String taxDocUrl;
    private String regNumber;
    private String regNumberDocUrl;
    private Item category;
    private String imageUrl;
    private long insurance;
    private Item status;
    private String contactFirstName;
    private String contactLastName;
    private String contactPhoneNumber;
    private String contactEmailAddress;
    private String contactDesignation;
    private long createdBy;
    private Date dateDeleted;
    private Date dateCreated;
    private Date dateLastUpdated;

}
