/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.service;

import com.securescm.shipment.entities.Shipment;
import com.securescm.shipment.payload.ShipmentRequest;
import com.securescm.shipment.util.PagedResponse;
import com.securescm.shipment.util.SingleItemResponse;

/**
 *
 * @author david
 */
public interface ShipmentService {
    public SingleItemResponse createUpdateShipment(ShipmentRequest request);
    public SingleItemResponse deleteShipment(Integer id);
    public PagedResponse<Shipment> getAllShipments(String direction, String orderBy, int page, int size);
    public SingleItemResponse findOneShipment(Integer id);
    
   
}
