package com.dylan.sqlite_demo;

import android.os.Parcel;
import android.os.Parcelable;

public class UserRecord implements Parcelable {
    private int id;
    private String phone;
    private String name;
    private String email;

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public UserRecord createFromParcel(Parcel in) {
            return new UserRecord(in);
        }

        public UserRecord[] newArray(int size) {
            return new UserRecord[size];
        }
    };

    protected UserRecord(Parcel in){
        id = in.readInt();
        phone = in.readString();
        name = in.readString();
        email = in.readString();

    }

    public UserRecord() {
    }

    public UserRecord(String phone, String name, String email) {
        this.phone = phone;
        this.name = name;
        this.email = email;
    }

    public UserRecord(int id, String phone, String name, String email) {
        this.id = id;
        this.phone = phone;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.phone);
        parcel.writeString(this.name);
        parcel.writeString(this.email);
    }

    @Override
    public String toString() {
        return "UserRecord{" +
                "phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
