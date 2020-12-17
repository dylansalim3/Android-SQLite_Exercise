package com.dylan.sqlite_demo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class UserSQLHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "users.db";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE  " + UserContract.User.TABLE_NAME + " (" +
                    UserContract.User.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    UserContract.User.COLUMN_PHONE + " TEXT," +
                    UserContract.User.COLUMN_NAME + " TEXT," +
                    UserContract.User.COLUMN_EMAIL + " TEXT)";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + UserContract.User.TABLE_NAME;
    private String[] allColumn = {
            UserContract.User.COLUMN_ID,
            UserContract.User.COLUMN_PHONE,
            UserContract.User.COLUMN_NAME,
            UserContract.User.COLUMN_EMAIL
    };

    public UserSQLHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        onUpgrade(sqLiteDatabase, oldVersion, newVersion);
    }

    public void insertUser(UserRecord userRecord) {
        ContentValues values = new ContentValues();
        values.put(UserContract.User.COLUMN_PHONE, userRecord.getPhone());
        values.put(UserContract.User.COLUMN_NAME, userRecord.getName());
        values.put(UserContract.User.COLUMN_EMAIL, userRecord.getEmail());

        SQLiteDatabase database = this.getWritableDatabase();
        database.insert(UserContract.User.TABLE_NAME, null, values);

    }

    public List<UserRecord> getAllUsers() {
        List<UserRecord> records = new ArrayList<>();

        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(UserContract.User.TABLE_NAME, allColumn, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            UserRecord userRecord = new UserRecord();
            userRecord.setId(cursor.getInt(0));
            userRecord.setPhone(cursor.getString(1));
            userRecord.setName(cursor.getString(2));
            userRecord.setEmail(cursor.getString(3));
            records.add(userRecord);
            cursor.moveToNext();
        }
        return records;
    }

    public int deleteUser(int id) {
        String selection = UserContract.User.COLUMN_ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(id)};
        SQLiteDatabase database = this.getWritableDatabase();
        return database.delete(UserContract.User.TABLE_NAME, selection, selectionArgs);
    }

    public int updateUser(UserRecord userRecord){
        ContentValues values = new ContentValues();
        values.put(UserContract.User.COLUMN_PHONE, userRecord.getPhone());
        values.put(UserContract.User.COLUMN_NAME, userRecord.getName());
        values.put(UserContract.User.COLUMN_EMAIL, userRecord.getEmail());

        String selection = UserContract.User.COLUMN_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(userRecord.getId()) };
        SQLiteDatabase database = this.getWritableDatabase();

        return database.update(
                UserContract.User.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

}
