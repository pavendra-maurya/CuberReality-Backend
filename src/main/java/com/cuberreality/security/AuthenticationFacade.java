package com.cuberreality.security;

import com.cuberreality.error.UnauthorizedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade implements IAuthenticationFacade {
 
    @Override
    public String getUserID() {
    	
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	
    	if (!(authentication instanceof AnonymousAuthenticationToken)) {
    		String currentUserName = authentication.getName();
    	    return currentUserName;
    	}
    	
        throw new UnauthorizedException("UnauthorizedException Please enter correct JWT token");
    }
}