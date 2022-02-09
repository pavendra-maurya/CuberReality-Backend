package com.cuberreality.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private JwtAuthenticationEntryPoint unauthorizedHandler;

	private JwtAuthenticationFilter authenticationTokenFilterBean;

	@Autowired
	public WebSecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,JwtAuthenticationFilter jwtAuthenticationFilter ) {
		unauthorizedHandler = jwtAuthenticationEntryPoint;
		authenticationTokenFilterBean = jwtAuthenticationFilter;
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors()
		.and()
		.csrf().disable()
		.authorizeRequests()
		.antMatchers("/api/v1/signup", "/api/v1/login","/api/v1/otp/validation", "/api/v1/property/*/*","/v2/api-docs",
				"/swagger-resources/**", "/swagger-ui.html", "/webjars/**","/reseller_app/version").permitAll()
		.anyRequest().authenticated()
		.and()
		.exceptionHandling()
		.authenticationEntryPoint(unauthorizedHandler)
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		//http.addFilterBefore(authenticationTokenFilterBean, UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(authenticationTokenFilterBean,UsernamePasswordAuthenticationFilter.class );
	}

}
