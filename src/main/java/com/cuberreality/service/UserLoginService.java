package com.cuberreality.service;

import com.cuberreality.error.OtpException;
import com.cuberreality.request.DeviceTokenRequest;
import com.cuberreality.request.login.OtpRequest;
import com.cuberreality.request.login.SignUpRequest;
import com.cuberreality.request.login.UserLoginRequest;
import com.cuberreality.response.login.OtpResponse;
import com.cuberreality.response.login.SignUpResponse;
import com.cuberreality.response.user.RegisterUserResponse;
import com.cuberreality.response.user.UserJwtTokenValidationResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserLoginService {

     OtpResponse userLogin(UserLoginRequest userLoginRequest) throws OtpException;

     RegisterUserResponse otpValidate(OtpRequest otpRequest) throws OtpException;

     UserJwtTokenValidationResponse userJwtTokenValidation();

    String updateDeviceToken(DeviceTokenRequest deviceToken);

    String getDeviceToken(String userID);

    SignUpResponse userSignUp(SignUpRequest signUpRequest) throws OtpException;
}
