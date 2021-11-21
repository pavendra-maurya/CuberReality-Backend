package com.cuberreality.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


public class CustomUserDetails extends User {

    private String userUuid;
    private boolean userActive;
    private String appType;


    public CustomUserDetails(String username, String password,
                             Collection<? extends GrantedAuthority> authorities,
                             String userUuid, boolean userActive, String userType) {
        super(username, password, authorities);
        this.userUuid = userUuid;
        this.userActive = userActive;
        this.appType = userType;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public Boolean getUserActive() {
        return userActive;
    }

    public String getAppType() {
        return appType;
    }
}
