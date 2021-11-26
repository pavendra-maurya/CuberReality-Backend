package com.cuberreality.service;


import com.cuberreality.entity.propertise.PropertiesSchema;
import com.cuberreality.request.propertise.PropertiesSearchRequest;
import com.cuberreality.response.propertise.PropertiesSearchResponse;

import java.util.List;

public interface PropertiesService {

    List<PropertiesSearchResponse> getPropertiesInSpace(PropertiesSearchRequest propertiesSearchRequest);

    PropertiesSearchResponse getPropertyByCustomer(String property_id, String referred_by_id);

    PropertiesSearchResponse getPropertyById(String property_id);
}
