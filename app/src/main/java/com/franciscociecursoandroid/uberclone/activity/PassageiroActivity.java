package com.franciscociecursoandroid.uberclone.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.franciscociecursoandroid.uberclone.model.Endereco;
import com.franciscociecursoandroid.uberclone.model.EnderecoFactory;
import com.franciscociecursoandroid.uberclone.model.services.Login;
import com.franciscociecursoandroid.uberclone.widgets.Alerts;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.os.HandlerThread;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.franciscociecursoandroid.uberclone.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class PassageiroActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    // Componentes
    View viewConfirmarDestino;
    EditText editDestino;
    TextView textDestino;
    Button btnConfirmarDestino, btnCancelarDestino;
    Boolean procurandoDestino = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passageiro);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        iniciarComponentes();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ocultarConfirmarDestino();
        pesquisarDestino();
    }

    public void pesquisarDestino() {

        editDestino.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (procurandoDestino == false)
                    new RecuperarDestinoAsync().execute(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private class RecuperarDestinoAsync extends AsyncTask<String, String, Address> {
        String error;
        @Override
        protected void onPreExecute() {
            viewConfirmarDestino.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected Address doInBackground(String... strings) {
            procurandoDestino = true;
            Address address = null;
            try {
                address = recuperarDestino(strings[0]);
            } catch (IOException e) {
                error ="Erro ao recuperar endereço: "+e.getMessage();
                e.printStackTrace();
            }
            return address;
        }

        @Override
        protected void onPostExecute(Address address) {
            super.onPostExecute(address);
            if (address != null) {
                confirmarDestino(address);
            }
            procurandoDestino = false;
            if(error != null){
                Alerts.dialogError(PassageiroActivity.this, error);
                error = null;
            }
        }
    }

    private Address recuperarDestino(String endereco) throws IOException {

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> listaEnderecos = geocoder.getFromLocationName(endereco, 1);
        if (listaEnderecos != null && listaEnderecos.size() > 0) {
            return listaEnderecos.get(0);
        }

        return null;
    }

    private void iniciarComponentes() {
        viewConfirmarDestino = findViewById(R.id.viewConfirmarDestino);
        editDestino = findViewById(R.id.editDestino);
        textDestino = findViewById(R.id.textDestino);
        btnConfirmarDestino = findViewById(R.id.btnConfirmarDestino);
        btnCancelarDestino = findViewById(R.id.btnCancelarDestino);
    }

    private void ocultarConfirmarDestino() {
        viewConfirmarDestino.setVisibility(View.INVISIBLE);
        textDestino.setText("");
    }

    private void confirmarDestino(Address address) {
        textDestino.setText(address.getAddressLine(0));
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        recuperaPosicaoUsuario();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void recuperaPosicaoUsuario() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                LatLng myPosition = new LatLng(latitude, longitude);

                mMap.clear();
                mMap.addMarker(new MarkerOptions()
                        .position(myPosition)
                        .title(Login.getLogin().getDisplayName())
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.usuario)));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 15));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    100000,
                    50,
                    locationListener
            );
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSair:
                Login.signOut();
                finish();
                break;
        }
        return true;
    }
}
