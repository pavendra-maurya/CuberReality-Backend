package com.cuberreality.service;

import com.cuberreality.request.leads.*;
import com.cuberreality.response.leads.*;

import java.util.List;

public interface InquiryLeadService {

    CreateLeadResponseModel inquiryLead(InquiryLeadModel createLeadModel) throws Exception;

    List<GetLeadResponseModel> getLeads() throws Exception;

    GetInquiryLeadResponseModel getInquiryLead(String id);

    UpdateLeadResponse updateInquiryLead(UpdateInquiryLeadModel updateInquiryLeadModel, String id) throws Exception;

    List<GetInquiryLeadResponseModel> getInquiryLeadByResellerId(String resellerId);
}
