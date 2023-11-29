package com.example.blogapp.blogapp.security;

import java.util.Collection;

import javax.security.auth.Subject;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.example.blogapp.blogapp.users.UserEntity;

public class JWTAuthentication implements Authentication {

    String jwt;
    UserEntity userEntity;
    
    public JWTAuthentication(String jwt) {
        this.jwt = jwt;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getCredentials() {
        // TODO Auto-generated method stub
        return jwt;
    }

    @Override
    public Object getDetails() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserEntity getPrincipal() {
        // TODO Auto-generated method stub
        return userEntity;
    }

    @Override
    public boolean isAuthenticated() {
        // TODO Auto-generated method stub
        return (userEntity!=null);
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }
    
     @Override
    public boolean implies(Subject subject) {
        return Authentication.super.implies(subject);
    }
}
