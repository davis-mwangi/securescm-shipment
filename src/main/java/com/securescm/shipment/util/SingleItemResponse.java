/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.util;

import com.securescm.shipment.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author david
 * @param <T>
 */
@Data 
@NoArgsConstructor
@AllArgsConstructor
public class SingleItemResponse<T> {
    private Status status;
    private  T data;

}
