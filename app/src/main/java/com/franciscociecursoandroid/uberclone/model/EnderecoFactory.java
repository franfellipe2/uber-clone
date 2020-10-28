package com.franciscociecursoandroid.uberclone.model;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

public class EnderecoFactory {

    public static Endereco createFromAddress(Address address){
        Endereco e = new Endereco();
        e.setEstado(address.getAdminArea() != null ? address.getAdminArea() : "");
        e.setCidade(address.getSubAdminArea() != null ? address.getSubAdminArea() : "");
        e.setLogradouro(address.getThoroughfare() != null ? address.getThoroughfare() : "");
        e.setNumero(address.getSubThoroughfare() != null ? address.getSubThoroughfare(): "");
        e.setCep(address.getPostalCode() != null ? address.getPostalCode() : "");
        e.setLatitude(String.valueOf(address.getLatitude()));
        e.setLongitude(String.valueOf(address.getLongitude()));
        return e;
    }

    public static Endereco createFromLatitudoLongitude(Context context, LatLng latLng) throws IOException {
        Geocoder geocoder = new Geocoder(context);
        List<Address> result = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
        if(result.size() > 0){
            return createFromAddress(result.get(0));
        }
        return null;
    }
}
