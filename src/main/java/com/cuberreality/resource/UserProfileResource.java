package com.cuberreality.resource;

import com.cuberreality.request.CreateUserProfileRequest;
import com.cuberreality.request.DeviceTokenRequest;
import com.cuberreality.request.user.CreateUserRequest;
import com.cuberreality.request.user.UpdateUserRequest;
import com.cuberreality.response.BaseResponse;
import com.cuberreality.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class UserProfileResource {

    @Autowired
    private UserProfileService userProfileService;

    @RequestMapping(value = "/user-profile/{user_id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserDetails(@PathVariable String user_id) {
        return new ResponseEntity<>(new BaseResponse<>(userProfileService.getUserDetails(user_id), ""), HttpStatus.OK);
    }

    @RequestMapping(value = "/user-profile", method = RequestMethod.POST)
    public ResponseEntity<?> createUserProfile(@RequestBody CreateUserProfileRequest createUserProfileRequest) throws Exception {
        return new ResponseEntity<>(new BaseResponse<>(userProfileService.createUserProfile(createUserProfileRequest), ""), HttpStatus.OK);
    }

    @RequestMapping(value = "/user-profile", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUserDetails(@RequestBody UpdateUserRequest updateUserRequest) {
        return new ResponseEntity<>(new BaseResponse<>(userProfileService.updateUserDetails(updateUserRequest), ""), HttpStatus.OK);
    }

    @RequestMapping(value = "/reseller/occupations", method = RequestMethod.GET)
    public ResponseEntity<?> getResellerOccupations() throws Exception {
        return new ResponseEntity<>(new BaseResponse<>(userProfileService.getResellersOccupation(), ""), HttpStatus.OK);
    }



}
