package com.microservice2.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private static final String SECRET_KEY = "MY_SECRET_KEY";
    private static final long EXPIRATION_TIME = 86400000; // 1 day


    public String validateTokenAndRetrieveSubject(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET_KEY))
                .build()
                .verify(token)
                .getSubject();
    }
}
