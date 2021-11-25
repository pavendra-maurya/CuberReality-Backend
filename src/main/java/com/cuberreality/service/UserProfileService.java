package com.cuberreality.service;

import com.cuberreality.entity.UserProfilesSchema;
import com.cuberreality.request.user.CreateUserRequest;
import com.cuberreality.request.user.UpdateUserRequest;
import com.cuberreality.response.user.ResellersOccupationResponse;
import com.cuberreality.response.user.UserDetailsApiResponse;

public interface UserProfileService {

    UserDetailsApiResponse getUserDetails(String user_id);

    String createUserProfile(CreateUserRequest createUserRequest) throws Exception;

    Object updateUserDetails(UpdateUserRequest updateUserRequest);

    ResellersOccupationResponse getResellersOccupation() throws Exception;

}
