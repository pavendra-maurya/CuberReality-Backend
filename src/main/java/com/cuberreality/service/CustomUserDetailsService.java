package com.cuberreality.service;

import com.cuberreality.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface CustomUserDetailsService extends UserDetailsService {

    CustomUserDetails loadUserByUsername(String var1,String v2) throws UsernameNotFoundException;
}
