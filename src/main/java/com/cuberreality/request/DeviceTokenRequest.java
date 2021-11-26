package com.cuberreality.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DeviceTokenRequest {
    
    @JsonProperty("device_token")
    private String deviceToken;
}
