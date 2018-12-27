package com.example.uitstark.dailys_notes.DatabaseManage;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.example.uitstark.dailys_notes.DTO.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dailynote";
    private static final int DATABASE_VERSION = 1;

    // USER=================================
    protected static final String TABLE_USER_NAME = "user";

    protected static final String KEY_USER__ID = "id";
    protected static final String KEY_USER__NAME = "name";
    protected static final String KEY_USER__EMAIL = "email";

    protected static final String KEY_USER__PASSWORD = "password";


    // BIRTHDAY==================================
    protected static final String TABLE_BIRTHDAY_NAME = "birthday";

    protected static final String KEY_BIRTHDAY_ID = "id";
    protected static final String KEY_BIRTHDAY_IDUSER = "iduser";
    protected static final String KEY_BIRTHDAY_NAME = "friendname";
    protected static final String KEY_BIRTHDAY_DATE = "date";
    protected static final String KEY_BIRTHDAY_TIME = "time";
    protected static final String KEY_BIRTHDAY_DETAIL     = "detail";
    protected static final String KEY_BIRTHDAY_STATUS = "status"; //1 da cap nhat len sever, 0 chua
    protected static final String KEY_BIRTHDAY_COLOR ="color";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        //===============USER=============================
        String strCreateTableUser=
                String.format("CREATE TABLE %s" +
                                "(%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT)",
                        TABLE_USER_NAME, KEY_USER__ID, KEY_USER__NAME, KEY_USER__EMAIL,KEY_USER__PASSWORD);

        db.execSQL(strCreateTableUser);

        //=============BIRTHDAY============================
        String strCreateTableBirthday=
                String.format("CREATE TABLE %s" +
                                "(%s INTEGER PRIMARY KEY,%s INTEGER, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s INTEGER,%s INTEGER)",
                        TABLE_BIRTHDAY_NAME, KEY_BIRTHDAY_ID,KEY_BIRTHDAY_IDUSER,
                        KEY_BIRTHDAY_NAME, KEY_BIRTHDAY_DATE, KEY_BIRTHDAY_TIME,KEY_BIRTHDAY_DETAIL,KEY_BIRTHDAY_STATUS, KEY_BIRTHDAY_COLOR);
        db.execSQL(strCreateTableBirthday);

        Log.d("TAGGG","create birth day table");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}