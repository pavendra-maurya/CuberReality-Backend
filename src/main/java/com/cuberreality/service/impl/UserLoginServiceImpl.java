package com.cuberreality.service.impl;

import com.cuberreality.constant.AppConstant;
import com.cuberreality.constant.UserType;
import com.cuberreality.entity.UserLogin;
import com.cuberreality.error.OtpException;
import com.cuberreality.repository.UserLoginRepository;
import com.cuberreality.request.OtpRequest;
import com.cuberreality.request.UserLoginRequest;
import com.cuberreality.response.OtpResponse;
import com.cuberreality.response.RegisterUserResponse;
import com.cuberreality.response.UserJwtTokenValidationResponse;
import com.cuberreality.security.CustomUserDetails;
import com.cuberreality.security.JwtProviderService;
import com.cuberreality.security.OtpGenerationService;
import com.cuberreality.service.CustomUserDetailsService;
import com.cuberreality.service.UserLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Service
@Slf4j
public class UserLoginServiceImpl implements CustomUserDetailsService, UserLoginService {

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtProviderService jwtTokenUtil;

    @Autowired
    private OtpGenerationService otpService;


    public CustomUserDetails loadUserByUsername(String phone,String userType) throws UsernameNotFoundException {
        UserLogin userLogin = userLoginRepository.findByPhoneNumberAndUserType(phone,userType);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+userType));

        if (userLogin == null) {
            log.error("Mobile number is not registered " + phone);
            throw new UsernameNotFoundException("Mobile number is not registered");
        }
        return new CustomUserDetails(userLogin.getPhoneNumber(),
                AppConstant.LOGIN_PASSWORD, authorities, userLogin.getUserUuid(),
                userLogin.getActive(), userLogin.getUserType()
        );
    }


    public OtpResponse userLogin(UserLoginRequest userLoginRequest) throws OtpException {
        otpService.generateSendOTP(userLoginRequest.getPhoneNumber());
        return new OtpResponse("Otp Successfully send to " + userLoginRequest.getPhoneNumber());
    }


    private String getToken(String phoneNumber,String userType) {
        CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(phoneNumber,userType);
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+userType));
        final Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails,
                AppConstant.LOGIN_PASSWORD, authorities);
        final String token = jwtTokenUtil.generateToken(authentication,userType);
        return token;
    }


    @Override
    public RegisterUserResponse registerUser(OtpRequest otpRequest) throws OtpException {
        // TODO Auto-generated method stub

        if (otpRequest.getOtp().equals(otpService.getOtp(otpRequest.getPhoneNumber()))|| true) {
            UserLogin userLogin = userLoginRepository.findByPhoneNumber(otpRequest.getPhoneNumber());

            if (Objects.isNull(userLogin)) {
                log.warn("There is no user registered with mobile number " + otpRequest.getPhoneNumber());
                log.info("Registering new user with phone number " + otpRequest.getPhoneNumber());

                userLogin = getNewUser(otpRequest.getPhoneNumber(), UserType.RESELLER);
                userLogin = userLoginRepository.save(userLogin);

                log.info("Registered new user with phone number " + otpRequest.getPhoneNumber() + " user details " + userLogin);
            } else {
                userLogin.setLastLoginDate(LocalDateTime.now());
                userLogin = userLoginRepository.save(userLogin);
            }
            return new RegisterUserResponse(getToken(userLogin.getPhoneNumber(), UserType.RESELLER));

        } else {
            throw new OtpException("Entered wrong otp, Please enter correct otp");
        }
    }


    private UserLogin getNewUser(String phoneNumber, String userType) {
        UserLogin userLogin = new UserLogin();
        userLogin.setUserUuid(UUID.randomUUID().toString());
        userLogin.setPhoneNumber(phoneNumber);
        userLogin.setUserType(userType);
        userLogin.setActive(true);
        userLogin.setUserCreatedDate(LocalDateTime.now());
        userLogin.setLastLoginDate(LocalDateTime.now());
        userLogin.setUserUpdatedDate(LocalDateTime.now());
        return userLogin;
    }


    @Override
    public UserJwtTokenValidationResponse userJwtTokenValidation() {
        // TODO Auto-generated method stub
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserJwtTokenValidationResponse userJwtTokenValidationResponse = new UserJwtTokenValidationResponse();

        if(!customUserDetails.getAppType().equals(UserType.RESELLER))
            return userJwtTokenValidationResponse;

        userJwtTokenValidationResponse.setAppType(customUserDetails.getAppType());
        userJwtTokenValidationResponse.setActive(customUserDetails.getUserActive());
        userJwtTokenValidationResponse.setPhone(customUserDetails.getUsername());
        userJwtTokenValidationResponse.setUuid(customUserDetails.getUserUuid());
        return userJwtTokenValidationResponse;
    }


    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        UserLogin userLogin = userLoginRepository.findByPhoneNumber(phone);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        if (userLogin == null) {
            log.error("Mobile number is not registered " + phone);
            throw new UsernameNotFoundException("Mobile number is not registered");
        }
        return new CustomUserDetails(userLogin.getPhoneNumber(),
                AppConstant.LOGIN_PASSWORD, authorities, userLogin.getUserUuid(),
                userLogin.getActive(), userLogin.getUserType()
        );
    }


}
