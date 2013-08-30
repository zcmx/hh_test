package ru.hh.test;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class ReplyEmployerDialog extends DialogFragment {
    private String mes;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.employer_response)).setMessage(mes).setPositiveButton(R.string.Ok, null);
        return builder.create();
    }

    public ReplyEmployerDialog(String mes) {
        this.mes = mes;
    }

}
