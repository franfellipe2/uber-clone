package com.franciscociecursoandroid.uberclone.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.franciscociecursoandroid.uberclone.R;
import com.franciscociecursoandroid.uberclone.model.services.Login;

public class RedirectLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redirect_login);
        getSupportActionBar().hide();

        Login.redirecionarUsuario(this, true);
    }
}
