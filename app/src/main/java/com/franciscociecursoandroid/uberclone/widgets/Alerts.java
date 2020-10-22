package com.franciscociecursoandroid.uberclone.widgets;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.AttrRes;

import com.franciscociecursoandroid.uberclone.R;

public class Alerts {

    public static void dialogError(Context context, String error) {
        AlertDialog.Builder al = new AlertDialog.Builder(context);
        al.setTitle("Erro!")
                .setMessage(error)
                .setIcon(R.drawable.ic_error_24dp)
                .setPositiveButton("OK", null)
                .show();
    }

}
