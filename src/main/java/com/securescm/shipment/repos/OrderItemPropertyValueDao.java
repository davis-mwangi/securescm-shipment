/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.repos;

import com.securescm.shipment.entities.OrderItemPropertyValue;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author david
 */
public interface OrderItemPropertyValueDao extends JpaRepository<OrderItemPropertyValue, Integer>{
    List<OrderItemPropertyValue>findByOrderItem(int orderItem);
}
