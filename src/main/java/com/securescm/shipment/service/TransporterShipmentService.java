/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.service;

import com.securescm.shipment.entities.TransporterShipmentItem;
import com.securescm.shipment.kafka.models.PropertyValuesModel;
import com.securescm.shipment.model.TransporterShipmentModel;
import com.securescm.shipment.model.UserModel;
import com.securescm.shipment.payload.AssignTransporterItemStoreRequest;
import com.securescm.shipment.payload.AuditTransporterItemRequest;
import com.securescm.shipment.payload.TransporterShipmentItemRequest;
import com.securescm.shipment.payload.TransporterShipmentRequest;
import com.securescm.shipment.util.PagedResponse;
import com.securescm.shipment.util.SingleItemResponse;
import java.util.List;

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
    public PagedResponse<TransporterShipmentModel> getTransporterShipments(UserModel userModel, String direction, String orderBy, int page, int size);
    
    public SingleItemResponse createUpdateTransporterShipmentItem(TransporterShipmentItemRequest request, UserModel userModel) ;
    public SingleItemResponse deleteTransporterShipmentItem(Integer id);
    public SingleItemResponse findTransporterShipmentItem(Integer id);
    public SingleItemResponse auditTransporterShipmentItem(AuditTransporterItemRequest request, UserModel userModel);
    public SingleItemResponse assignTransporterShipmentItemToStore(AssignTransporterItemStoreRequest request, UserModel userModel);
    public List<PropertyValuesModel> getTransporterItemProperties(TransporterShipmentItem  transporterShipmentItem);
}
