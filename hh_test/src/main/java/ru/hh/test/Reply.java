package ru.hh.test;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.EditText;


public class Reply extends FragmentActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reply);
        Intent intent = getIntent();
        ResumeFinalFragment resumeFinal = new ResumeFinalFragment();
//        if(savedInstanceState == null){
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.resume_final_fragment, resumeFinal);
            transaction.commit();
//        }
//        ((TextView)resumeFinal.getActivity().findViewById(R.id.fullName_result)).setText(intent.getStringExtra(Resume.FIO));
//        ((TextView)resumeFinal.getActivity().findViewById(R.id.birthday_result)).setText(intent.getStringExtra(Resume.BIRTHDAY));
//        ((TextView)resumeFinal.getActivity().findViewById(R.id.sex_result)).setText(intent.getStringExtra(Resume.SEX));
//        ((TextView)resumeFinal.getActivity().findViewById(R.id.position_result)).setText(intent.getStringExtra(Resume.POSITION));
//        ((TextView)resumeFinal.getActivity().findViewById(R.id.salary_result)).setText(intent.getStringExtra(Resume.SALARY));
//        ((TextView)resumeFinal.getActivity().findViewById(R.id.phone_result)).setText(intent.getStringExtra(Resume.PHONE));
//        ((TextView)resumeFinal.getActivity().findViewById(R.id.email_result)).setText(intent.getStringExtra(Resume.EMAIL));
    }

    public void reply(View view){
        String reply = ((EditText)findViewById(R.id.editReply)).getText().toString();
        Intent intent = new Intent();
        intent.setData(Uri.parse(reply));
        setResult(RESULT_OK, intent);
        finish();
    }
}