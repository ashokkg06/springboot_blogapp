package com.example.blogapp.blogapp.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.example.blogapp.blogapp.users.UsersService;

public class JWTAuthenticationManager implements AuthenticationManager {

    private JWTService jwtService;
    private UsersService usersService;

    public JWTAuthenticationManager(JWTService jwtService, UsersService usersService) {
        this.jwtService = jwtService;
        this.usersService = usersService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // TODO Auto-generated method stub

        if(authentication instanceof JWTAuthentication)
        {
            var jwtAuthentication = (JWTAuthentication) authentication;
            var jwt = jwtAuthentication.getCredentials();
            var userId = jwtService.retrieveUserId(jwt);
            var userEntity = usersService.getUser(userId);

            jwtAuthentication.userEntity = userEntity;
            jwtAuthentication.setAuthenticated(true);
            return jwtAuthentication;
        }

        throw new IllegalAccessError("Cannot authenticate non-JWT authentication");
    }
}
