package com.cuberreality.request.leads;

import lombok.Data;

@Data
public class SearchLeadRequest {

    private String ownerName;
    private String status;
    private String mobNo;
    private String propertyName;
    private String area;
    private String city;

}
