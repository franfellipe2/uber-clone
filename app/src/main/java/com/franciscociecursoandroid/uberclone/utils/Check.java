package com.franciscociecursoandroid.uberclone.utils;

import android.text.Editable;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Check {

    public static boolean isEmpty(EditText text){
        return text.getText().toString().isEmpty();
    }

    public static boolean isEmailValid(String email){
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        return Pattern.compile(regex).matcher(email).matches();
    }


}
