package com.cuberreality.service;


import com.cuberreality.entity.propertise.PropertiesSchema;
import com.cuberreality.request.propertise.PropertiesSearchRequest;

import java.util.List;

public interface PropertiesService {

    List<PropertiesSchema> getPropertiesInSpace(PropertiesSearchRequest propertiesSearchRequest);

    PropertiesSchema getPropertyByCustomer(String property_id, String referred_by_id);

    PropertiesSchema getPropertyById(String property_id);
}
