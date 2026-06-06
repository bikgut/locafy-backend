package com.locafy.locafy_backend.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "LCFY";
    private static final Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

    public String crearToken(String username, String rol){
        return JWT.create()
                .withSubject(username)
                .withIssuer("LocafyApp")
                .withClaim("rol", rol)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 ))
                .sign(algorithm);
    }

    public DecodedJWT validarYDecdificar(String token){
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY))
                .withIssuer("LocafyApp")
                .build();

        return verifier.verify(token);
    }

    public String extraerUsername(String token){
        return validarYDecdificar(token).getSubject();
    }

    public String extraerRol(String token){
        return validarYDecdificar(token).getClaim("rol").asString();
    }

    public boolean TokenValido(String token){
        try {
            validarYDecdificar(token);
            return true;
        }catch (JWTVerificationException e){
            return false;
        }
    }
}
