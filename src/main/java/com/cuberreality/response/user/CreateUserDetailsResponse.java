package com.cuberreality.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.context.annotation.Primary;

@Data
public class CreateUserDetailsResponse {

    @JsonProperty(value = "id")
    private  String id;
}
