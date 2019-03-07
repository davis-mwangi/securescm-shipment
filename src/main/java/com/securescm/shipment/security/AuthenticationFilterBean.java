/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.securescm.shipment.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.securescm.shipment.model.Item;
import com.securescm.shipment.model.UserModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

/**
 *
 * @author david
 */
@Component
public class AuthenticationFilterBean extends GenericFilterBean {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationFilterBean.class);
    private static final String HEADER_STRING = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    public static final String SB_FILE_PATH = "/usr/local/securescm/files/";
    private static final String PUBLIC_KEY_FILENAME = SB_FILE_PATH+"secure_scm_public_key.der";
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
      HttpServletRequest httpRequest = ((HttpServletRequest)request);
      String token =  httpRequest.getHeader(HEADER_STRING);
      Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
      
      if(token != null){
         Claims claims =  Jwts.parser()
                 .setSigningKey(getPublicKey(PUBLIC_KEY_FILENAME))
                 .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                 .getBody();
         
        UserModel userModel =  new ObjectMapper().convertValue(claims.get("user"),
                UserModel.class);
        if(userModel != null) {
         List<GrantedAuthority>authorities = new ArrayList<>();
         if(userModel.getPermissions() != null){
           for (Item permission:  userModel.getPermissions()){
             authorities.add(new SimpleGrantedAuthority(permission.getCode()));
           }
         }
         authentication = new UsernamePasswordAuthenticationToken(new ApiPrincipal(userModel), null, authorities);
        }
      }
      SecurityContextHolder.getContext().setAuthentication(authentication);
      chain.doFilter(request, response);
    }
    
    //Get decode public key
    public static PublicKey getPublicKey(String filename) {
        PublicKey publicKey = null;
        try {
            byte[] keyBytes = Files.readAllBytes(Paths.get(filename));

            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            publicKey = kf.generatePublic(spec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return publicKey;
    }
}
