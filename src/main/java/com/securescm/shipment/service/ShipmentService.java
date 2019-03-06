/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.service;

import com.securescm.shipment.entities.AssignTransporterRequest;
import com.securescm.shipment.model.ShipmentModel;
import com.securescm.shipment.model.UserModel;
import com.securescm.shipment.payload.ApproveTransporterRequest;
import com.securescm.shipment.payload.ShipmentRequest;
import com.securescm.shipment.util.PagedResponse;
import com.securescm.shipment.util.SingleItemResponse;

/**
 *
 * @author david
 */
public interface ShipmentService {
    public SingleItemResponse createUpdateShipment(ShipmentRequest request, UserModel userModel);
    public SingleItemResponse deleteShipment(Integer id);
    public PagedResponse<ShipmentModel> getAllShipments(UserModel userModel, String direction, String orderBy, int page, int size);
    public SingleItemResponse findOneShipment(UserModel userModel,Integer id);
    
    
    public SingleItemResponse assignTransporter(AssignTransporterRequest request);
    public SingleItemResponse approveShipment(UserModel userModel, ApproveTransporterRequest request);
    public SingleItemResponse closeShipment(Integer id);
}
