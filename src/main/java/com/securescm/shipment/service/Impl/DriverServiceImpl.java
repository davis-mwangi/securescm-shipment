/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.service.Impl;

import com.securescm.shipment.entities.Driver;
import com.securescm.shipment.entities.Transporter;
import com.securescm.shipment.model.DriverModel;
import com.securescm.shipment.model.ItemName;
import com.securescm.shipment.model.UserModel;
import com.securescm.shipment.payload.DriverRequest;
import com.securescm.shipment.repos.DriverDao;
import com.securescm.shipment.service.DriverService;
import com.securescm.shipment.util.Response;
import com.securescm.shipment.util.SingleItemResponse;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author david
 */
@Service
public class DriverServiceImpl implements DriverService{
    
    @Autowired
    private DriverDao driverDao;

    @Override
    public SingleItemResponse createUpdateDriver(DriverRequest request, UserModel userModel) {
        Driver driver = new Driver();
        Date now =  new Date();
        if(request.getId() != null){
            driver =  driverDao.getOne(request.getId());
            driver.setDateLastUpdated(now);
        }
        driver.setFirstName(request.getFirstName());
        driver.setLastName(request.getLastName());
        driver.setLicenceNo(request.getLicenceNo());
        driver.setPhoneNumber(request.getPhoneNumber());
        driver.setNationalId(request.getNationalId());
        driver.setDateCreated(now);
        driver.setTransporter(new Transporter(userModel.getStakeholder().getId()));
       driverDao.save(driver);
       return new SingleItemResponse(Response.SUCCESS.status(), new ItemName(driver.getId()));
       
    }

    @Override
    public SingleItemResponse deleteDriver(Integer id) {
       Driver driver =  driverDao.getOne(id);
        SingleItemResponse singleItemResponse;
        if(driver != null){
          driver.setDateDeleted(new Date());
          driverDao.save(driver);
          singleItemResponse =  new SingleItemResponse(Response.SUCCESS.status(),null);
        }else{
           singleItemResponse = new SingleItemResponse(Response.SHIPMENT_ITEM_NOT_FOUND.status(), null);
        }
       
        return singleItemResponse;
    }

    @Override
    public SingleItemResponse findDriver(Integer id) {
       Driver driver =  driverDao.getOne(id);   
     return new SingleItemResponse(Response.SUCCESS.status(), DriverModel.map(driver));
    }
    
}
