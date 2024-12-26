package com.joaocarv05.teste_tecnico_picpay.infra.security;

import com.joaocarv05.teste_tecnico_picpay.domain.user.User;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.util.Date;

@Service
public class TokenService {

    @Value("${jwtsecret.value}")
    String JWTsecret;

    /**
     * retorna um JWT com o login do usúario assinado.
     *
     * @param user
     * @return JWT
     * @throws JOSEException
     */
    public String sign(User user) throws JOSEException {
        JWSSigner jwsSigner = new MACSigner(JWTsecret);
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .issuer("API")
                .subject("my-api")
                .expirationTime(new Date(new Date().getTime() + 60 * 60 * 1000))
                .claim("login", user.getEmail())
                .build();
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256
        ), claimsSet);
        signedJWT.sign(jwsSigner);
        return signedJWT.serialize();
    }

    /**
     * verifica a autenticidade do token.
     *
     * @param token
     * @return retorna o login do token caso sua autenticidade seja comprovada
     * @throws JOSEException
     * @throws ParseException
     */
    public String verifyToken(String token) throws JOSEException, ParseException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
        JWSVerifier verifier = new MACVerifier(JWTsecret);
        if (!signedJWT.verify(verifier) || new Date().after(claims.getExpirationTime())) {
            throw new JOSEException("token inválido e/ou expirado");
        } else {
            return claims.getStringClaim("login");
        }
    }
}
