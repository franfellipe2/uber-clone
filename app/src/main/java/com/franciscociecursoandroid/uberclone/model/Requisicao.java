package com.franciscociecursoandroid.uberclone.model;

import com.google.firebase.database.Exclude;

public class Requisicao {
    @Exclude
    public static final String TABLENAME = "requisicoes";
    private String id, status, requisicao;
    User passageiro;
    User motorista;
    Endereco destino;
    Endereco origem;

    public Endereco getOrigem() {
        return origem;
    }

    public void setOrigem(Endereco origem) {
        this.origem = origem;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = RequisicaoStatus.valueOf(status).toString();
    }

    public String getRequisicao() {
        return requisicao;
    }

    public void setRequisicao(String requisicao) {
        this.requisicao = requisicao;
    }

    public User getPassageiro() {
        return passageiro;
    }

    public void setPassageiro(User passageiro) {
        this.passageiro = passageiro;
    }

    public User getMotorista() {
        return motorista;
    }

    public void setMotorista(User motorista) {
        this.motorista = motorista;
    }

    public Endereco getDestino() {
        return destino;
    }

    public void setDestino(Endereco destino) {
        this.destino = destino;
    }
}
