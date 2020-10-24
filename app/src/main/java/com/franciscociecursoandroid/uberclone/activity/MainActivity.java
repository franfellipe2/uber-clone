package com.franciscociecursoandroid.uberclone.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.franciscociecursoandroid.uberclone.R;
import com.franciscociecursoandroid.uberclone.model.User;
import com.franciscociecursoandroid.uberclone.model.services.Login;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       if(!Login.hasLogin()) {
            setContentView(R.layout.activity_main);
       }else {
            setContentView(R.layout.redirecionando);
        }
        getSupportActionBar().hide();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Login.redirecionarUsuario(this, true);
    }

    public void  openViewLogin(View view){
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
    }

    public void  openViewCreateLogin(View view){
        Intent i = new Intent(getApplicationContext(), CreateLoginActivity.class);
        startActivity(i);
    }

}
