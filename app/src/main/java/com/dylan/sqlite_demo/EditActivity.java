package com.dylan.sqlite_demo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;

import javax.xml.validation.Validator;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class EditActivity extends AppCompatActivity {
    private Validator nonempty_validate;
    private EditText mPhone, mName, mEmail;
    private Button mBackBtn, mSubmitBtn;
    UserSQLHelper userDataSource;
    private int selectedId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.TBEdit);
        setSupportActionBar(mToolbar);
        ActionBar ac = getSupportActionBar();
        ac.setDisplayHomeAsUpEnabled(true);

        userDataSource = new UserSQLHelper(this);

        Bundle bundle = getIntent().getExtras();


        mPhone = findViewById(R.id.et_phone_edit);
        mName = findViewById(R.id.et_name_edit);
        mEmail = findViewById(R.id.et_email_edit);

        if (null != bundle && null != bundle.getParcelable(getResources().getString(R.string.user_record))) {
            UserRecord userRecord = bundle.getParcelable(getResources().getString(R.string.user_record));
            selectedId = userRecord.getId();
            setInitialValue(userRecord);
        } else {
            finish();
        }

        mBackBtn = findViewById(R.id.btn_cancel_edit);
        mSubmitBtn = findViewById(R.id.btn_save_edit);
        mBackBtn.setOnClickListener(view -> finish());
        mSubmitBtn.setOnClickListener(view -> updateRecord());
    }

    private void setInitialValue(UserRecord userRecord) {
        mPhone.setText(userRecord.getPhone());
        mName.setText(userRecord.getName());
        mEmail.setText(userRecord.getEmail());
    }

    private void updateRecord() {
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
        record.setId(selectedId);
        record.setPhone(phone);
        record.setName(name);
        record.setEmail(email);

        int updatedId = userDataSource.updateUser(record);

        Intent intent = new Intent();
        intent.putExtra("MESSAGE", String.format("User detail number %d have been updated",updatedId));
        setResult(RESULT_OK, intent);
        finish();


        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userDataSource.close();
    }
}