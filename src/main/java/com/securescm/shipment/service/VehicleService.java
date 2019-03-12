/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.service;

import com.securescm.shipment.model.UserModel;
import com.securescm.shipment.payload.DriverVehicleRequest;
import com.securescm.shipment.payload.VehicleRequest;
import com.securescm.shipment.util.SingleItemResponse;

/**
 *
 * @author david
 */
public interface VehicleService {
    public SingleItemResponse createUpdateVehicle(VehicleRequest request, UserModel userModel);
    public SingleItemResponse deleteVehicle(Integer id);
    public SingleItemResponse findVehicle(Integer id); 
    
    public SingleItemResponse createUpdateDriverVehicle(DriverVehicleRequest request, UserModel userModel);
    public SingleItemResponse terminateDriver(Integer id);
    public SingleItemResponse findDriverVehicle(Integer id);
}
