package com.cuberreality.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ResellerOccupationApiResponse {

    @JsonProperty("data")
    private List<ResellerResponse> data;

}

