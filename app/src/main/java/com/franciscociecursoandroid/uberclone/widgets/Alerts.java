package com.franciscociecursoandroid.uberclone.widgets;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.AttrRes;

import com.franciscociecursoandroid.uberclone.R;

public class Alerts {

    public static void dialogError(Activity activity, String error) {
        AlertDialog.Builder al = new AlertDialog.Builder(activity);
        al.setTitle("Erro!")
                .setMessage(error)
                .setIcon(R.drawable.ic_error_24dp)
                .setPositiveButton("OK", null)
                .show();
    }

    public static void dialogSuccess(Activity activity, String s) {
        AlertDialog.Builder al = new AlertDialog.Builder(activity);
        al.setTitle("Sucesso!")
                .setMessage(s)
                .setIcon(R.drawable.ic_success_24dp)
                .setPositiveButton("OK", null)
                .show();
    }
}
