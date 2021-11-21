package com.cuberreality.service;

import com.cuberreality.error.OtpException;
import com.cuberreality.request.OtpRequest;
import com.cuberreality.request.UserLoginRequest;
import com.cuberreality.response.OtpResponse;
import com.cuberreality.response.RegisterUserResponse;
import com.cuberreality.response.UserJwtTokenValidationResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserLoginService {

    public OtpResponse userLogin(UserLoginRequest userLoginRequest) throws OtpException;

    public RegisterUserResponse registerUser(OtpRequest otpRequest) throws OtpException;

    public UserJwtTokenValidationResponse userJwtTokenValidation();

}
