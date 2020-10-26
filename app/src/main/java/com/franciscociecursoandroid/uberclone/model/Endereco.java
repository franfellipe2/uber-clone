package com.franciscociecursoandroid.uberclone.model;

import androidx.annotation.NonNull;

public class Endereco {

    private String logradouro;
    private String numero;
    private String cidade;
    private String bairro;
    private String cep;
    private String uf;

    private String latitude;
    private String longitude;



    public Endereco() {
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    @NonNull
    @Override
    public String toString() {
        // "Av. Juscelino Kubitschek, 1860 - IPÊ AMARELO, \n Frutal - MG, 38200-000";
        return String.format(
                "%s, %s - %s,\n %s -  %s, %s",
                logradouro,
                "",
                bairro,
                cidade,
                uf,
                cep
        );
    }
}
