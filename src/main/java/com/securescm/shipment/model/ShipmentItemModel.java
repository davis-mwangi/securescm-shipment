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
import com.securescm.shipment.entities.ShipmentItemStatus;
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

    private ShipCustom shipment;

    private double quantity;
    private double loadedQuantity;
    private double receivedQuantity;
    private double assignedQuantity;

    private Retailer retailer;
    
    private Provider provider;

    private Date orderDate;

    private Date orderRequiredDate;
    
    private ShipmentItemStatus status;

    public static ShipmentItemModel map(ShipmentItem sim) {
        ShipmentItemModel model = new ShipmentItemModel();
        model.setId(sim.getId());
        model.setProduct(sim.getProduct());
        model.setQuantity(sim.getQuantity());
        model.setAssignedQuantity(sim.getAssignedQuantity());
        model.setLoadedQuantity(sim.getLoadedQuantity());
        model.setReceivedQuantity(sim.getReceivedQuantity());
        model.setShipment(new ShipCustom(
                sim.getShipment().getId(),
                sim.getShipment().getCode(),
                sim.getShipment().getName(),
                sim.getShipment().getAddress() + ", " + 
                        sim.getShipment().getCity() + ", " + 
                        sim.getShipment().getCountry().getCountry_name(),
                sim.getShipment().getShipmentDate()));
        
        model.setRetailer(sim.getOrderItem().getRetailer());
        model.setStatus(sim.getStatus());
        model.setProvider(sim.getOrderItem().getProvider());
        model.setOrderDate(null);
        model.setOrderRequiredDate(sim.getOrderItem().getDateRequired());

        return model;

    }

}
