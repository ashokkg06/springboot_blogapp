package com.example.blogapp.blogapp.security;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class JWTService {
    // TODO: MOVE jwt token to .properties file
    private static final String JWT_KEY = "ijf2u39nre993nflwkmf8bt83ke93jg309234lf923";
    private Algorithm algorithm = Algorithm.HMAC256(JWT_KEY);

    public String createJwt(Long userId) {
        return JWT.create()
                        .withSubject(userId.toString())
                        .withIssuedAt(new Date())
                        // .withExpiresAt(null) //Handle expiry
                        .sign(algorithm);
    }

    public Long retrieveUserId(String jwtString) {
        var decodedJwt = JWT.decode(jwtString);
        Long userId = Long.valueOf(decodedJwt.getSubject());
        return userId;
        // try {
        //     String decodedJwt = JWT.decode(jwtString).toString();
        //     Long userId = Long.valueOf(decodedJwt);
        //     return userId;
        // } catch (JWTDecodeException e) {
        //     throw new Illegal
        // } catch (NumberFormatException e) {

        // }
    }

}
