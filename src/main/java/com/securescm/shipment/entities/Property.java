/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.entities;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author david
 */
@NoArgsConstructor
@Data
@Entity
public class Property {
    @Id
    private Integer id;
    private String name;
    private String code;
    private Date dateCreated;
    private Date dateDeleted;

    public Property(Integer id) {
        this.id = id;
    }
    
    
}
