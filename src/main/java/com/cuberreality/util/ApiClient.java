package com.cuberreality.util;

import com.cuberreality.config.AppConfig;
import com.cuberreality.service.impl.AuthorizationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ApiClient {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AuthorizationToken authorizationToken;

    @Autowired
    private AppConfig appConfig;

    public <R, T> T post(R requestBody, Class<T> responseClass, String path) throws Exception {
        String token = authorizationToken.getAuthorizationToken();
        String baseUrl = appConfig.getCrm_base_url();
        String url = baseUrl + path;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(token);
        HttpEntity<R> entity = new HttpEntity<>(requestBody, httpHeaders);
        ResponseEntity<T> response = restTemplate.postForEntity(url, entity, responseClass);
        return response.getBody();

    }


    public <R, T> T put(R requestBody, Class<T> responseClass, String path) throws Exception {
        String token = authorizationToken.getAuthorizationToken();
        String baseUrl = appConfig.getCrm_base_url();
        String url = baseUrl + path;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(token);
        HttpEntity<R> entity = new HttpEntity<>(requestBody, httpHeaders);
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.PUT ,entity, responseClass);
        return response.getBody();
    }

    public <T> T get(Class<T> responseClass, String path) throws Exception {
        String token = authorizationToken.getAuthorizationToken();
        String baseUrl = appConfig.getCrm_base_url();
        String url = baseUrl + path;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, entity, responseClass);
        return response.getBody();

    }
}
