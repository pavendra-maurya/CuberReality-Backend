package com.cuberreality.service;


import com.cuberreality.request.propertise.PropertiesSearchRequest;
import com.cuberreality.response.propertise.PropertiesSearchDetails;
import com.cuberreality.response.propertise.PropertiesSearchResponse;
import com.cuberreality.response.propertise.PropertyAreaResponse;

import java.util.List;

public interface PropertiesService {

    PropertiesSearchResponse getPropertiesInSpace(PropertiesSearchRequest propertiesSearchRequest);

    PropertiesSearchDetails getPropertyByCustomer(String property_id, String referred_by_id);

    PropertiesSearchDetails getPropertyById(String property_id);
    PropertyAreaResponse getSearchAreas();
}
