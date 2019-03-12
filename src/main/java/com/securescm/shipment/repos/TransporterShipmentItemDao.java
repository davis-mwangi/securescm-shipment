/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.repos;

import com.securescm.shipment.entities.ShipmentItem;
import com.securescm.shipment.entities.TransporterShipment;
import com.securescm.shipment.entities.TransporterShipmentItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author david
 */
public interface TransporterShipmentItemDao extends JpaRepository<TransporterShipmentItem, Integer> {

    @Query(value = "SELECT t FROM TransporterShipmentItem t WHERE t.id = :id")
    @Override
    TransporterShipmentItem getOne(@Param("id")Integer id);
    
    List<TransporterShipmentItem>findByShipmentItem(ShipmentItem item);
    List<TransporterShipmentItem>findByTransporterShipment(TransporterShipment ts);
}
