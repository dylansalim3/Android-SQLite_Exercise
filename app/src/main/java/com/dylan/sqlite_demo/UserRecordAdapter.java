package com.dylan.sqlite_demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class UserRecordAdapter extends ArrayAdapter<UserRecord> {
    public UserRecordAdapter(@NonNull Context context, int resource, @NonNull List<UserRecord> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        UserRecord userRecord = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_record, parent, false);
        }
        TextView mPhone, mName, mEmail;

        mPhone = (TextView) convertView.findViewById(R.id.tv_phone);
        mName = (TextView) convertView.findViewById(R.id.tv_name);
        mEmail = (TextView) convertView.findViewById(R.id.tv_email);

        mPhone.setText(userRecord.getPhone());
        mName.setText(userRecord.getName());
        mEmail.setText(userRecord.getEmail());

        return convertView;
    }
}
