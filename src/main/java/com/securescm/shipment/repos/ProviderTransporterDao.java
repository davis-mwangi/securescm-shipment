/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.repos;

import com.securescm.shipment.entities.Provider;
import com.securescm.shipment.entities.ProviderTransporter;
import com.securescm.shipment.entities.Shipment;
import com.securescm.shipment.entities.Transporter;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author david
 */
@Repository
public interface ProviderTransporterDao extends JpaRepository<ProviderTransporter, Integer> {
    boolean existsByProviderAndTransporter(Provider provider, Transporter transporter);
    
    @Query(value="SELECT p FROM ProviderTransporter p WHERE p.id = :id")
    ProviderTransporter findProviderTransporter(@Param("id") Integer id);
    
    List<ProviderTransporter>findByProvider(Provider provider);
    Page<ProviderTransporter> findAllByProvider(Provider provider,Pageable pageable);
}
