package com.cuberreality.response.leads;

import lombok.Data;

@Data
public class UpdateLeadResponseModel {

    private String code;
    private Details details;
    private String message;
    private String status;
}
