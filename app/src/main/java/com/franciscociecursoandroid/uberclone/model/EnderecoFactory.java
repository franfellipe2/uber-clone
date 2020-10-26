package com.franciscociecursoandroid.uberclone.model;

import android.location.Address;

public class EnderecoFactory {

    public static Endereco createFromAddress(Address address){
        Endereco e = new Endereco();
        e.setCidade(address.getAdminArea() != null ? address.getAdminArea() : "");
        e.setCep(address.getPostalCode() != null ? address.getPostalCode() : "");
        e.setBairro(address.getSubLocality() != null ? address.getSubLocality() : "");
        e.setLogradouro(address.getThoroughfare() != null ? address.getThoroughfare() : "");
        e.setNumero(address.getFeatureName() != null ? address.getFeatureName(): "");
        e.setLatitude(String.valueOf(address.getLatitude()));
        e.setLongitude(String.valueOf(address.getLongitude()));
        e.setUf(address.getSubAdminArea() != null ? address.getSubAdminArea() : "");
        return e;
    }
}
