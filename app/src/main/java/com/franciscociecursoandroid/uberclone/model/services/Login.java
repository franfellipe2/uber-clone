package com.franciscociecursoandroid.uberclone.model.services;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.franciscociecursoandroid.uberclone.activity.MotoqueiroRequisicoesActivity;
import com.franciscociecursoandroid.uberclone.activity.PassageiroActivity;
import com.franciscociecursoandroid.uberclone.model.User;
import com.franciscociecursoandroid.uberclone.model.UserType;
import com.franciscociecursoandroid.uberclone.model.dao.MyFirebase;
import com.franciscociecursoandroid.uberclone.model.dao.UserDao;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class Login {

    public static FirebaseUser getLogin() {
        return MyFirebase.getAuth().getCurrentUser();
    }

    public static User getUserData() {
        FirebaseUser login = Login.getLogin();
        User u = new User();
        u.setId(login.getUid());
        u.setName(login.getDisplayName());
        u.setEmail(login.getEmail());
        return u;
    }

    public static boolean hasLogin() {
        return getLogin() != null;
    }

    public static void redirecionarUsuario(Activity activity, Boolean finishAtivity) {

        if (!hasLogin()) return;
        UserDao.findById(getLogin().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User u = snapshot.getValue(User.class);
                Intent i = null;
                if (u.getType() == UserType.MOTORISTA.toString()) {
                    i = new Intent(activity, MotoqueiroRequisicoesActivity.class);
                } else {
                    i = new Intent(activity, PassageiroActivity.class);
                }
                activity.startActivity(i);
                if (finishAtivity)
                    activity.finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static void signOut() {
        MyFirebase.getAuth().signOut();
    }
}
