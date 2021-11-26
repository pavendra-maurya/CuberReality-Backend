package com.cuberreality.mapper;

import com.cuberreality.entity.propertise.PropertiesSchema;
import com.cuberreality.response.propertise.PropertiesSearchDetails;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PropertiesMapper {
    PropertiesSearchDetails toPropertiesResponse(PropertiesSchema response);
}
