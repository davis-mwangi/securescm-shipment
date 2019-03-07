/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author david
 */
@Configuration
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter{
    @Autowired
    private AuthenticationFilterBean authenticationFilterBean;
    
    @Autowired
    private ClientUserDetailsService userDetailService;
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
      DaoAuthenticationProvider authenticationProvider =  new DaoAuthenticationProvider();
      authenticationProvider.setUserDetailsService(userDetailService);
      return authenticationProvider;
    }

   

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
         auth.authenticationProvider(authenticationProvider());
    }
    
     
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.csrf().disable().authorizeRequests()
               .anyRequest().authenticated()
               .and()
               .addFilterBefore(authenticationFilterBean, UsernamePasswordAuthenticationFilter.class)
               .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    
}
