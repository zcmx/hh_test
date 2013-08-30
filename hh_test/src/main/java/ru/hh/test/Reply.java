package ru.hh.test;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;


public class Reply extends FragmentActivity {
    private String fragmentResume1;
    private String fragmentResume2;
    private String fragmentResume3;

    public void onCreate(Bundle savedInstanceState) {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            setTheme(R.style.AppThemeHolo);
        } else {
            setTheme(R.style.AppBaseTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reply);
        fragmentResume1 = getString(R.string.fragment_resume_1);
        fragmentResume2 = getString(R.string.fragment_resume_2);
        fragmentResume3 = getString(R.string.fragment_resume_3);
        if (savedInstanceState == null) {
            ResumeBlankFragment1 fragment1 = new ResumeBlankFragment1();
            ResumeBlankFragment2 fragment2 = new ResumeBlankFragment2();
            SendFormFragment fragment3 = new SendFormFragment();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.resume_part_1, fragment1, fragmentResume1);
            transaction.add(R.id.resume_part_2, fragment2, fragmentResume2);
            transaction.add(R.id.resume_reply, fragment3, fragmentResume3);
            transaction.commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        View fragmentResumeView1 = getSupportFragmentManager().findFragmentByTag(fragmentResume1).getView();
        View fragmentResumeView2 = getSupportFragmentManager().findFragmentByTag(fragmentResume2).getView();
        View fragmentSendView = getSupportFragmentManager().findFragmentByTag(fragmentResume3).getView();
        ((EditText)fragmentSendView.findViewById(R.id.editReply)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ScrollView)findViewById(R.id.scroll_send_form)).scrollBy(0,10);
            }
        });
        Intent intent = getIntent();
        ((TextView) findViewById(R.id.fullName_result)).setText(intent.getStringExtra(Resume.FIO));
        ((TextView) fragmentResumeView1.findViewById(R.id.birthday_result)).setText(intent.getStringExtra(Resume.BIRTHDAY));
        ((TextView) fragmentResumeView1.findViewById(R.id.sex_result)).setText(intent.getStringExtra(Resume.SEX));
        ((TextView) fragmentResumeView1.findViewById(R.id.position_result)).setText(intent.getStringExtra(Resume.POSITION));
        ((TextView) fragmentResumeView2.findViewById(R.id.salary_result)).setText(intent.getStringExtra(Resume.SALARY));
        ((TextView) fragmentResumeView2.findViewById(R.id.phone_result)).setText(intent.getStringExtra(Resume.PHONE));
        ((TextView) fragmentResumeView2.findViewById(R.id.email_result)).setText(intent.getStringExtra(Resume.EMAIL));
    }

    public void reply(View view) {
        View view1 = getSupportFragmentManager().findFragmentByTag(fragmentResume3).getView();
        String reply = ((EditText) view1.findViewById(R.id.editReply)).getText().toString();
        Intent intent = new Intent();
        intent.setData(Uri.parse(reply));
        setResult(RESULT_OK, intent);
        finish();
    }
}