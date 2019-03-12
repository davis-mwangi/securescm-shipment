/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.payload;

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
public class DriverRequest {    
    private Integer id;
    private String firstName;
    private String lastName;
    private String nationalId;
    private String phoneNumber;
    private String licenceNo;
}
