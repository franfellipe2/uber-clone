package com.franciscociecursoandroid.uberclone.model;

public enum UserType {
    PASSAGEIRO("p"), MOTORISTA("m");
    private String type;

    UserType(String type) {
        this.type = type;
    }
}
