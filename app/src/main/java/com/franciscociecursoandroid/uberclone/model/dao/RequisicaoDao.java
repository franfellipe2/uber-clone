package com.franciscociecursoandroid.uberclone.model.dao;

import com.franciscociecursoandroid.uberclone.model.Requisicao;
import com.franciscociecursoandroid.uberclone.model.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

public class RequisicaoDao {

    public static Task<Void> create(Requisicao obj) {
        DatabaseReference ref = MyFirebase.getReference();
        DatabaseReference requisicaoRef = ref.child(Requisicao.TABLENAME);
        String id = requisicaoRef.push().getKey();
        obj.setId(id);
        return requisicaoRef.child(id).setValue(obj);
    }

    public static DatabaseReference findById(String uiId){
        DatabaseReference ref = MyFirebase.getReference();
        return ref.child(User.TABLENAME).child(uiId);
    }
}
