/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.service;

import com.securescm.shipment.kafka.models.OrderItemModel;
import com.securescm.shipment.kafka.models.ProductModel;
import com.securescm.shipment.kafka.models.ProviderModel;
import com.securescm.shipment.model.RetailerModel;
import com.securescm.shipment.model.Status;

/**
 *
 * @author david
 */
public interface KafkaService {
    public Status createUpdateProvider(ProviderModel provider);
    public Status deleteProvider(ProviderModel provider);
    
    public Status createUpdateProduct(ProductModel provider);
    public Status deleteProduct(ProductModel provider);
    
    public Status createOrderItem(OrderItemModel orderModel);
    
    public Status createUpdateRetailer(RetailerModel model);
    public Status deleteRetailer(RetailerModel model);
}
