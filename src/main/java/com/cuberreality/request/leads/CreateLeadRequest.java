package com.cuberreality.request.leads;

import com.cuberreality.request.leads.CreateLeadModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
public class CreateLeadRequest {

    @JsonProperty("data")
   private List<CreateLeadModel> data;


}
