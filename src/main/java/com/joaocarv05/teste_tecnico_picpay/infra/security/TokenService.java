package com.joaocarv05.teste_tecnico_picpay.infra.security;

import com.joaocarv05.teste_tecnico_picpay.domain.user.User;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.text.ParseException;

@Service
public class TokenService {

    SecureRandom random = new SecureRandom();
    byte[] sharedSecret = new byte[32];

    public TokenService() {
        random.nextBytes(sharedSecret);
    }

    /**
     * retorna um JWT com o login do usúario assinado.
     * @param user
     * @return JWT
     * @throws JOSEException
     */
    public String sign(User user) throws JOSEException {
        JWSSigner signer = new MACSigner(sharedSecret);
        JWSObject jwsObject = new JWSObject(
                new JWSHeader(JWSAlgorithm.HS256),
                new Payload(user.getEmail())
        );
        jwsObject.sign(signer);
        return jwsObject.serialize();
    }

    /**
     * verifica a autenticidade do token.
     * @param token
     * @return retorna o payload do token caso sua autenticidade seja comprovada
     * @throws JOSEException
     * @throws ParseException
     */
    public String verify(String token) throws JOSEException, ParseException {
        JWSObject jwsObject = JWSObject.parse(token);
        JWSVerifier verifier = new MACVerifier(sharedSecret);
        return jwsObject.getPayload().toString();
    }

}
