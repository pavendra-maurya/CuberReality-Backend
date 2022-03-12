package com.cuberreality.service.impl;

import com.cuberreality.entity.user.UserLogin;
import com.cuberreality.entity.user.UserProfilesSchema;
import com.cuberreality.error.RecordNotFoundException;
import com.cuberreality.mapper.UserMapper;
import com.cuberreality.repository.OccupationsRepository;
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
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

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
    private OccupationsRepository occupationsRepository;

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public UserDetailsApiResponse getUserDetails() {


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
        createUserRequest.setLastName("None");
        request.setCreateRequest(Collections.singletonList(createUserRequest));
        // Create User details in CRM

        String path = "/bigin/v1/Contacts";
        CreateUserApiResponse createUserApiResponse = apiClient.post(request, CreateUserApiResponse.class, path);
        String id = createUserApiResponse.getData().get(0).getDetails().getId();

        // Get user details in from CRM
        System.out.println("Start fetching data "+id);
        String pathWithId = path + "/" + id;
        FetchUserApiResponse fetchUserApiResponse = apiClient.get(FetchUserApiResponse.class, pathWithId);
        UserDetailsApiResponse userDetailsApiResponse = fetchUserApiResponse.getData().get(0);
        userDetailsApiResponse.setUserUuid(userUuid);
        userDetailsApiResponse.setReferralCode(referralCode);
        userDetailsApiResponse.setReferralEligibleCashback(true);

        // Save user details in local database;
        userDetailsApiResponse.setWallet(1000);
        userProfileRepository.save(userMapper.toUserProfileSchema(userDetailsApiResponse));
        userLogin.setUserRegistered(false);
        userLoginRepository.save(userLogin);

        return "Successfully Created";

    }

    @Override
    public UpdateUserApiResponse updateUserDetails(UpdateUserRequest updateUserRequest, String userId) throws Exception {
        CreateApiRequest<UpdateUserRequest> request = new CreateApiRequest<>();
        updateUserRequest.setFirst_Name(updateUserRequest.getName());
        request.setCreateRequest(Collections.singletonList(updateUserRequest));
        String path = "/bigin/v1/Contacts/"+userId;
        UpdateUserApiResponse createUserApiResponse = apiClient.put(request, UpdateUserApiResponse.class, path);


       if("success".equalsIgnoreCase(createUserApiResponse.getData().get(0).getStatus())){
           update(updateUserRequest,userId);

       }


        return createUserApiResponse;
    }

    @Override
    public ResellersOccupationResponse getResellersOccupation() throws Exception {
        return userMapper.toResellersOccupationResponse(occupationsRepository.findAll().get(0));
    }

    public UserProfilesSchema update(UpdateUserRequest updateUserRequest, String userId){
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(userId));
        Update update = new Update();
        update.set("Email", updateUserRequest.getEmail());
     //   update.set("First_Name", updateUserRequest.getFirst_Name());
        update.set("First_Name", updateUserRequest.getName() );
       // update.set("Last_Name", updateUserRequest.getLast_Name());
        update.set("Reseller_Is", updateUserRequest.getReseller_Is());
        return mongoTemplate.findAndModify(query, update, UserProfilesSchema.class);
    }
}
