package com.franciscociecursoandroid.uberclone.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.franciscociecursoandroid.uberclone.R;
import com.franciscociecursoandroid.uberclone.model.Endereco;
import com.franciscociecursoandroid.uberclone.model.EnderecoFactory;
import com.franciscociecursoandroid.uberclone.model.Requisicao;
import com.franciscociecursoandroid.uberclone.model.RequisicaoStatus;
import com.franciscociecursoandroid.uberclone.model.dao.RequisicaoDao;
import com.franciscociecursoandroid.uberclone.model.services.Login;
import com.franciscociecursoandroid.uberclone.widgets.Alerts;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class PassageiroActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;

    // Componentes
    CheckedTextView checkedTextDestino;
    LinearLayout checkDestino;
    EditText editDestino;
    Button btnChamarUber;

    // geral
    Address addressEncontrado;
    Endereco destino;
    Boolean procurandoDestino = false;
    private LatLng locaPassageiro;


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
        checkDestino.setVisibility(View.INVISIBLE);
        // Selecionar destino
        checkedTextDestino.setOnClickListener(v -> destinoToggle());
        // Procurar destino
        editDestino.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                checkDestino.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String textBusca = s.toString().trim();
                desSelecionarDestino(textBusca.length() == 0 ? true : false);
                if (!procurandoDestino && (count > 0 || before > 0))
                    new RecuperarDestinoAsync().execute(textBusca);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // Chamar Uber
        btnChamarUber.setOnClickListener(v -> chamarUber());
    }

    private void chamarUber() {
        if (destino == null || !checkedTextDestino.isChecked()) {
            Alerts.dialogAlert(this, "Você não escolheu um destino!");
        } else {
            Alerts.dialogConfirmSuccess(this, "Confirma o endereço de destino?", destino.toString(), new Alerts.OnConfirmeListener() {
                @Override
                public void onConfirme() {
                    salvarRequisicao(destino);
                    desSelecionarDestino(true);
                }

                @Override
                public void onCancel() {
                    desSelecionarDestino(true);
                }
            });

        }
    }

    private Address recuperarDestino(String s) throws IOException {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addressList = geocoder.getFromLocationName(s, 1);
        if (addressList.size() > 0) {
            return addressList.get(0);
        }
        return null;
    }

    class RecuperarDestinoAsync extends AsyncTask<String, String, Address> {
        String error;

        @Override
        protected void onPreExecute() {
            destino = null;
        }

        @Override
        protected Address doInBackground(String... strings) {
            procurandoDestino = true;
            try {
                return recuperarDestino(strings[0]);
            } catch (IOException e) {
                error = e.getMessage();
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            checkedTextDestino.setText("....");
        }

        @Override
        protected void onPostExecute(Address address) {
            procurandoDestino = false;
            if (address != null) {
                checkedTextDestino.setText(address.getAddressLine(0));
                addressEncontrado = address;
                destino = EnderecoFactory.createFromAddress(address);
            } else {
                checkedTextDestino.setText("Procurando...");
            }
        }
    }

    private void destinoToggle() {
        if (!checkedTextDestino.isChecked()) {
            selecionarDestino();
        } else {
            desSelecionarDestino(false);
        }
    }

    private void selecionarDestino() {
        debugAddress(addressEncontrado);
        checkedTextDestino.setChecked(true);
        checkedTextDestino.setCheckMarkDrawable(R.drawable.ic_checked_24dp);
    }

    private void desSelecionarDestino(Boolean ocultarView) {
        destino = null;
        checkedTextDestino.setChecked(false);
        checkedTextDestino.setCheckMarkDrawable(R.drawable.ic_check_24dp);
        if (ocultarView)
            checkDestino.setVisibility(View.INVISIBLE);
    }

    private void iniciarComponentes() {
        editDestino = findViewById(R.id.editDestino);
        checkDestino = findViewById(R.id.layoutCheckDestino);
        checkedTextDestino = findViewById(R.id.checkedTextDestino);
        btnChamarUber = findViewById(R.id.btnChamarUber);
    }

    public void salvarRequisicao(Endereco destino) {

        Requisicao requisicao = new Requisicao();
        requisicao.setStatus(RequisicaoStatus.AGUARDADNDO.toString());
        requisicao.setDestino(destino);
        requisicao.setPassageiro(Login.getUserData());

        Endereco origem = new Endereco();
        origem.setLatitude(String.valueOf(locaPassageiro.latitude));
        origem.setLongitude(String.valueOf(locaPassageiro.longitude));
        requisicao.setOrigem(origem);

        RequisicaoDao.create(requisicao).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Alerts.dialogSuccess(this, "Requisição Criada com sucesso!");
            } else {
                Alerts.dialogError(this, task.getException().getMessage() + "\n\n" + task.getException().getStackTrace().toString());
            }
        });
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
                locaPassageiro = new LatLng(latitude, longitude);

                mMap.clear();
                mMap.addMarker(new MarkerOptions()
                        .position(locaPassageiro)
                        .title("Meu local")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.usuario)));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locaPassageiro, 15));
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


    public void debugAddress(Address address) {
        StringBuilder builder = new StringBuilder();
        builder.append("\ngetLocality: " + address.getLocality());
        builder.append("\ngetSubLocality: " + address.getSubLocality());
        builder.append("\ngetAdminArea: " + address.getAdminArea());
        builder.append("\ngetSubAdminArea: " + address.getSubAdminArea());
        builder.append("\ngetCountryCode: " + address.getCountryCode());
        builder.append("\ngetCountryName: " + address.getCountryName());
        builder.append("\ngetFeatureName: " + address.getFeatureName());
        builder.append("\ngetLocale: " + address.getLocale());
        builder.append("\ngetPostalCode: " + address.getPostalCode());
        builder.append("\ngetSubThoroughfare: " + address.getSubThoroughfare());
        builder.append("\ngetThoroughfare: " + address.getThoroughfare());
        builder.append("\ngetPremises: " + address.getPremises());
        Log.d("ADDRESS", builder.toString());
    }

}
