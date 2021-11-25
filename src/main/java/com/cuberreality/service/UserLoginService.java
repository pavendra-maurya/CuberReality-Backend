package com.cuberreality.service;

import com.cuberreality.error.OtpException;
import com.cuberreality.request.login.OtpRequest;
import com.cuberreality.request.login.UserLoginRequest;
import com.cuberreality.response.login.OtpResponse;
import com.cuberreality.response.user.RegisterUserResponse;
import com.cuberreality.response.user.UserJwtTokenValidationResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserLoginService {

    public OtpResponse userLogin(UserLoginRequest userLoginRequest) throws OtpException;

    public RegisterUserResponse registerUser(OtpRequest otpRequest) throws OtpException;

    public UserJwtTokenValidationResponse userJwtTokenValidation();

}
