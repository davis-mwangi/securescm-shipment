/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author david
 */
@NoArgsConstructor
@Data
@Entity
public class PropertyValue {
    @Id
    private Integer id;
    @JoinColumn(name = "property", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Property property;
    private String value;
    private String code;
    @JsonIgnore
    private Date dateCreated;
    @JsonIgnore
    private Date dateDeleted;

    public PropertyValue(Integer id) {
        this.id = id;
    }
    
    
}
