package com.cuberreality.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CreateUserProfileRequest {

    @JsonProperty("Email")
    public Object email;

    @JsonProperty("Reseller_Is")
    public List<String> resellerIs;         // is loan agent, bank manager, builder

}
