package com.cuberreality.resource;

import com.cuberreality.error.OtpException;
import com.cuberreality.request.DeviceTokenRequest;
import com.cuberreality.request.login.SignUpRequest;
import com.cuberreality.request.login.OtpRequest;
import com.cuberreality.request.login.UserLoginRequest;
import com.cuberreality.response.BaseResponse;
import com.cuberreality.response.login.OtpResponse;
import com.cuberreality.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class LoginResource {

    @Autowired
    private UserLoginService userLoginService;


    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<?> userSignUp(@Valid @RequestBody SignUpRequest signUpRequest) throws OtpException {
        return new ResponseEntity<>(new BaseResponse<>(userLoginService.userSignUp(signUpRequest), ""), HttpStatus.OK);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> userLogin(@Valid @RequestBody UserLoginRequest userLoginRequest) throws OtpException {
        OtpResponse response = userLoginService.userLogin(userLoginRequest);
        return new ResponseEntity<>(new BaseResponse<>(response, ""), HttpStatus.OK);
    }

    @RequestMapping(value = "/otp/validation", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@Valid @RequestBody OtpRequest otpRequest) throws OtpException {
        return new ResponseEntity<>(new BaseResponse<>(userLoginService.registerUser(otpRequest), ""), HttpStatus.OK);
    }

//    @RequestMapping(value = "/user/jwt/validation", method = RequestMethod.GET)
//    public ResponseEntity<?> userJwtTokenValidation() {
//        return new ResponseEntity<>(new BaseResponse<>(userLoginService.userJwtTokenValidation(), ""), HttpStatus.OK);
//    }



    @RequestMapping(method = RequestMethod.PUT,value = "/device-token")
    public ResponseEntity<?> updateDeviceToken(@RequestBody DeviceTokenRequest deviceToken) {

        return  new ResponseEntity<>(new BaseResponse<>(userLoginService.updateDeviceToken(deviceToken), ""), HttpStatus.OK);
    }


}
