/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.repos;

import com.securescm.shipment.entities.Shipment;
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
     
    @Query(value="select p from Shipment p where p.dateDeleted= null")
     @Override
    Page<Shipment> findAll(Pageable pageable);
    
    public Shipment findByIdAndDateDeletedIsNull(Integer id);
}
