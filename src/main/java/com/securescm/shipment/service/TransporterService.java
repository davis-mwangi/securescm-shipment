/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.service;

import com.securescm.shipment.entities.ProviderTransporter;
import com.securescm.shipment.model.TransporterModel;
import com.securescm.shipment.model.UserModel;
import com.securescm.shipment.payload.ProviderTransporterRequest;
import com.securescm.shipment.payload.TransporterRequest;
import com.securescm.shipment.util.ListItemResponse;
import com.securescm.shipment.util.PagedResponse;
import com.securescm.shipment.util.SingleItemResponse;

/**
 *
 * @author david
 */
public interface TransporterService {
    public SingleItemResponse createUpdateTransporter(TransporterRequest request, UserModel userModel);
    public SingleItemResponse deleteTransporter(Integer id);
    public PagedResponse<TransporterModel> getAllTransporters(String direction, String orderBy, int page, int size);
    public SingleItemResponse findOneTransporter(Integer id);
    
    public ListItemResponse getAllTransporterTypes();
    
    public SingleItemResponse createProviderTransporter(UserModel userModel,ProviderTransporterRequest request);
    public SingleItemResponse deleteProviderTransporter(Integer id);
    public PagedResponse<ProviderTransporter>getAllProviderTransporter(String direction, String orderBy, int page, int size);
    public SingleItemResponse findOneProviderTransporter(Integer id);
    
    public PagedResponse<ProviderTransporter>getAllTransportersForProvider( UserModel userModel, String direction, String orderBy, int page, int size);
}
