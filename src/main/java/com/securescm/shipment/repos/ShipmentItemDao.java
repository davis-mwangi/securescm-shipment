/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.repos;

import com.securescm.shipment.entities.OrderItem;
import com.securescm.shipment.entities.Provider;
import com.securescm.shipment.entities.Retailer;
import com.securescm.shipment.entities.Shipment;
import com.securescm.shipment.entities.ShipmentItem;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author david
 */
@Repository
public interface  ShipmentItemDao extends  JpaRepository<ShipmentItem,Integer>{
    @Query(value="SELECT s FROM ShipmentItem s WHERE s.id = :id")
    ShipmentItem getOneShipmentItem (@Param("id") Integer id);
    
    boolean existsByShipmentAndOrderItem(Shipment shipment, OrderItem orderItem);
    
    List<ShipmentItem>findByShipment(Shipment shipment);
    
    Page<ShipmentItem>findByShipment(Shipment shipment, Pageable pageable);
    Page<ShipmentItem>findByProvider(Provider provider, Pageable pageable);
    Page<ShipmentItem>findByRetailer(Retailer retailer, Pageable pageable);
    
    List<ShipmentItem>findByProvider(Provider provider);
    List<ShipmentItem>findByRetailer(Retailer retailer);
}
