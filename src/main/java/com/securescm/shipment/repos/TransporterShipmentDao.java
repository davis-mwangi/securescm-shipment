/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.repos;

import com.securescm.shipment.entities.Transporter;
import com.securescm.shipment.entities.TransporterShipment;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author david
 */
public interface TransporterShipmentDao extends JpaRepository<TransporterShipment, Integer> {
    boolean existsByName(String name);
    @Query(value="SELECT t FROM TransporterShipment t WHERE t.id = :id")
    @Override
    TransporterShipment getOne(@Param("id")Integer id);
    List<TransporterShipment>findByTransporter(Transporter transporter);
    Page<TransporterShipment>findByTransporter(Transporter transporter, Pageable pageable);
    boolean existsByIdAndTransporter(Integer id, Transporter transporter);
}
