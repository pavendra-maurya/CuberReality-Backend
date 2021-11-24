package com.cuberreality.service;

import com.cuberreality.entity.PropertiesSchema;
import com.cuberreality.request.CreateLeadRequest;
import com.cuberreality.request.PropertiesSearchRequest;
import com.cuberreality.request.UpdateLeadRequest;
import com.cuberreality.response.CreateLeadResponse;
import com.cuberreality.response.LeadResponse;
import com.cuberreality.response.ResellersOccupationResponse;

import java.util.List;

public interface PropertiesService {

    List<PropertiesSchema> getPropertiesInSpace(PropertiesSearchRequest propertiesSearchRequest);

    PropertiesSchema getProperty(String id, String referred_by_id);



}
