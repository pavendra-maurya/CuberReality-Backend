package com.cuberreality.mapper;

import com.cuberreality.entity.propertise.PropertiesSchema;
import com.cuberreality.response.propertise.PropertiesSearchDetails;
import com.cuberreality.response.propertise.PropertiesSearchDetailsAppResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PropertiesMapper {

    PropertiesSearchDetailsAppResponse toPropertiesAppResponse(PropertiesSchema response);
    PropertiesSearchDetails toPropertiesResponse(PropertiesSchema response);
}
