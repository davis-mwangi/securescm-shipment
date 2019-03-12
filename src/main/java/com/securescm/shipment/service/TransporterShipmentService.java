/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.service;

import com.securescm.shipment.model.UserModel;
import com.securescm.shipment.payload.AuditTransporterItemRequest;
import com.securescm.shipment.payload.TransporterShipmentItemRequest;
import com.securescm.shipment.payload.TransporterShipmentRequest;
import com.securescm.shipment.util.SingleItemResponse;

/**
 *
 * @author david
 */
public interface TransporterShipmentService {

    public SingleItemResponse createUpdateTransporterShipment(TransporterShipmentRequest request, UserModel userModel);
    public SingleItemResponse deleteTransporterShipment(Integer id);
    public SingleItemResponse findTransporterShipment(Integer id);
    public SingleItemResponse closeTransporterShipment(Integer id);
     public SingleItemResponse openTransporterShipment(Integer id);
    
    public SingleItemResponse createUpdateTransporterShipmentItem(TransporterShipmentItemRequest request, UserModel userModel) ;
    public SingleItemResponse deleteTransporterShipmentItem(Integer id);
    public SingleItemResponse findTransporterShipmentItem(Integer id);
    public SingleItemResponse auditTransporterShipmentItem(AuditTransporterItemRequest request, UserModel userModel);
    
}
