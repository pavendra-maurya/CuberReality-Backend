package com.cuberreality.service.impl;

import com.cuberreality.request.CreateUserRequest;
import com.cuberreality.request.UpdateUserRequest;
import com.cuberreality.response.ResellerOccupationApiResponse;
import com.cuberreality.response.ResellersOccupationResponse;
import com.cuberreality.response.UserDetailsResponse;
import com.cuberreality.service.UserProfileService;
import com.cuberreality.util.ApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private ApiClient apiClient;

    @Override
    public UserDetailsResponse getUserDetails(String user_id) {
        return null;
    }

    @Override
    public Object createUserProfile(CreateUserRequest createUserRequest) {
        return null;
    }

    @Override
    public Object updateUserDetails(UpdateUserRequest updateUserRequest) {
        return null;
    }

    @Override
    public ResellersOccupationResponse getResellersOccupation() throws Exception {
//
//        List<String> arrayList = Arrays.asList("Loan Agent", "Insurance Agent", "Homemaker", "Home Loan Agent", "Mutual Fund Agent", "Financial Advisors", "Chartered accountant", "Architect", "Interior Designer", "Civil Contractor / Engineer");
        String path = "/bigin/v1/Contacts/269594000000237301?fields=Reseller_Is";
        ResellerOccupationApiResponse resellerOccupationResponse = apiClient.get(ResellerOccupationApiResponse.class, path);
        return new ResellersOccupationResponse(resellerOccupationResponse.getData().get(0).getResellerList());
    }
}
