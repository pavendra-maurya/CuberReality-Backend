package com.cuberreality.request.leads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class InquiryLeadCrmRequest {


        @JsonProperty("data")
        private List<InquiryLeadModel> data;



}
