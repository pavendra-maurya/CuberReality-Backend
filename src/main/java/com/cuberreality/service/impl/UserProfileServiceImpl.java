package com.cuberreality.service.impl;

import com.cuberreality.entity.user.UserLogin;
import com.cuberreality.entity.user.UserProfilesSchema;
import com.cuberreality.error.RecordNotFoundException;
import com.cuberreality.mapper.UserMapper;
import com.cuberreality.repository.UserLoginRepository;
import com.cuberreality.repository.UserProfileRepository;
import com.cuberreality.request.CreateUserProfileRequest;
import com.cuberreality.request.user.CreateApiRequest;
import com.cuberreality.request.user.CreateUserRequest;
import com.cuberreality.request.user.UpdateUserRequest;
import com.cuberreality.response.user.*;
import com.cuberreality.service.UserLoginService;
import com.cuberreality.service.UserProfileService;
import com.cuberreality.util.ApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private ApiClient apiClient;

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetailsApiResponse getUserDetails(String user_id) {
        UserJwtTokenValidationResponse userJwtTokenValidationResponse = userLoginService.userJwtTokenValidation();
        String uuid = userJwtTokenValidationResponse.getUuid();
        Optional<UserProfilesSchema> userProfilesSchema = userProfileRepository.findByUserUuid(uuid);

        UserDetailsApiResponse response = userMapper.toUserDetailsApi(userProfilesSchema.orElseThrow(() -> new RecordNotFoundException("Record does not exist in mongo db")));
        return response;

    }

    @Override
    public String createUserProfile(CreateUserProfileRequest createUserProfileRequest) throws Exception {

        UserJwtTokenValidationResponse jwtTokenValidationResponse = userLoginService.userJwtTokenValidation();
        String mobileNumber = jwtTokenValidationResponse.getPhone();
        String userUuid = jwtTokenValidationResponse.getUuid();

        UserLogin userLogin = userLoginRepository.findByPhoneNumber(mobileNumber);
        String referralCode = userLogin.getReferralCode();
        String userType = userLogin.getUserType();

        CreateUserRequest createUserRequest = userMapper.toCreateUserRequest(createUserProfileRequest);
        CreateApiRequest<CreateUserRequest> request = new CreateApiRequest<>();
        createUserRequest.setUserType(Collections.singletonList(userType));
        createUserRequest.setUserStatus("Active");
        createUserRequest.setMobile(mobileNumber);

        request.setCreateRequest(Collections.singletonList(createUserRequest));

        // Create User details in CRM

        String path = "/bigin/v1/Contacts";
        CreateUserApiResponse createUserApiResponse = apiClient.post(request, CreateUserApiResponse.class, path);
        String id = createUserApiResponse.getData().get(0).getDetails().getId();

        // Get user details in from CRM
        String pathWithId = path + "/" + id;
        FetchUserApiResponse fetchUserApiResponse = apiClient.get(FetchUserApiResponse.class, pathWithId);
        UserDetailsApiResponse userDetailsApiResponse = fetchUserApiResponse.getData().get(0);

        userDetailsApiResponse.setUserUuid(userUuid);
        userDetailsApiResponse.setReferralCode(referralCode);
        userDetailsApiResponse.setReferralEligibleCashback(true);

        // Save user details in local database;
        userProfileRepository.save(userMapper.toUserProfileSchema(userDetailsApiResponse));

        return "Successfully Created";

    }

    @Override
    public Object updateUserDetails(UpdateUserRequest updateUserRequest) {

        return null;
    }

    @Override
    public ResellersOccupationResponse getResellersOccupation() throws Exception {
//        List<String> arrayList = Arrays.asList("Loan Agent", "Insurance Agent", "Homemaker", "Home Loan Agent", "Mutual Fund Agent", "Financial Advisors", "Chartered accountant", "Architect", "Interior Designer", "Civil Contractor / Engineer");
        String path = "/bigin/v1/Contacts/269594000000237301?fields=Reseller_Is";
        ResellerOccupationApiResponse resellerOccupationResponse = apiClient.get(ResellerOccupationApiResponse.class, path);
        return new ResellersOccupationResponse(resellerOccupationResponse.getData().get(0).getResellerList());
    }
}
