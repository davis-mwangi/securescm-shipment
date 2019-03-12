/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.repos;

import com.securescm.shipment.entities.Driver;
import com.securescm.shipment.entities.Transporter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author david
 */
public interface DriverDao extends JpaRepository<Driver, Integer>{
    @Query(value="SELECT d FROM Driver d WHERE d.id = :id")
    @Override
    Driver getOne (@Param("id")Integer id);
    boolean existsByNationalId(String  nationalId);
    Page<Driver>findByTransporterAndDateDeletedIsNull(Transporter transporter, Pageable pageable);
}
