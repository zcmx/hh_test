package ru.hh.test;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Resume extends Activity {
    private AlertDialog.Builder alertDialog;
    private DatePicker datePicker;
    public final static String FIO = "ru.hh.test.getFIO";
    public final static String BIRTHDAY = "ru.hh.test.getBirthday";
    public final static String SEX = "ru.hh.test.getSex";
    public final static String POSITION = "ru.hh.test.getPosition";
    public final static String SALARY = "ru.hh.test.getSalary";
    public final static String PHONE = "ru.hh.test.getPhone";
    public final static String EMAIL = "ru.hh.test.getEmail";
    static final int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resume);
        this.alertDialog = new AlertDialog.Builder(this);
        this.datePicker = new DatePicker(this);
        ((TextView)findViewById(R.id.reply)).setMovementMethod(new ScrollingMovementMethod());
        findViewById(R.id.birthday).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) setBirthDay();
            }
        });
        findViewById(R.id.birthday).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBirthDay();
            }
        });
        Spinner sex = (Spinner)findViewById(R.id.sex_spinner);
        ArrayAdapter<CharSequence> sexAdapter = ArrayAdapter.createFromResource(this, R.array.sex, android.R.layout.simple_spinner_item);
        sexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sex.setAdapter(sexAdapter);
        Spinner currency = (Spinner)findViewById(R.id.currency_spinner);
        ArrayAdapter<CharSequence> currencyAdapter = ArrayAdapter.createFromResource(this, R.array.currency, android.R.layout.simple_spinner_item);
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currency.setAdapter(currencyAdapter);
    }


    public void setBirthDay(){
        showDialog(0);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch(id){
            case 0:
                this.alertDialog.setTitle(R.string.birthday_hint).setView(datePicker).setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((EditText) findViewById(R.id.birthday)).setText(new StringBuilder().append(datePicker.getDayOfMonth()).append(R.string.dot).append(datePicker.getMonth()).append(R.string.dot).append(datePicker.getYear()).toString());
                    }
                });
                return this.alertDialog.create();
        }
        return null;
    }

    public void sendResume(View view){
        Intent sendResume = new Intent(this, Reply.class);
        sendResume.putExtra(FIO, ((EditText)findViewById(R.id.full_name)).getText().toString());
        sendResume.putExtra(BIRTHDAY, ((EditText)findViewById(R.id.birthday)).getText().toString());
        sendResume.putExtra(SEX, ((Spinner)findViewById(R.id.sex_spinner)).getSelectedItem().toString());
        sendResume.putExtra(POSITION, ((EditText)findViewById(R.id.position)).getText().toString());
        String salary = ((EditText)findViewById(R.id.salary)).getText().toString();
        if(!salary.trim().isEmpty())
            sendResume.putExtra(SALARY, new StringBuilder().append(salary).append(R.string.space).append(((Spinner)findViewById(R.id.currency_spinner)).getSelectedItem().toString()).toString() );
        sendResume.putExtra(PHONE, ((EditText)findViewById(R.id.phone)).getText().toString());
        sendResume.putExtra(EMAIL, ((EditText)findViewById(R.id.email)).getText().toString());
        startActivityForResult(sendResume, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
                String reply = data.getData().toString();
                if(!reply.trim().isEmpty()){
                    ((TextView)findViewById(R.id.reply_label)).setHint(R.string.employer_response);
                    ((TextView)findViewById(R.id.reply)).setText(reply);
                }else{
                    ((TextView)findViewById(R.id.reply_label)).setHint(R.string.empty);
                    ((TextView)findViewById(R.id.reply)).setText(R.string.empty);
                }
            }
        }
    }
}
