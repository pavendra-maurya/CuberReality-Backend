package com.cuberreality.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CreateUserApiResponse {


    @JsonProperty("data")
    private List<CreateUserDetailsApiResponse> data;

}
