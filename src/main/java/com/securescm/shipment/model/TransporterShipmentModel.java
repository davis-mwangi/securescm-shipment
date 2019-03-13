/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.model;

import com.securescm.shipment.entities.TransporterShipment;
import com.securescm.shipment.entities.TransporterShipmentItem;
import com.securescm.shipment.entities.TransporterShipmentStatus;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author david
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransporterShipmentModel {
    private int id;
    private String name;
    private ItemName transporter;
    private TransporterShipmentStatus status;
    private int trips;
    private VehicleModel vehicle;
    private List<TransporterShipmentItemModel> items;
    
    public static TransporterShipmentModel map(TransporterShipment ts, List<TransporterShipmentItem> transShipItms) {
        TransporterShipmentModel model = new TransporterShipmentModel();
        model.setId(ts.getId());
        model.setName(ts.getName());
        model.setStatus(ts.getStatus());
        model.setTrips(ts.getTrips());
        model.setTransporter(new ItemName(
                ts.getTransporter().getId(), ts.getTransporter().getName()));
        model.setVehicle(new VehicleModel(
                ts.getVehicle().getId(),
                ts.getVehicle().getRegistrationNo(),
                null,
                new DriverModel(
                        ts.getVehicle().getDriver().getId(),
                        ts.getVehicle().getDriver().getFirstName(),
                        ts.getVehicle().getDriver().getLastName(),
                        ts.getVehicle().getDriver().getNationalId(),
                        ts.getVehicle().getDriver().getPhoneNumber(),
                        ts.getVehicle().getDriver().getLicenceNo(), null),
                ts.getVehicle().getType(),
                null));
        List<TransporterShipmentItemModel> items = new ArrayList<>();
        if (transShipItms != null) {
            for (TransporterShipmentItem item : transShipItms) {
                TransporterShipmentItemModel itModel = new TransporterShipmentItemModel();
                itModel.setId(item.getId());
                itModel.setQuantity(item.getQuantity());
                itModel.setShipmentItem(ShipmentItemModel.map(item.getShipmentItem()));
                itModel.setShipment(new ItemName(item.getShipment().getId(), item.getShipment().getName()));
                itModel.setStatus(item.getStatus());

                items.add(itModel);
            }
          model.setItems(items);   
        }else {
          model.setItems(null);
        }

       
        return model;
    }
}
