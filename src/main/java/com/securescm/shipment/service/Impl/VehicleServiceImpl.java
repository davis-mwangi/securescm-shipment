/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.service.Impl;

import com.securescm.shipment.entities.Driver;
import com.securescm.shipment.entities.DriverVehicle;
import com.securescm.shipment.entities.DriverVehicleStatus;
import com.securescm.shipment.entities.Transporter;
import com.securescm.shipment.entities.Vehicle;
import com.securescm.shipment.entities.VehicleType;
import com.securescm.shipment.model.DriverVehicleModel;
import com.securescm.shipment.model.ItemName;
import com.securescm.shipment.model.UserModel;
import com.securescm.shipment.model.VehicleModel;
import com.securescm.shipment.payload.DriverVehicleRequest;
import com.securescm.shipment.payload.VehicleRequest;
import com.securescm.shipment.repos.DriverDao;
import com.securescm.shipment.repos.DriverVehicleDao;
import com.securescm.shipment.repos.VehicleDao;
import com.securescm.shipment.service.VehicleService;
import com.securescm.shipment.util.AppConstants;
import com.securescm.shipment.util.Response;
import com.securescm.shipment.util.SingleItemResponse;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author david
 */
@Service
public class VehicleServiceImpl implements VehicleService{
    Logger log=  LoggerFactory.getLogger(VehicleServiceImpl.class);
    
    @Autowired
    private VehicleDao vehicleDao;
    
    @Autowired
    private DriverVehicleDao driverVehicleDao;
    
    @Autowired
    private DriverDao driverDao;
    
    @Override
    public SingleItemResponse createUpdateVehicle(VehicleRequest request, UserModel userModel) {
         Vehicle vehicle = new Vehicle();
         Date now =  new Date();
         if(request.getId() != null){
             vehicle =  vehicleDao.findOne(request.getId());
             if(request.getDriver() !=null){
                 vehicle.setDriver(new Driver(request.getDriver())); 
             }
             vehicle.setDateLastUpdated(now);
         }
         vehicle.setRegistrationNo(request.getRegistrationNo());
         vehicle.setType(new VehicleType(request.getType()));
         vehicle.setTransporter(new Transporter(userModel.getStakeholder().getId()));
         vehicle.setDateCreated(now);
         
         vehicleDao.save(vehicle);
         return new SingleItemResponse(Response.SUCCESS.status(), new ItemName(vehicle.getId()));
    }

    @Override
    public SingleItemResponse deleteVehicle(Integer id) {
        Vehicle vehicle = vehicleDao.getOne(id);
        SingleItemResponse singleItemResponse;
        if(vehicle != null){
          vehicle.setDateDeleted(new Date());
          vehicleDao.save(vehicle);
          singleItemResponse =  new SingleItemResponse(Response.SUCCESS.status(),null);
        }else{
           singleItemResponse = new SingleItemResponse(Response.SHIPMENT_ITEM_NOT_FOUND.status(), null);
        }
       
        return singleItemResponse;
    }

    @Override
    public SingleItemResponse findVehicle(Integer id) {
       Vehicle vehicle = vehicleDao.getOne(id); 
       return new SingleItemResponse(Response.SUCCESS.status(), VehicleModel.map(vehicle));
    }
    
    
    //////////////////////////VEHICLE DRIVER ASSIGNMENT/////////////////////////

    @Override
    public SingleItemResponse createUpdateDriverVehicle(DriverVehicleRequest request, UserModel userModel) {
       DriverVehicle dv =  new DriverVehicle();
       dv.setTransporter(new Transporter(userModel.getStakeholder().getId()));
       dv.setDriver(new Driver(request.getDriver()));
       dv.setDateAssigned(new Date());
       dv.setVehicle(new Vehicle(request.getVehicle()));
       dv.setStatus(new DriverVehicleStatus(AppConstants.DRIVER_ACTIVE));
       driverVehicleDao.save(dv);
       
       Vehicle  vehicle = vehicleDao.getOne(request.getVehicle());
       vehicle.setDriver(new Driver(request.getDriver()));
       vehicleDao.save(vehicle);
       
        List<DriverVehicle> driverList = driverVehicleDao.findByDriver(new Driver(request.getDriver()));
        for (DriverVehicle driverV : driverList) {
           // log.info(driverV.getVehicle().getId() + " " + dv.getVehicle().getId());
          //  log.info(Boolean.toString(!Objects.equals(driverV.getVehicle().getId(), dv.getVehicle().getId())));
            if (!Objects.equals(driverV.getVehicle().getId(), dv.getVehicle().getId())) {
                driverV.setStatus(new DriverVehicleStatus(AppConstants.DRIVER_REASSIGNED));
                driverV.setAssignmentEndDate(new Date());
                driverVehicleDao.save(driverV);
            }
        }
        return new SingleItemResponse(Response.SUCCESS.status(), new ItemName(dv.getId()));
    }

    @Override
    public SingleItemResponse terminateDriver(Integer id) {
        DriverVehicle dv  =  driverVehicleDao.findByDriverAndStatus(
                new Driver(id), new DriverVehicleStatus(1));
        if(dv != null){
            dv.setStatus(new DriverVehicleStatus(AppConstants.DRIVER_TERMINATED));
            driverVehicleDao.save(dv);
        }
      return new SingleItemResponse(Response.SUCCESS.status(), null);  
    }

    @Override
    public SingleItemResponse findDriverVehicle(Integer id) {
        DriverVehicle dv  =  driverVehicleDao.getOne(id);
        return new SingleItemResponse(Response.SUCCESS.status(), DriverVehicleModel.map(dv)); 
    }
    
    
}
