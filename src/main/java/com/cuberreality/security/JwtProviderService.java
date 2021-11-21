package com.cuberreality.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public interface JwtProviderService{

    public String generateToken(Authentication authentication,String appType);
    
    public String getUsernameFromToken(String token);

    public String getAppTypeFromToken(String token);
    
    public Boolean validateToken(String token, CustomUserDetails customUserDetails);

    public  UsernamePasswordAuthenticationToken getAuthentication(final String token,final CustomUserDetails customUserDetails);

}
