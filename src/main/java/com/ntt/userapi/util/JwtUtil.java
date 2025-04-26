package com.ntt.userapi.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;


import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final  long EXPERATIO_TIME = 1000 * 60 * 10;
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);


    public String generarToken (String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt( new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPERATIO_TIME))
                .signWith(key)
                .compact();

    }


}
