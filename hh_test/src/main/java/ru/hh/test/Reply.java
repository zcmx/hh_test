package ru.hh.test;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;


public class Reply extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reply);
        Intent intent = getIntent();
        ((TextView)findViewById(R.id.fullName_result)).setText(intent.getStringExtra(Resume.FIO));
        ((TextView)findViewById(R.id.birthday_result)).setText(intent.getStringExtra(Resume.BIRTHDAY));
        ((TextView)findViewById(R.id.sex_result)).setText(intent.getStringExtra(Resume.SEX));
        ((TextView)findViewById(R.id.position_result)).setText(intent.getStringExtra(Resume.POSITION));
        ((TextView)findViewById(R.id.salary_result)).setText(intent.getStringExtra(Resume.SALARY));
        ((TextView)findViewById(R.id.phone_result)).setText(intent.getStringExtra(Resume.PHONE));
        ((TextView)findViewById(R.id.email_result)).setText(intent.getStringExtra(Resume.EMAIL));
    }

    public void reply(View view){
        String reply = ((EditText)findViewById(R.id.editReply)).getText().toString();
        Intent intent = new Intent();
        intent.setData(Uri.parse(reply));
        setResult(RESULT_OK, intent);
        finish();
    }
}