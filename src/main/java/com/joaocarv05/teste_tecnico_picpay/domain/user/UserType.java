package com.joaocarv05.teste_tecnico_picpay.domain.user;

public enum UserType {
    COMMON_USER("COMMON_USER"),
    MERCHANT("MERCHANT");

    private final String type;

    UserType(String role){
        this.type = role;
    }

    public String getType() {
        return type;
    }
}
