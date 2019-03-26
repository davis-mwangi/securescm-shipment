/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.repos;

import com.securescm.shipment.entities.Transporter;
import com.securescm.shipment.entities.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author david
 */
public interface VehicleDao extends JpaRepository<Vehicle, Integer>{
    @Query(value="SELECT v FROM Vehicle v WHERE v.id = :id")
    Vehicle findOne(@Param("id")Integer id);
    boolean existsByRegistrationNo(String regNo);
    Page<Vehicle>findByTransporterAndDateDeletedIsNullAndDriverIsNotNull(Transporter transporter, Pageable pageable);
}
