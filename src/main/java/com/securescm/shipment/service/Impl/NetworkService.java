/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.service.Impl;

import java.util.HashMap;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author david
 */
@Service
public class NetworkService {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(NetworkService.class);

    @Value("${madigapay.url}")
    private String baseUrl;

    
    public ResponseEntity<String> sendRequest(String urlPart, HttpMethod method,
            HashMap<String, Object> data, String token, String apiKey) {
        HttpHeaders headers = new HttpHeaders();
        if (token != null) {
            headers.set("Content-Type", "application/json");
            headers.set("Authorization", "Bearer " + token);
            headers.set("API-Key", apiKey);
        }

        return process(urlPart, method, data, headers);
    }

  
    
    private ResponseEntity process(String urlPart, HttpMethod method, 
            HashMap<String, Object> data, HttpHeaders headers){
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
}
