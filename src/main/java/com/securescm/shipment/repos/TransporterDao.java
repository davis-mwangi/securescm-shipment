/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.repos;

import com.securescm.shipment.entities.Transporter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author david
 */
@Repository
public interface TransporterDao extends JpaRepository<Transporter,Integer>{
    @Query(value="select p from Transporter p where p.id = :id")
    Transporter getOneServiceProvider(@Param("id") Integer id);
    boolean existsByName(String name);
}
