package com.franciscociecursoandroid.uberclone.widgets;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

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

    public static void dialogAlert(Activity activity, String s) {
        AlertDialog.Builder al = new AlertDialog.Builder(activity);
        al.setTitle("Atenção!")
                .setMessage(s)
                .setIcon(R.drawable.ic_alert_24dp)
                .setPositiveButton("OK", null)
                .show();
    }

    public static void dialogInfo(Activity activity, String s) {
        AlertDialog.Builder al = new AlertDialog.Builder(activity);
        al.setTitle("Sucesso!")
                .setMessage(s)
                .setIcon(R.drawable.ic_info_24dp)
                .setPositiveButton("OK", null)
                .show();
    }

    public static void dialogConfirmWarning(Activity activity, String s, OnConfirmeListener listener) {
        AlertDialog.Builder al = new AlertDialog.Builder(activity);
        al.setTitle("Alerta!")
                .setMessage(s)
                .setIcon(R.drawable.ic_alert_24dp)
                .setPositiveButton("CONFIRMAR", (DialogInterface dialog, int which) -> {
                    listener.onConfirme();
                })
                .setNegativeButton("CANCELAR", (DialogInterface dialog, int which) -> {
                    listener.onCancel();
                }).show();
    }

    public static void dialogConfirmWarning(Activity activity, String title, String message, OnConfirmeListener listener) {
        AlertDialog.Builder al = new AlertDialog.Builder(activity);
        al.setTitle(title != null ? title: "Confirmar!")
                .setMessage(message)
                .setIcon(R.drawable.ic_alert_24dp)
                .setPositiveButton("CONFIRMAR", (DialogInterface dialog, int which) -> {
                    listener.onConfirme();
                })
                .setNegativeButton("CANCELAR", (DialogInterface dialog, int which) -> {
                    listener.onCancel();
                }).show();
    }

    public static void dialogConfirmSuccess(Activity activity, String title, String message, OnConfirmeListener listener) {
        AlertDialog.Builder al = new AlertDialog.Builder(activity);
        al.setTitle(title != null ? title: "Confirmar!")
                .setMessage(message)
                .setIcon(R.drawable.ic_success_24dp)
                .setPositiveButton("CONFIRMAR", (DialogInterface dialog, int which) -> {
                    listener.onConfirme();
                })
                .setNegativeButton("CANCELAR", (DialogInterface dialog, int which) -> {
                    listener.onCancel();
                }).show();
    }

    public interface OnConfirmeListener {
        void onConfirme();

        void onCancel();
    }
}
