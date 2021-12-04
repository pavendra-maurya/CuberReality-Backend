package com.cuberreality.response.leads;

import lombok.Data;

import java.util.List;
@Data
public class GetLeadResponse {
    private List<GetLeadResponseModel> data;

}
