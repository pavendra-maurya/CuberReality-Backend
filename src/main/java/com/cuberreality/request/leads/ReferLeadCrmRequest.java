package com.cuberreality.request.leads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ReferLeadCrmRequest {


        @JsonProperty("data")
        private List<ReferLeadModel> data;



}
