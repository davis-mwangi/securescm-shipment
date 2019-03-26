/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.securescm.shipment.model.Status;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author david
 */
public class Util<T> {
    
   

    public static final String SB_FILE_PATH = "/usr/local/securescm/files/";

    public static String getIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

    public static String toJson(Object entity) {
        String json = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            json = mapper.writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    public T fromJson(String json, Class<T> type) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PrivateKey getPrivateKey(String filename) {
        PrivateKey privateKey = null;
        try {
            byte[] keyBytes = Files.readAllBytes(Paths.get(filename));
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            privateKey = kf.generatePrivate(spec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return privateKey;
    }
    
    public ResponseEntity process(String baseUrl,String urlPart, HttpMethod method, 
            HashMap<String, Object> data, HttpHeaders headers,Logger log){
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String>  result;
        try {
            HttpEntity<HashMap<String, Object>> entity = new HttpEntity<>(data, headers);
            result = restTemplate.exchange(baseUrl+urlPart,method,entity,String.class);
        } catch (final HttpClientErrorException e) {
            log.error(e.getResponseBodyAsString());
            log.error(e.getMessage());
            result = new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }
        log.info(result.getBody());
        return result;
    }
    
    public static boolean isPost(HttpServletRequest request) {
        return "POST".equals(request.getMethod());
    }
    
    public static ResponseEntity getResponse(Status status, Object entity){
        return ResponseEntity.ok().body(status.getCode() == Response.SUCCESS.status().getCode() ? (entity != null ? entity : new ResultResponse(status)) : new ResultResponse(status));
    }
    public static ResponseEntity getResponse(Status status){
        return getResponse(status,null);
    }
    
    
    public static Pageable getPageable(int page, int size,String direction, String orderBy){
        Sort sort;
        if (direction.equals("ASC")) {
            sort = new Sort(Sort.Direction.ASC, orderBy);
        } else {
            sort = new Sort(Sort.Direction.DESC, orderBy);
        }
        return PageRequest.of(page, size, sort);
    }

    public static PagedResponse getResponse(Page page, List content){
        PagedResponse response;
        if (page.getNumberOfElements() == 0) {
            response = new PagedResponse<>(Response.SUCCESS.status(), Collections.emptyList(), page.getNumber(),
                    page.getSize(), page.getTotalElements(), page.getTotalPages(), page.isLast());
        } else {
            response = new PagedResponse<>(Response.SUCCESS.status(), content, page.getNumber(),
                    page.getSize(), page.getTotalElements(), page.getTotalPages(), page.isLast());
        }
        return response;
    }
    
    
    

}





