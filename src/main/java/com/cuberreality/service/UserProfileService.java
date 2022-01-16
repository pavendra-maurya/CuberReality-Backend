package com.cuberreality.service;

import com.cuberreality.request.CreateUserProfileRequest;
import com.cuberreality.request.user.UpdateUserRequest;
import com.cuberreality.response.user.ResellersOccupationResponse;
import com.cuberreality.response.user.UpdateUserApiResponse;
import com.cuberreality.response.user.UserDetailsApiResponse;

public interface UserProfileService {

    UserDetailsApiResponse getUserDetails();

    String createUserProfile(CreateUserProfileRequest createUserProfileRequest) throws Exception;

    UpdateUserApiResponse updateUserDetails(UpdateUserRequest updateUserRequest, String userId) throws Exception;

    ResellersOccupationResponse getResellersOccupation() throws Exception;

}
