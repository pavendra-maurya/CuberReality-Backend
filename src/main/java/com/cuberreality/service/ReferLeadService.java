package com.cuberreality.service;

import com.cuberreality.request.leads.ReferLeadModel;
import com.cuberreality.request.leads.UpdateLeadModel;
import com.cuberreality.request.leads.UpdateReferLeadModel;
import com.cuberreality.response.leads.CreateLeadResponseModel;
import com.cuberreality.response.leads.GetLeadResponseModel;
import com.cuberreality.response.leads.GetReferLeadResponseModel;
import com.cuberreality.response.leads.UpdateLeadResponse;

import java.util.List;

public interface ReferLeadService {

    CreateLeadResponseModel referLead(ReferLeadModel createLeadModel) throws Exception;


    List<GetLeadResponseModel> getLeads() throws Exception;

    UpdateLeadResponse updateLead(UpdateLeadModel updateLeadRequest, String leadId) throws Exception;

    GetReferLeadResponseModel getReferLead(String id);

    UpdateLeadResponse updateReferLead(UpdateReferLeadModel updateReferLeadModel, String id) throws Exception;

    List<GetReferLeadResponseModel> getReferLeadByResellerId(String resellerId);
}
