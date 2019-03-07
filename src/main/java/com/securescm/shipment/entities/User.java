/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class User {

    @Id
    private Integer id;
    private String firstName;
    private String lastName;
    private long staffId;
    private long stakeholder;
    
    private  String username;

    @JsonIgnore
    private  String email;

    public User(Integer id) {
        this.id = id;
    }
}