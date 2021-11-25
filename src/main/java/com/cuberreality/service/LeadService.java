package com.cuberreality.service;

import com.cuberreality.request.leads.CreateLeadRequest;
import com.cuberreality.request.leads.UpdateLeadRequest;
import com.cuberreality.response.leads.CreateLeadResponse;
import com.cuberreality.response.leads.LeadResponse;

import java.util.List;

public interface LeadService {

    CreateLeadResponse createLead(CreateLeadRequest createLeadRequest) throws Exception;

    LeadResponse getLead(String id);

    List<LeadResponse> getLeads();

    List<LeadResponse> updateLead(UpdateLeadRequest updateLeadRequest);
}
