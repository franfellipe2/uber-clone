package com.franciscociecursoandroid.uberclone.model.dao;

import com.franciscociecursoandroid.uberclone.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DatabaseReference;

public class UserDao {

    public static void create(User user, OnCompleteListener listener) {
        DatabaseReference ref = MyFirebase.getReference();
        ref.child(User.TABLENAME).child(user.getId()).setValue(user).addOnCompleteListener(listener);
    }

    public static DatabaseReference findById(String uiId){
        DatabaseReference ref = MyFirebase.getReference();
        return ref.child(User.TABLENAME).child(uiId);
    }
}
