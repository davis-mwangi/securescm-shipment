/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.model;

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
public class RetModel {
    private Integer id;
    private String name;
    private Date dateLastUpdated;
    private Date dateDeleted;
    
}
