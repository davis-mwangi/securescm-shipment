/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.kafka;




import com.securescm.shipment.kafka.models.OrderEvent;
import com.securescm.shipment.kafka.models.OrderItemModel;
import com.securescm.shipment.kafka.models.ProductEvent;
import com.securescm.shipment.kafka.models.ProductModel;
import com.securescm.shipment.kafka.models.ProviderEvent;
import com.securescm.shipment.kafka.models.ProviderModel;
import com.securescm.shipment.kafka.models.RetailerEvent;
import com.securescm.shipment.model.RetailerModel;
import com.securescm.shipment.service.KafkaService;
import com.securescm.shipment.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

/**
 *
 * @author david
 */
public class MessageListener {
    Logger log = LoggerFactory.getLogger(MessageListener.class.getName());

    @Autowired
    private KafkaService kafkaService;

    @KafkaListener(topics = "${topic.provider}")
    public void listenRetailer(String message) {
        log.info("Received message: {}", message);

        ProviderEvent providerEvent = (ProviderEvent) new Util().fromJson(message, ProviderEvent.class);
        ProviderModel providerModel =  providerEvent.getData();
        if(providerEvent.getEvent().equalsIgnoreCase("createUpdate")){
            kafkaService.createUpdateProvider(providerModel);
        }
        
        if(providerEvent.getEvent().equalsIgnoreCase("delete")){
           kafkaService.deleteProvider(providerModel);
        }
        
    }
    
    @KafkaListener(topics = "${topic.product}")
    public void listenProduct(String message) {
        log.info("Received message: {}", message);

        ProductEvent productEvent = (ProductEvent) new Util().fromJson(message, ProductEvent.class);
        ProductModel productModel =  productEvent.getData();
        if(productEvent.getEvent().equalsIgnoreCase("createUpdate")){
            kafkaService.createUpdateProduct(productModel);
        }
        
        if(productEvent.getEvent().equalsIgnoreCase("delete")){
           kafkaService.deleteProduct(productModel);
        }
        
    }
    
    @KafkaListener(topics = "${topic.order}")
    public void listenOrder(String message) {
        log.info("Received message: {}", message);

        OrderEvent orderEvent = (OrderEvent) new Util().fromJson(message, OrderEvent.class);
        OrderItemModel orderItemModel =  orderEvent.getData();
        
        if(orderEvent.getEvent().equalsIgnoreCase("ack")){
            kafkaService.createOrderItem(orderItemModel);
        }   
    }
    
    @KafkaListener(topics = "${topic.retailer}")
    public void listenRetailers(String message) {
        log.info("Received message: {}", message);

        RetailerEvent retailerEvent = (RetailerEvent) new Util().fromJson(message, RetailerEvent.class);
        RetailerModel retailerModel =  retailerEvent.getData();
        if(retailerEvent.getEvent().equalsIgnoreCase("createUpdate")){
            kafkaService.createUpdateRetailer(retailerModel);
        }  
        
        if(retailerEvent.getEvent().equalsIgnoreCase("delete")){
           kafkaService.deleteRetailer(retailerModel);
        }
    }


   
}