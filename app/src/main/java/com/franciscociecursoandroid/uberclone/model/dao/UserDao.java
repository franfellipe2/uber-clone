package com.franciscociecursoandroid.uberclone.model.dao;

import androidx.annotation.NonNull;

import com.franciscociecursoandroid.uberclone.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

public class UserDao {
    public static void create(User user, OnCompleteListener listener) {
        DatabaseReference ref = MyFirebase.getReference();
        ref.child(User.TABLENAME).child(user.getId()).setValue(user).addOnCompleteListener(listener);
    }
}
