package ru.hh.test;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.*;

public class Resume extends FragmentActivity {
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
    private String emptyResumeFragment1;
    private String emptyResumeFragment2;
    private String replyFormFragment1;
    private static String reply = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resume);
        this.alertDialog = new AlertDialog.Builder(this);
        this.datePicker = new DatePicker(this);
        emptyResumeFragment1 = getString(R.string.fragment_empty_resume_1);
        emptyResumeFragment2 = getString(R.string.fragment_empty_resume_2);
        replyFormFragment1 = getString(R.string.fragment_empty_resume_3);
        if(savedInstanceState == null){
            ResumeEmptyFragment1 resumeEmptyFragment1 = new ResumeEmptyFragment1();
            ResumeEmptyFragment2 resumeEmptyFragment2 = new ResumeEmptyFragment2();
            ReplyFormFragment replyFormFragment = new ReplyFormFragment();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.empty_resume_fragment1, resumeEmptyFragment1,emptyResumeFragment1);
            transaction.add(R.id.empty_resume_fragment2, resumeEmptyFragment2,emptyResumeFragment2);
            transaction.add(R.id.reply_form_fragment, replyFormFragment,replyFormFragment1);
            transaction.commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        View viewFragment1 = getSupportFragmentManager().findFragmentByTag(emptyResumeFragment1).getView();
        View viewFragment2 = getSupportFragmentManager().findFragmentByTag(emptyResumeFragment2).getView();
        View viewFragment3 = getSupportFragmentManager().findFragmentByTag(replyFormFragment1).getView();
        ((TextView) viewFragment3.findViewById(R.id.reply)).setMovementMethod(new ScrollingMovementMethod());
        viewFragment1.findViewById(R.id.birthday).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) setBirthDay();
            }
        });
        viewFragment1.findViewById(R.id.birthday).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBirthDay();
            }
        });
        Spinner sex = (Spinner)viewFragment1.findViewById(R.id.sex_spinner);
        ArrayAdapter<CharSequence> sexAdapter = ArrayAdapter.createFromResource(this, R.array.sex, android.R.layout.simple_spinner_item);
        sexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sex.setAdapter(sexAdapter);
        Spinner currency = (Spinner)viewFragment2.findViewById(R.id.currency_spinner);
        ArrayAdapter<CharSequence> currencyAdapter = ArrayAdapter.createFromResource(this, R.array.currency, android.R.layout.simple_spinner_item);
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currency.setAdapter(currencyAdapter);
        activateReplyForm();
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
                        ((EditText) findViewById(R.id.birthday)).setText(new StringBuilder().append(datePicker.getDayOfMonth()).append(getString(R.string.dot)).append(datePicker.getMonth()).append(getString(R.string.dot)).append(datePicker.getYear()).toString());
                    }
                });
                return this.alertDialog.create();
        }
        return null;
    }

    public void sendResume(View view){
        Intent sendResume = new Intent(this, Reply.class);
        View viewFragment1 = getSupportFragmentManager().findFragmentByTag(emptyResumeFragment1).getView();
        View viewFragment2 = getSupportFragmentManager().findFragmentByTag(emptyResumeFragment2).getView();
        sendResume.putExtra(FIO, ((EditText)viewFragment1.findViewById(R.id.full_name)).getText().toString());
        sendResume.putExtra(BIRTHDAY, ((EditText)viewFragment1.findViewById(R.id.birthday)).getText().toString());
        sendResume.putExtra(SEX, ((Spinner)viewFragment1.findViewById(R.id.sex_spinner)).getSelectedItem().toString());
        sendResume.putExtra(POSITION, ((EditText)viewFragment1.findViewById(R.id.position)).getText().toString());
        String salary = ((EditText)viewFragment2.findViewById(R.id.salary)).getText().toString();
        if(!salary.trim().isEmpty())
            sendResume.putExtra(SALARY, new StringBuilder().append(salary).append(getString(R.string.space)).append(((Spinner)viewFragment2.findViewById(R.id.currency_spinner)).getSelectedItem().toString()).toString() );
        sendResume.putExtra(PHONE, ((EditText)viewFragment2.findViewById(R.id.phone)).getText().toString());
        sendResume.putExtra(EMAIL, ((EditText)viewFragment2.findViewById(R.id.email)).getText().toString());
        startActivityForResult(sendResume, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
                reply = data.getData().toString();
                activateReplyForm();
            }
        }
    }

    private void activateReplyForm() {
        View viewFragment3 = getSupportFragmentManager().findFragmentByTag(replyFormFragment1).getView();
        if(!reply.trim().isEmpty()){
            ((TextView)viewFragment3.findViewById(R.id.reply_label)).setHint(R.string.employer_response);
            ((TextView)viewFragment3.findViewById(R.id.reply)).setText(reply);
        }else{
            ((TextView)viewFragment3.findViewById(R.id.reply_label)).setHint(R.string.empty);
            ((TextView)viewFragment3.findViewById(R.id.reply)).setText(R.string.empty);
        }
    }
}
