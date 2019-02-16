/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.model;

import com.securescm.shipment.entities.Product;
import com.securescm.shipment.entities.Provider;
import com.securescm.shipment.entities.Retailer;
import com.securescm.shipment.entities.ShipmentItem;
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
public class ShipmentItemModel {

    private int id;

    private Product product;

    private ShipItem shipment;

    private double quantity;

    private Retailer retailer;
    
    private Provider provider;

    private Date orderDate;

    private Date orderRequiredDate;

    public static ShipmentItemModel map(ShipmentItem sim) {
        ShipmentItemModel model = new ShipmentItemModel();
        model.setId(sim.getId());
        model.setProduct(sim.getProduct());
        model.setQuantity(sim.getQuantity());
        model.setShipment(new ShipItem(
                sim.getShipment().getId(),
                sim.getShipment().getCode(),
                sim.getShipment().getName(),
                sim.getShipment().getAddress() + ", " + 
                        sim.getShipment().getCity() + ", " + 
                        sim.getShipment().getCountry().getCountry_name(),
                sim.getShipment().getShipmentDate()));
        
        model.setRetailer(sim.getOrderItem().getOrder().getRetailer());
        model.setProvider(sim.getOrderItem().getOrder().getProvider());
        model.setOrderDate(sim.getOrderItem().getOrder().getOrderDate());
        model.setOrderRequiredDate(sim.getOrderItem().getOrder().getRequiredDate());

        return model;

    }

}
