/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.repos;

import com.securescm.shipment.entities.Provider;
import com.securescm.shipment.entities.Shipment;
import com.securescm.shipment.entities.Transporter;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author david
 */
@Repository
public interface ShipmentDao extends PagingAndSortingRepository<Shipment, Integer>{
     boolean existsByName(String name);
     boolean existsByCode(String code);
     @Query(value =" SELECT s FROM Shipment s WHERE s.id = :id")
     public Shipment getOneShipment(@Param("id")Integer id);
     
    Page<Shipment> findByCreatedByAndDateDeletedIsNull(Provider provider,Pageable pageable);
    
    public Shipment findByIdAndDateDeletedIsNull(Integer id);
    public Shipment findByIdAndCreatedBy(Integer id, Provider provider);
    boolean existsByIdAndCreatedBy(Integer id, Provider provider);
    List<Shipment>findByCreatedByAndDateDeletedIsNull(Provider provider);
    List<Shipment> findByTransporterAndCreatedBy(Transporter transporter, Provider provider);
}
