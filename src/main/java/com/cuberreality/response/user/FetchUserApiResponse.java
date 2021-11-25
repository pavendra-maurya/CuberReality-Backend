package com.cuberreality.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class FetchUserApiResponse {

    @JsonProperty(value = "data")
    private List<UserDetailsApiResponse> data;
}
