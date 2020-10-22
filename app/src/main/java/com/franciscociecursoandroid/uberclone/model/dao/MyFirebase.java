package com.franciscociecursoandroid.uberclone.model.dao;

import com.google.firebase.auth.FirebaseAuth;

public class MyFirebase {

    private static FirebaseAuth auth;

    public static FirebaseAuth getAuth() {
        if (auth == null) {
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }
}
