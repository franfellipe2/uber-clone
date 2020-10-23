package com.franciscociecursoandroid.uberclone.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.franciscociecursoandroid.uberclone.R;
import com.franciscociecursoandroid.uberclone.model.User;
import com.franciscociecursoandroid.uberclone.model.UserType;
import com.franciscociecursoandroid.uberclone.model.dao.MyFirebase;
import com.franciscociecursoandroid.uberclone.model.dao.UserDao;
import com.franciscociecursoandroid.uberclone.utils.Check;
import com.franciscociecursoandroid.uberclone.widgets.Alerts;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Activity activity;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.editEmail);
        password = findViewById(R.id.editPassword);
        progressBar = findViewById(R.id.progressBarLogin);
        activity = this;
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void signIn(View view) {
        progressBar.setVisibility(View.VISIBLE);
        if (!formValidate()) return;
        execSign();
    }

    private void onSignIn(){

        progressBar.setVisibility(View.INVISIBLE);

        Alerts.dialogSuccess(activity, "\nUsuario logado com sucesso!");

       /* UserDao.findById(MyFirebase.getAuth().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if(user.getType() == UserType.MOTORISTA.toString()){

                }else{

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
    }

    public void execSign() {
        MyFirebase.getAuth()
                .signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(task -> {
                    if(!task.isSuccessful()){
                        Alerts.dialogError(activity, task.getException().getMessage());
                        progressBar.setVisibility(View.INVISIBLE);
                    }else{
                        onSignIn();
                    }
                });
    }

    public boolean formValidate() {
        String error = "";
        if (Check.isEmpty(email)) {
            error += "> Email em branco!\n";
        } else if (!Check.isEmailValid(email.getText().toString())) {
            error += "> Informe um email válido!\n";
        }
        if (Check.isEmpty(password)) {
            error += "> Senha em branco!\n";
        }
        if (error.equals("")) {
            return true;
        } else {
            Alerts.dialogError(this, error);
            return false;
        }

    }
}
