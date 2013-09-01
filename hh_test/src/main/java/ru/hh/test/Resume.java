package ru.hh.test;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

public class Resume extends FragmentActivity {
    private DatePicker datePicker;
    private AlertDialog.Builder alertDialog;
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
    private String reply = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resume);
        this.datePicker = new DatePicker(this);
        this.alertDialog = new AlertDialog.Builder(this);
        emptyResumeFragment1 = getString(R.string.fragment_empty_resume_1);
        emptyResumeFragment2 = getString(R.string.fragment_empty_resume_2);
        String replyFormFragment1 = getString(R.string.fragment_empty_resume_3);
        if (savedInstanceState == null) {
            FragmentManager manager = getSupportFragmentManager();
            ResumeEmptyFragment1 resumeEmptyFragment1 = new ResumeEmptyFragment1();
            ResumeEmptyFragment2 resumeEmptyFragment2 = new ResumeEmptyFragment2();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.empty_resume_fragment1, resumeEmptyFragment1, emptyResumeFragment1);
            transaction.add(R.id.empty_resume_fragment2, resumeEmptyFragment2, emptyResumeFragment2);
            transaction.commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        View viewFragment1 = getSupportFragmentManager().findFragmentByTag(emptyResumeFragment1).getView();
        View viewFragment2 = getSupportFragmentManager().findFragmentByTag(emptyResumeFragment2).getView();

        ((EditText)viewFragment1.findViewById(R.id.birthday)).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) setBirthDay();
            }
        });
        ((EditText)viewFragment1.findViewById(R.id.birthday)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBirthDay();
            }
        });
        Spinner sex = (Spinner) viewFragment1.findViewById(R.id.sex_spinner);
        ArrayAdapter<CharSequence> sexAdapter = ArrayAdapter.createFromResource(this, R.array.sex, android.R.layout.simple_spinner_item);
        sexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sex.setAdapter(sexAdapter);
        Spinner currency = (Spinner) viewFragment2.findViewById(R.id.currency_spinner);
        ArrayAdapter<CharSequence> currencyAdapter = ArrayAdapter.createFromResource(this, R.array.currency, android.R.layout.simple_spinner_item);
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currency.setAdapter(currencyAdapter);
    }

    public void setBirthDay() {
        showDialog(0);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
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

    public void sendResume(View view) {
        Intent sendResume = new Intent(this, Reply.class);
        View viewFragment1 = getSupportFragmentManager().findFragmentByTag(emptyResumeFragment1).getView();
        View viewFragment2 = getSupportFragmentManager().findFragmentByTag(emptyResumeFragment2).getView();
        sendResume.putExtra(FIO, ((EditText) viewFragment1.findViewById(R.id.full_name)).getText().toString());
        sendResume.putExtra(BIRTHDAY, ((EditText)viewFragment1.findViewById(R.id.birthday)).getText().toString());
        sendResume.putExtra(SEX, ((Spinner) viewFragment1.findViewById(R.id.sex_spinner)).getSelectedItem().toString());
        sendResume.putExtra(POSITION, ((EditText) viewFragment1.findViewById(R.id.position)).getText().toString());
        String salary = ((EditText) viewFragment2.findViewById(R.id.salary)).getText().toString();
        if (!salary.trim().isEmpty())
            sendResume.putExtra(SALARY, new StringBuilder().append(salary).append(getString(R.string.space)).append(((Spinner) viewFragment2.findViewById(R.id.currency_spinner)).getSelectedItem().toString()).toString());
        sendResume.putExtra(PHONE, ((EditText) viewFragment2.findViewById(R.id.phone)).getText().toString());
        sendResume.putExtra(EMAIL, ((EditText) viewFragment2.findViewById(R.id.email)).getText().toString());
        startActivityForResult(sendResume, REQUEST_CODE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!reply.trim().isEmpty()) {
            DialogFragment dialogFragment = new ReplyEmployerDialog(reply);
            dialogFragment.show(getSupportFragmentManager(), null);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                reply = data.getData().toString();
            }
        }
    }
}
