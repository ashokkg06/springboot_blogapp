package com.example.blogapp.blogapp.security;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class JwtServiceTests {
    
    JWTService jwtService = new JWTService();

    @Test
    public void canCreateJwtFromUserId() {
        var jwt = jwtService.createJwt(123L);
        System.out.println(jwt);
        assertNotNull(jwt);
    }
}
