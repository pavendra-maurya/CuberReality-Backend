package com.cuberreality.request.leads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class UpdateReferLeadRequest {

    @JsonProperty("data")
    private List<UpdateReferLeadModel> data;
}
