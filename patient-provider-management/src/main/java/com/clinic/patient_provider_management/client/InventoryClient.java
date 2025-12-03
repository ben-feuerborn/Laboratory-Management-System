package com.clinic.patient_provider_management.client;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class InventoryClient {

    @Autowired
    private RestTemplate restTemplate;

    @SuppressWarnings("unchecked")
    public List<Object> getInventoryItems(){
        try {
            return restTemplate.getForObject("http://inventory-management/api/inventory", List.class);
        } catch (RestClientException ex){
            return Collections.emptyList();
        }
    }

    @Configuration
    public static class RestConfig {
        @Bean
        @LoadBalanced
        public RestTemplate restTemplate(){
            return new RestTemplate();
        }
    }
}
