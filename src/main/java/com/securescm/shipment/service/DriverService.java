/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.service;

import com.securescm.shipment.model.UserModel;
import com.securescm.shipment.payload.DriverRequest;
import com.securescm.shipment.util.SingleItemResponse;

/**
 *
 * @author david
 */
public interface DriverService {
    public SingleItemResponse createUpdateDriver(DriverRequest request, UserModel userModel);
    public SingleItemResponse deleteDriver(Integer id);
    //public PagedResponse<TransporterModel> getAllTransporters(String direction, String orderBy, int page, int size);
    public SingleItemResponse findDriver(Integer id);
    
    
}
