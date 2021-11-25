package com.cuberreality.mapper;

import com.cuberreality.entity.UserProfilesSchema;
import com.cuberreality.response.user.UserDetailsApiResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserProfilesSchema toUserProfileSchema(UserDetailsApiResponse response);

    UserDetailsApiResponse toUserDetailsApi(UserProfilesSchema response);
}
