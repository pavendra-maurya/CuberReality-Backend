package com.cuberreality.mapper;

import com.cuberreality.entity.propertise.PropertiesSchema;
import com.cuberreality.entity.user.UserProfilesSchema;
import com.cuberreality.response.propertise.PropertiesSearchResponse;
import com.cuberreality.response.user.UserDetailsApiResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PropertiesMapper {
    PropertiesSearchResponse toPropertiesResponse(PropertiesSchema response);
}
