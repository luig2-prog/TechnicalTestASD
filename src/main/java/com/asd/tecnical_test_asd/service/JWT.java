package com.asd.tecnical_test_asd.service;

import com.asd.tecnical_test_asd.config.JWTAuthorizationFilter;
import com.asd.tecnical_test_asd.dto.UserDTO;
import com.asd.tecnical_test_asd.model.Role;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JWT {

    @Value("${app.jwt-secret}")
    private String secret;
    private static final Logger log = LoggerFactory.getLogger(JWT.class);

    public String getJWTToken(UserDTO user, String ip) throws JsonProcessingException {
        System.out.println(secret);
        ObjectMapper mapper = new ObjectMapper();
        String token = Jwts.builder()
                .setId("achJWT")
                .setSubject (mapper.writeValueAsString(user))
                .claim("ip", ip)
                .claim("authorities",   mapearRoles(user.getRole())
                )
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(SignatureAlgorithm.HS256, secret.getBytes()).compact();
        return token;
    }

    private Collection<? extends GrantedAuthority> mapearRoles(Role rol){
        return Collections.singleton(new SimpleGrantedAuthority(rol.getName()));
    }

}
