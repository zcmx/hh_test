package ru.hh.test;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class ReplyEmployerDialog extends DialogFragment{
    private AlertDialog.Builder builder;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.employer_response)).setPositiveButton(R.string.Ok, null);
        builder.setMessage("asdasd");
        return builder.create();
    }

    public void setMess(String str){
        builder.setMessage(str);
    }
}
