package com.cuberreality.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterUserResponse {

    @JsonProperty("token")
    private String token;

    @JsonProperty("new_user")
    private boolean newUser;


}
