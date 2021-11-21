package com.cuberreality.security;

import com.cuberreality.config.AppConfig;
import com.cuberreality.constant.AppConstant;
import com.cuberreality.constant.JwtCalims;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtProviderServiceImp implements Serializable, JwtProviderService {

    @Autowired
    private AppConfig appConfig;

    private static final long serialVersionUID = 7009591013858184025L;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    @Override
    public String getAppTypeFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return (String) claims.get(JwtCalims.APP_TYPE);
    }


    public String generateToken(Authentication authentication,String appType) {
        final String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(appConfig.getAuthorities_key(), authorities)
                .claim(JwtCalims.APP_TYPE,appType)
                .signWith(SignatureAlgorithm.HS256, appConfig.getSigning_key())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(
                        new Date(System.currentTimeMillis() + appConfig.getAccess_token_validity_seconds() * 1000))
                .compact();
    }

    public Boolean validateToken(String token, CustomUserDetails customUserDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(customUserDetails.getUsername()) && !isTokenExpired(token));
    }


    public UsernamePasswordAuthenticationToken getAuthentication( final String token,final CustomUserDetails customUserDetails) {
        final JwtParser jwtParser = Jwts.parser().setSigningKey(appConfig.getSigning_key());
        final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
        final Claims claims = claimsJws.getBody();

        final Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(appConfig.getAuthorities_key()).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(customUserDetails, AppConstant.LOGIN_PASSWORD, authorities);
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(appConfig.getSigning_key()).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
}
