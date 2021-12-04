package com.cuberreality.request.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SignUpRequest {

    @NotNull(message = "mobile_number can't  be null")
    @JsonProperty("mobile_number")
    private String phoneNumber;

}
