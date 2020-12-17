package com.dylan.sqlite_demo;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;

import javax.xml.validation.Validator;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddActivity extends AppCompatActivity {
    private Validator nonempty_validate;
    private EditText mPhone, mName, mEmail;
    private Button mBackBtn, mSubmitBtn;
    UserSQLHelper userDataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.TBAdd);
        setSupportActionBar(mToolbar);
        ActionBar ac = getSupportActionBar();
        ac.setDisplayHomeAsUpEnabled(true);

        userDataSource = new UserSQLHelper(this);


        mPhone = findViewById(R.id.et_phone_add);
        mName = findViewById(R.id.et_name_add);
        mEmail = findViewById(R.id.et_email_add);

        mBackBtn = findViewById(R.id.btn_cancel_add);
        mSubmitBtn = findViewById(R.id.btn_save_add);
        mBackBtn.setOnClickListener(view -> finish());
        mSubmitBtn.setOnClickListener(view -> addRecord());

    }

    public void addRecord() {
        String phone, name, email;
        phone = mPhone.getText().toString();
        if (phone.isEmpty()) {
            mPhone.setError(getResources().getString(R.string.error_phone));
            return;
        }

        name = mName.getText().toString();
        if (name.isEmpty()) {
            mName.setError(getResources().getString(R.string.error_name));
            return;
        }

        email = mEmail.getText().toString();
        if (email.isEmpty()) {
            mEmail.setError(getResources().getString(R.string.error_email));
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError(getResources().getString(R.string.error_invalid_email));
            return;
        }

        UserRecord record = new UserRecord();
        record.setPhone(phone);
        record.setName(name);
        record.setEmail(email);

        userDataSource.insertUser(record);



        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userDataSource.close();
    }
}