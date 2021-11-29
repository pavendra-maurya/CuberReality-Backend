package com.cuberreality.mapper;

import com.cuberreality.entity.user.UserProfilesSchema;
import com.cuberreality.request.CreateUserProfileRequest;
import com.cuberreality.request.user.CreateUserRequest;
import com.cuberreality.response.user.UserDetailsApiResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserProfilesSchema toUserProfileSchema(UserDetailsApiResponse response);

    UserDetailsApiResponse toUserDetailsApi(UserProfilesSchema response);

    CreateUserRequest  toCreateUserRequest(CreateUserProfileRequest request);
}
