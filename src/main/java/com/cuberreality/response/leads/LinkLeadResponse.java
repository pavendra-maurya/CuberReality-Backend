package com.cuberreality.response.leads;

import lombok.Data;

import java.util.List;

@Data
public class LinkLeadResponse {
    private List<LinkLeadResponsePojo> data;
}
