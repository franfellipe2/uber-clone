package com.franciscociecursoandroid.uberclone.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.franciscociecursoandroid.uberclone.R;
import com.franciscociecursoandroid.uberclone.model.User;
import com.franciscociecursoandroid.uberclone.model.services.Login;
import com.franciscociecursoandroid.uberclone.utils.Permissoes;
import com.franciscociecursoandroid.uberclone.widgets.Alerts;

public class MainActivity extends AppCompatActivity {

    private String[] permissions = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Login.signOut();
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        Permissoes.validarPermissoes(permissions, this, 1);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void openViewLogin(View view) {
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
    }

    public void openViewCreateLogin(View view) {
        Intent i = new Intent(getApplicationContext(), CreateLoginActivity.class);
        startActivity(i);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int permitionResult : grantResults) {
            if (permitionResult == PackageManager.PERMISSION_DENIED) {
                Alerts.dialogConfirmWarning(
                        MainActivity.this,
                        "Para utilizar o app, é necessario aceitar as pemissões!",
                        new Alerts.OnConfirmeListener() {
                            @Override
                            public void onConfirme() {
                                startActivity(new Intent(MainActivity.this, MainActivity.class));
                            }

                            @Override
                            public void onCancel() {
                                finish();
                            }
                        }
                );
            }
        }
    }
}
