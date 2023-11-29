package com.example.blogapp.blogapp.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;

import jakarta.servlet.http.HttpServletRequest;

public class JWTAuthenticationConverter implements AuthenticationConverter {

    @Override
    public Authentication convert(HttpServletRequest request) {
        var header = request.getHeader("Authorization");
        if(header==null || !(header.startsWith("Bearer")))
            return null;
        
        var jwt = header.replace("Bearer ", "");

        return new JWTAuthentication(jwt);
    }
}
