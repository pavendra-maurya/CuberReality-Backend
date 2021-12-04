package com.cuberreality.request.leads;

import lombok.Data;

import java.util.List;
@Data
public class LinkProductLeadRequest {
    private List<LeadProductPojo> data;
}
