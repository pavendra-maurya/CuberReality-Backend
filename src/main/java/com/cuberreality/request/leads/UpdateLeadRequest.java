package com.cuberreality.request.leads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
public class UpdateLeadRequest {

    @JsonProperty("data")
   private List<UpdateLeadModel> data;
}
