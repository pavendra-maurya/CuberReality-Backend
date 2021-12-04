package com.cuberreality.response.leads;

import lombok.Data;

@Data
public class LinkLeadResponsePojo {
    private String code;
    private Details details;
    private String message;
    private String status;
}
