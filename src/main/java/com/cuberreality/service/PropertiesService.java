package com.cuberreality.service;

import com.cuberreality.entity.PropertiesSchema;
import com.cuberreality.request.PropertiesSearchRequest;
import com.cuberreality.request.UpdateLeadRequest;
import com.cuberreality.response.PropertyDetailedResponse;

import java.util.List;

public interface PropertiesService {

    List<PropertiesSchema> getPropertiesInSpace(PropertiesSearchRequest propertiesSearchRequest);

    PropertyDetailedResponse getProperty(String id, String referred_by_id);
}
