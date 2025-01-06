package com.joaocarv05.teste_tecnico_picpay.infra.security;

import com.joaocarv05.teste_tecnico_picpay.domain.user.User;

public interface TokenService {
    public String createSignedJWT(User user);
    public String getTokenClaim(String token);
    public boolean isTokenAuthentic(String token);
}
