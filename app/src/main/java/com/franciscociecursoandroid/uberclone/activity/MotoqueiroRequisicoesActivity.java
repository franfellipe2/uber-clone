package com.franciscociecursoandroid.uberclone.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.franciscociecursoandroid.uberclone.R;
import com.franciscociecursoandroid.uberclone.model.services.Login;

public class MotoqueiroRequisicoesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motoqueiro_requisicoes);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuSair:
                Login.signOut();
                finish();
                break;
        }
        return true;
    }
}
