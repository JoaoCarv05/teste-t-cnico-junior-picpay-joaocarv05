package com.joaocarv05.teste_tecnico_picpay.infra.security;

import com.joaocarv05.teste_tecnico_picpay.domain.user.User;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Primary
@Service
public class TokenServiceRSAimpl implements TokenService {

    @Value("${jwttoken.expiration.value}")
    int JWTexpirationTimeInMinutes;

    RSAKey rsaJWK = new RSAKeyGenerator(2048)
            .keyID("123")
            .generate();
    RSAKey rsaPublicJWK = rsaJWK.toPublicJWK();

    JWSSigner signer = new RSASSASigner(rsaJWK);


    public TokenServiceRSAimpl() throws JOSEException {
    }

    @SneakyThrows
    @Override
    public String createSignedJWT(User user) {
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .issuer("API")
                .subject("my-api")
                .expirationTime(expirationTokenTime())
                .claim("login", user.getEmail())
                .build();
        SignedJWT signedJWT = new SignedJWT(new JWSHeader.Builder(JWSAlgorithm.RS256).build(), jwtClaimsSet);
        signedJWT.sign(signer);
        return signedJWT.serialize();
    }

    @SneakyThrows
    @Override
    public String getTokenClaim(String token) {
        SignedJWT signedJWT = SignedJWT.parse(token);
        String login = signedJWT.getJWTClaimsSet().getStringClaim("login");
        return login;
    }

    @SneakyThrows
    @Override
    public boolean isTokenAuthentic(String token) {
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWSVerifier verifier = new RSASSAVerifier(rsaPublicJWK);
        return signedJWT.verify(verifier);
    }

    private Date expirationTokenTime() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime newTime = now.plusMinutes(JWTexpirationTimeInMinutes);
        return Date.from(newTime.toInstant(ZoneOffset.of("-03:00")));
    }
}
