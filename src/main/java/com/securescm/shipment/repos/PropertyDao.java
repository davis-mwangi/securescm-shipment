/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.repos;


import com.securescm.shipment.entities.Property;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author david
 */
public interface PropertyDao extends JpaRepository<Property, Integer> {
    
}