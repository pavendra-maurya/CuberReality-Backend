package com.cuberreality.service.impl;

import com.cuberreality.config.AppConfig;
import com.cuberreality.response.RefreshTokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Component
@Slf4j
public class AuthorizationToken {

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private RestTemplate restTemplate;

    public String authorizationToken = "1000.cbb3355ab1691f4e03ceabf263287afb.f13d703d47f9250402f4935b84aa9ae1";

    //    @Scheduled(fixedRate = 20000)
    public void crmRefreshAuthorizationToken() {

        String url = appConfig.getCrm_base_url() + "/oauth/v2/token";

        HttpEntity<MultiValueMap<String, Object>> entity = getHeaderEntity();

        log.info("Generating authorization token using " + entity.toString());
        ResponseEntity<RefreshTokenResponse> response = restTemplate.postForEntity(url, entity, RefreshTokenResponse.class);

        log.debug(response.getBody().toString());

        RefreshTokenResponse refreshTokenResponse = response.getBody();

        if (refreshTokenResponse.getError().length() < 1) {
            log.info("Authorization key generated successfully ");
            this.authorizationToken = refreshTokenResponse.getAccessToken();
        } else {
            log.error(refreshTokenResponse.getError());
        }

    }

    private HttpEntity<MultiValueMap<String, Object>> getHeaderEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("refresh_token", appConfig.getCrm_refresh_token());
        map.add("grant_type", "refresh_token");
        map.add("client_id", appConfig.getCrm_client_id());
        map.add("client_secret", appConfig.getCrm_client_secret());
        return new HttpEntity<>(map, headers);
    }

    public String getAuthorizationToken() {

        if (this.authorizationToken == null) {
            crmRefreshAuthorizationToken();
            return this.authorizationToken;
        }

        return this.authorizationToken;
    }
}
