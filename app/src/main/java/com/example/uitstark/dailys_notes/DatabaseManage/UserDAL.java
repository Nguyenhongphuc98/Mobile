package com.example.uitstark.dailys_notes.DatabaseManage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.uitstark.dailys_notes.DTO.ToDo;
import com.example.uitstark.dailys_notes.DTO.User;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class UserDAL extends DatabaseHandler {



    public UserDAL(Context context) {

        super(context);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String strDropTableUser = String.format("DROP TABLE IF EXISTS %s", TABLE_USER_NAME);
        db.execSQL(strDropTableUser);
    }


    public User queryUser(String email, String password) {

        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        Cursor cursor = db.query(TABLE_USER_NAME, new String[]{KEY_USER__ID,KEY_USER__NAME,
                        KEY_USER__EMAIL, KEY_USER__PASSWORD}, KEY_USER__EMAIL + "=? and " + KEY_USER__PASSWORD + "=?",
                new String[]{email, password}, null, null, null, "1");
        if (cursor != null)
            cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0) {
            user = new User(
                    Integer.parseInt(cursor.getString(0)) ,
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3));
        }

        db.close();
        return user;
    }
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER__NAME, user.getFullName());
        values.put(KEY_USER__EMAIL, user.getEmail());
        values.put(KEY_USER__PASSWORD,user.getPassword());
        //if value is empty -> don't insert -> null
        db.insert(TABLE_USER_NAME, null, values);
        db.close();
    }
    public User getUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER_NAME, null, KEY_USER__ID + " = ?", new String[] { String.valueOf(userId) },null, null, null);
        User user=null;
        if(cursor != null) {
            cursor.moveToFirst();
            int idUser=cursor.getInt(0);
            String username= cursor.getString(1);
            String password= cursor.getString(2);
            String fullname=cursor.getString(3);

            user= new User(idUser,username,password,fullname);
        }
        db.close();

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
                    cursor.getString(2), cursor.getString(3));
            userList.add(user);
            cursor.moveToNext();
        }

        db.close();
        return userList;
    }


    public void updateUser(int idUser,User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER__NAME, user.getFullName());
        values.put(KEY_USER__EMAIL, user.getEmail());

        values.put(KEY_USER__PASSWORD,user.getPassword());

        db.update(TABLE_USER_NAME, values, KEY_USER__ID + " = ?", new String[] { String.valueOf(idUser) });
        db.close();
    }

    public void deleteUser(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER_NAME, KEY_USER__ID + " = ?", new String[] { String.valueOf(userId) });
        db.close();
    }
}
