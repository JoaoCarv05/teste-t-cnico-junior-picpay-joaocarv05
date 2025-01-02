package com.joaocarv05.teste_tecnico_picpay.infra.security;

import com.joaocarv05.teste_tecnico_picpay.domain.user.User;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService{

    @Value("${jwttoken.jwtsecret.value}")
    String JWTsecret;
    @Value("${jwttoken.expiration.value}")
    int JWTexpirationTimeInMinutes;


    @SneakyThrows
    public String createSignedJWT(User user) {
            return getSignedToken(user);
    }

    @SneakyThrows
    public String verifyToken(String token){
            return getVerifiedToken(token);
    }

    private String getVerifiedToken(String token) throws ParseException, JOSEException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
        JWSVerifier verifier = new MACVerifier(JWTsecret);
        if (!signedJWT.verify(verifier)) {
            return null;
        } else {
            return claims.getStringClaim("login");
        }
    }

    private String getSignedToken(User user) throws JOSEException {
        JWSSigner jwsSigner = new MACSigner(JWTsecret);
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .issuer("API")
                .subject("my-api")
                .expirationTime(expirationTokenTime())
                .claim("login", user.getEmail())
                .build();
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256
        ), claimsSet);
        signedJWT.sign(jwsSigner);
        return signedJWT.serialize();
    }

    private Date expirationTokenTime() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime newTime = now.plusMinutes(JWTexpirationTimeInMinutes);
        return Date.from(newTime.toInstant(ZoneOffset.of("-03:00")));
    }
}
