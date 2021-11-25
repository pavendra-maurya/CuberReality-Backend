package com.cuberreality.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateUserDetailsApiResponse {

    @JsonProperty(value = "details")
    private CreateUserDetailsResponse details;
}
