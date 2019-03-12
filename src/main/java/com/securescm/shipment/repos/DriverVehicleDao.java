/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.repos;

import com.securescm.shipment.entities.Driver;
import com.securescm.shipment.entities.DriverVehicle;
import com.securescm.shipment.entities.DriverVehicleStatus;
import com.securescm.shipment.entities.Transporter;
import com.securescm.shipment.entities.Vehicle;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author david
 */
public interface DriverVehicleDao extends JpaRepository<DriverVehicle, Integer>{
    boolean existsByDriverAndVehicleAndStatus(Driver driver, Vehicle vehicle, DriverVehicleStatus status);
    List<DriverVehicle> findByDriver(Driver driver);
    DriverVehicle findByDriverAndStatus(Driver driver, DriverVehicleStatus status);
    boolean existsByDriverAndStatus(Driver driver,  DriverVehicleStatus status);
    
    Page<DriverVehicle>findByTransporterAndStatus(Transporter transporter,DriverVehicleStatus status, Pageable pageabale);
}
