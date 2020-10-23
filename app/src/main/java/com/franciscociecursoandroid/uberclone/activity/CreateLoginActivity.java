package com.franciscociecursoandroid.uberclone.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;

import com.franciscociecursoandroid.uberclone.R;
import com.franciscociecursoandroid.uberclone.model.User;
import com.franciscociecursoandroid.uberclone.model.UserType;
import com.franciscociecursoandroid.uberclone.model.dao.MyFirebase;
import com.franciscociecursoandroid.uberclone.model.dao.UserDao;
import com.franciscociecursoandroid.uberclone.utils.Check;
import com.franciscociecursoandroid.uberclone.widgets.Alerts;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.UserProfileChangeRequest;

public class CreateLoginActivity extends AppCompatActivity {

    Activity activity;
    ProgressBar progressBar;
    EditText name, email, password, confirmPassword;
    RadioGroup typeUser;
    Button btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_login);

        name = findViewById(R.id.editName);
        email = findViewById(R.id.editEmail);
        password = findViewById(R.id.editSenha);
        confirmPassword = findViewById(R.id.editConfirmarSenha);
        typeUser = findViewById(R.id.radioTypeUser);
        progressBar = findViewById(R.id.progressBarLogin);
        btnCreate = findViewById(R.id.btnCreate);
        this.activity = this;

        progressBar.setVisibility(View.INVISIBLE);

    }

    public void onLoginCreated(Task<AuthResult> task) {
        createUser(task);
        updateLoginName(name.getText().toString());
    }

    public void onUserCreated() {
        progressBar.setVisibility(View.INVISIBLE);
        Intent i = new Intent(this, MapsActivity.class);
        startActivity(i);
        finish();
    }

    public void createLogin(View view) {

        if (!formValidate()) return;
        progressBar.setVisibility(View.VISIBLE);

        MyFirebase.getAuth()
                .createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            onLoginCreated(task);
                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                            Alerts.dialogError(activity, task.getException().getMessage());
                            task.getException().printStackTrace();
                        }
                    }
                });
    }

    public void createUser(Task<AuthResult> task) {
        User user = new User();
        user.setName(name.getText().toString());
        user.setEmail(email.getText().toString());
        user.setPassword(password.getText().toString());
        switch (typeUser.getCheckedRadioButtonId()) {
            case R.id.radioPassageiro:
                user.setType(UserType.PASSAGEIRO.toString());
                break;
            case R.id.radioMotorista:
                user.setType(UserType.MOTORISTA.toString());
                break;
        }
        user.setId(task.getResult().getUser().getUid());
        UserDao.create(user, taskDao -> {
            if (!taskDao.isSuccessful()) {
                Alerts.dialogError(activity, taskDao.getException().getMessage());
                progressBar.setVisibility(View.INVISIBLE);
            } else {
                onUserCreated();
            }
        });

    }

    public boolean formValidate() {
        String error = "";
        Log.d("VALIDAR", "formValidate: " + Check.isEmpty(name) + name.getText());
        if (Check.isEmpty(name))
            error += "> Nome em branco!\n";
        if (Check.isEmpty(email)) {
            error += "> Email em branco!\n";
        } else if (!Check.isEmailValid(email.getText().toString())) {
            error += "> Informe um email válido!\n";
        }
        if (Check.isEmpty(password)) {
            error += "> Senha em branco!\n";
        } else if (password.getText().length() < 6) {
            error += "> A senha deve ter no mínimo 6 caracteres!\n";
        } else if (Check.isEmpty(confirmPassword)) {
            error += "> A Confirme a senha!\n";
        } else if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
            error += "> As senhas não conferem!\n";
        }
        if (typeUser.getCheckedRadioButtonId() == -1) {
            error += "> Tipo de cadastro, não informado(Passageiro/Motorista)!\n";
        }

        if (error.equals("")) {
            return true;
        } else {
            Alerts.dialogError(this, error);
            return false;
        }

    }

    public void updateLoginName(String name) {

        UserProfileChangeRequest.Builder builderUser = new UserProfileChangeRequest.Builder();
        builderUser.setDisplayName(name);
        UserProfileChangeRequest newData = builderUser.build();

        MyFirebase.getAuth().getCurrentUser().updateProfile(newData);

    }

}
