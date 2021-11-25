package com.cuberreality.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ResellerResponse {

    @JsonProperty("Reseller_Is")
    private List<String> resellerList;
}
