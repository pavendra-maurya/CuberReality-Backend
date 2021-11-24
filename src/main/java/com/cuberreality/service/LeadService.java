package com.cuberreality.service;

import com.cuberreality.request.CreateLeadRequest;
import com.cuberreality.request.UpdateLeadRequest;
import com.cuberreality.response.CreateLeadResponse;
import com.cuberreality.response.LeadResponse;

import java.util.List;

public interface LeadService {

    CreateLeadResponse createLead(CreateLeadRequest createLeadRequest) throws Exception;

    LeadResponse getLead(String id);

    List<LeadResponse> getLeads();

    List<LeadResponse> updateLead(UpdateLeadRequest updateLeadRequest);
}
