package com.cuberreality.response.leads;

import lombok.Data;

import java.util.List;

@Data
public class InquiryLeadResponse {
    private List<GetInquiryLeadResponseModel> data;
}
