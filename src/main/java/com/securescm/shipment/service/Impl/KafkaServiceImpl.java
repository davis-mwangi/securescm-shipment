/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.service.Impl;

import com.securescm.shipment.entities.OrderItem;
import com.securescm.shipment.entities.Product;
import com.securescm.shipment.entities.Provider;
import com.securescm.shipment.entities.Retailer;
import com.securescm.shipment.kafka.models.ProductModel;
import com.securescm.shipment.kafka.models.ProviderModel;
import com.securescm.shipment.kafka.models.OrderItemModel;
import com.securescm.shipment.model.RetailerModel;
import com.securescm.shipment.model.Status;
import com.securescm.shipment.repos.OrderDao;
import com.securescm.shipment.repos.OrderItemDao;
import com.securescm.shipment.repos.ProductDao;
import com.securescm.shipment.repos.ProviderDao;
import com.securescm.shipment.repos.RetailerDao;
import com.securescm.shipment.service.KafkaService;
import com.securescm.shipment.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author david
 */
@Service
public class KafkaServiceImpl implements KafkaService {

    private Logger log = LoggerFactory.getLogger(KafkaServiceImpl.class);

    @Autowired
    private ProviderDao providerDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private RetailerDao retailerDao;

    @Override
    public Status createUpdateProvider(ProviderModel providerModel) {
        Provider provider = new Provider();
        provider.setId(providerModel.getId());
        provider.setName(providerModel.getName());
        provider.setCode(providerModel.getCode());

        providerDao.save(provider);

        return Response.SUCCESS.status();
    }

    @Override
    public Status deleteProvider(ProviderModel providerMdel) {
        Provider provider = providerDao.getOne(providerMdel.getId());

        if (provider != null) {
            providerDao.deleteById(providerMdel.getId());
        } else {
            log.info("Provider not found");
        }
        return Response.SUCCESS.status();
    }

    @Override
    public Status createUpdateProduct(ProductModel productModel) {
        Product product = new Product();
        product.setId(productModel.getId());
        product.setCode(productModel.getCode());
        product.setName(productModel.getName());
        product.setDescription(productModel.getDescription());
        productDao.save(product);
        return Response.SUCCESS.status();
    }

    @Override
    public Status deleteProduct(ProductModel productModel) {
        Product product = productDao.getOne(productModel.getId());

        if (product != null) {
            productDao.deleteById(productModel.getId());
        } else {
            log.info("Product not found");
        }
        return Response.SUCCESS.status();
    }

    @Override
    public Status createOrderItem(OrderItemModel itemModel) { 
                OrderItem item = new OrderItem();
                //Convert Long to Integer
                Long longId = itemModel.getId();
                Integer intId = longId.intValue();
                
                item.setId(intId);
                item.setRetailer(new Retailer(itemModel.getRetailer().getId()));
                item.setProduct(new Product(itemModel.getProduct().getId()));
                item.setQuantity(itemModel.getQuantity());
                item.setUnitPrice(itemModel.getPrice());
                item.setUom(itemModel.getUom().getCode());
                item.setDateRequired(itemModel.getDateRequired());
                item.setProvider(new Provider(itemModel.getSupplier().getId()));
                

                orderItemDao.save(item);
          
        return Response.SUCCESS.status();
    }

    @Override
    public Status createUpdateRetailer(RetailerModel model) {
        Retailer retailer = new Retailer();
        retailer.setId(model.getId());
        retailer.setName(model.getName());
        retailer.setLogicalName(model.getRegNumber());

        retailerDao.save(retailer);
        return Response.SUCCESS.status();
    }

    @Override
    public Status deleteRetailer(RetailerModel model) {
        Retailer retailer = retailerDao.getOne(model.getId());

        if (retailer != null) {
            retailerDao.deleteById(model.getId());
        } else {
            log.info("Retailer not found");
        }
        return Response.SUCCESS.status();
    }
}
