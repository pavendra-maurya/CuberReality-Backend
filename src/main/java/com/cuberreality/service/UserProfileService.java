package com.cuberreality.service;

import com.cuberreality.request.CreateUserRequest;
import com.cuberreality.request.UpdateUserRequest;
import com.cuberreality.response.ResellersOccupationResponse;
import com.cuberreality.response.UserDetailsResponse;

public interface UserProfileService {

    UserDetailsResponse getUserDetails(String user_id);

    Object createUserProfile(CreateUserRequest createUserRequest);

    Object updateUserDetails(UpdateUserRequest updateUserRequest);

    ResellersOccupationResponse getResellersOccupation() throws Exception;

}
