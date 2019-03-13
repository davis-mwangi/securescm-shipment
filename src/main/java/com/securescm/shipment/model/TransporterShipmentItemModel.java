/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.model;

import com.securescm.shipment.entities.TransporterShipmentItem;
import com.securescm.shipment.entities.TransporterShipmentItemStatus;
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
public class TransporterShipmentItemModel {
    private Integer id;
    private ItemName shipment;
    private ShipmentItemModel shipmentItem;
    private double quantity;
    private TransporterShipmentItemStatus status;
    
    public static TransporterShipmentItemModel map(TransporterShipmentItem tsi){
        TransporterShipmentItemModel model =  new TransporterShipmentItemModel();
        model.setId(tsi.getId());
        model.setQuantity(tsi.getQuantity());
        model.setShipment(new ItemName(
                tsi.getShipment().getId(),
                tsi.getShipment().getName()));
        model.setShipmentItem(ShipmentItemModel.map(tsi.getShipmentItem()));
        model.setStatus(tsi.getStatus());
    return model;   
    }
    
}
