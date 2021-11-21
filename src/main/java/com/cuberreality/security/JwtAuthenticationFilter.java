package com.cuberreality.security;

import com.cuberreality.config.AppConfig;
import com.cuberreality.service.CustomUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private CustomUserDetailsService customUserDetailsService;
    private AppConfig appConfig;
    private JwtProviderService jwtTokenUtil;

    @Autowired
     public  JwtAuthenticationFilter(CustomUserDetailsService customUserDetailsService, AppConfig appConfig, JwtProviderService jwtTokenUtil){
          this.appConfig = appConfig;
          this.jwtTokenUtil = jwtTokenUtil;
          this.customUserDetailsService = customUserDetailsService;
     }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        String header = req.getHeader(appConfig.getHeader_string());
        String phoneNumber = null;
        String authToken = null;
        String appType=null;
        
        if ( !ObjectUtils.isEmpty(header)  && header.startsWith(appConfig.getToken_prefix())) {
        	
            authToken = header.replace(appConfig.getToken_prefix(),"");
            try {
                phoneNumber = jwtTokenUtil.getUsernameFromToken(authToken);
                appType = jwtTokenUtil.getAppTypeFromToken(authToken);
            } catch (IllegalArgumentException e) {
                log.error("an error occurred during getting username from token", e);
            } catch (ExpiredJwtException e) {
                log.warn("the token is expired and not valid anymore", e);
            } catch(SignatureException e){
                log.error("Authentication Failed. Username or Password not valid.");
            }
        } else {
            log.warn("couldn't find token string, will ignore the header");
        }
        
        if ( !ObjectUtils.isEmpty(phoneNumber)  && ObjectUtils.isEmpty(SecurityContextHolder.getContext().getAuthentication())) {
            CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(phoneNumber,appType);
            if (jwtTokenUtil.validateToken(authToken, customUserDetails)) {
                UsernamePasswordAuthenticationToken authentication = jwtTokenUtil.getAuthentication(authToken,customUserDetails);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                log.info("authenticated user " + phoneNumber + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(req, res);
    }
}