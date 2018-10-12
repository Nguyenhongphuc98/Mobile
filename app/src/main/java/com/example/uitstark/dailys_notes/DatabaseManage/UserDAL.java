package com.example.uitstark.dailys_notes.DatabaseManage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.uitstark.dailys_notes.DTO.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAL extends DatabaseHandler {

    private static final String TABLE_USER_NAME = "user";

    private static final String KEY_USER__ID = "id";
    private static final String KEY_USER__NAME = "name";
    private static final String KEY_USER__EMAIL = "email";
    private static final String KEY_USER__PHONE_NUMBER = "phone_number";
    private static final String KEY_USER__PASSWORD = "password";

    public UserDAL(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String strCreateTableUser=
                String.format("CREATE TABLE %s" +
                                "(%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
                        TABLE_USER_NAME, KEY_USER__ID, KEY_USER__NAME, KEY_USER__EMAIL, KEY_USER__PHONE_NUMBER,KEY_USER__PASSWORD);

        db.execSQL(strCreateTableUser);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String strDropTableUser = String.format("DROP TABLE IF EXISTS %s", TABLE_USER_NAME);
        db.execSQL(strDropTableUser);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER__EMAIL, user.getEmail());
        values.put(KEY_USER__PASSWORD,user.getPassWord());
        values.put(KEY_USER__NAME, user.getFullName());
        values.put(KEY_USER__PHONE_NUMBER,user.getPhoneNumber());

        //if value is empty -> don't insert -> null
        db.insert(TABLE_USER_NAME, null, values);
        db.close();
    }

    public User getUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER_NAME, null, KEY_USER__ID + " = ?", new String[] { String.valueOf(userId) },null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        User user = new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4));
        return user;
    }

    public List<User> getAllUser() {
        List<User>  userList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_USER_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false) {
            User user = new User(cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3),cursor.getString(4));
            userList.add(user);
            cursor.moveToNext();
        }
        return userList;
    }


    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER__NAME, user.getFullName());
        values.put(KEY_USER__EMAIL, user.getEmail());
        values.put(KEY_USER__PHONE_NUMBER,user.getPhoneNumber());
        values.put(KEY_USER__PASSWORD,user.getPassWord());

        db.update(TABLE_USER_NAME, values, KEY_USER__ID + " = ?", new String[] { String.valueOf(user.getId()) });
        db.close();
    }

    public void deleteUser(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER_NAME, KEY_USER__ID + " = ?", new String[] { String.valueOf(userId) });
        db.close();
    }
}
