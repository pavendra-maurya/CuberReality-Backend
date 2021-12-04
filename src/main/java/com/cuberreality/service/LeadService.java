package com.cuberreality.service;

import com.cuberreality.request.leads.*;
import com.cuberreality.response.leads.CreateLeadResponse;
import com.cuberreality.response.leads.CreateLeadResponseModel;
import com.cuberreality.response.leads.GetLeadResponseModel;
import com.cuberreality.response.leads.UpdateLeadResponse;

import java.util.List;

public interface LeadService {

    CreateLeadResponseModel createLead(CreateLeadModel createLeadRequest) throws Exception;

    GetLeadResponseModel getLead(String id) throws Exception;

    List<GetLeadResponseModel> getLeads() throws Exception;

    List<GetLeadResponseModel> searchLeads(SearchLeadRequest searchLeadRequest) throws Exception;
    int findLeadsCountByReseller(String id) throws Exception;

    List<GetLeadResponseModel> findLeadsByReseller(String id) throws Exception;

    UpdateLeadResponse updateLead(UpdateLeadModel updateLeadRequest, String leadId) throws Exception;
}
