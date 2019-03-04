/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.security;

import com.securescm.shipment.model.Item;
import com.securescm.shipment.model.UserModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author david
 */
public class ApiPrincipal  implements UserDetails{
    private UserModel user;

     public ApiPrincipal(UserModel user){
         this.user = user;
     }
     
     public UserModel getUser(){
       return user;
     }
    
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      List<GrantedAuthority>authorities =  new ArrayList<>();
      for(Item permission: user.getPermissions()){
        authorities.add(new SimpleGrantedAuthority(permission.getCode()));
      }
      return authorities;
    }

    @Override
    public String getPassword() {
       return null;
    }

    @Override
    public String getUsername() {
      return null;
    }

    @Override
    public boolean isAccountNonExpired() {
      return true;
    }

    @Override
    public boolean isAccountNonLocked() {
          return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
       return true;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
    
}
