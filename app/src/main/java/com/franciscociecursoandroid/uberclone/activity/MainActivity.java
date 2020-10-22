package com.franciscociecursoandroid.uberclone.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.franciscociecursoandroid.uberclone.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
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
