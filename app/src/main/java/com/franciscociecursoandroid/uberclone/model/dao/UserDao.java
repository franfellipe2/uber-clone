package com.franciscociecursoandroid.uberclone.model.dao;

import com.franciscociecursoandroid.uberclone.model.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

public class UserDao {

    public static Task<Void> create(User user) {
        DatabaseReference ref = MyFirebase.getReference();
        return ref.child(User.TABLENAME).child(user.getId()).setValue(user);
    }

    public static DatabaseReference findById(String uiId){
        DatabaseReference ref = MyFirebase.getReference();
        return ref.child(User.TABLENAME).child(uiId);
    }
}
