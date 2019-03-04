/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.kafka.models;


import com.securescm.shipment.model.Item;
import com.securescm.shipment.model.ProductSupplierModel;
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
public class ProductModel {
    private Integer id;
    private String name;
    private String code;
    private Item category;
    private Item codeType;
    private String description;
    private List<ProductSupplierModel>productSupplier;
    private List<String>images;

}
