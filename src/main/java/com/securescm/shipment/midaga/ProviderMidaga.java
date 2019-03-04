/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.midaga;

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
public class ProviderMidaga {
   private int id; 
   private String businessName;
   private String kraPin;
   private String countryCode;
   private String email;
   private String location;
   private String town;
   private Spoc spoc;
   
    
}
