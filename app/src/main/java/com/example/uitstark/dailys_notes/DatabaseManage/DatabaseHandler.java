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
    private static final int DATABASE_VERSION = 2;

    // USER=================================
    protected static final String TABLE_USER_NAME = "user";

    protected static final String KEY_USER__ID = "id";
    protected static final String KEY_USER__NAME = "name";
    protected static final String KEY_USER__EMAIL = "email";

    protected static final String KEY_USER__PASSWORD = "password";

// Note===============================
    protected static final String TABLE_NOTE_NAME = "note";

    protected static final String KEY_NOTE__ID = "id";
    protected static final String KEY_NOTE__IDUSER = "iduser";
    protected static final String KEY_NOTE__TITLE = "title";

    protected static final String KEY_NOTE__CONTENT = "content";
    protected static final String KEY_NOTE_COLOR= "color";

    // TODOList===============================
    protected static final String TABLE_TODO_NAME = "todo";
    protected static final String KEY_TODO__ID = "id";
    protected static final String KEY_TODO__IDUSER = "iduser";
    protected static final String KEY_TODO__TITLE = "title";
    protected static final String KEY_TODO__CONTENT = "content";
    protected static final String KEY_TODO_TIME = "time";
    protected static final String KEY_TODO_STATUS= "status";
    protected static final String KEY_TODO_COLOR= "color";


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


        //===============NOTE=============================
        String strCreateTableNote=
                String.format("CREATE TABLE %s" +
                                "(%s INTEGER PRIMARY KEY,%s INTEGER, %s TEXT, %s TEXT,%s INTEGER)",
                        TABLE_NOTE_NAME, KEY_NOTE__ID, KEY_NOTE__IDUSER, KEY_NOTE__TITLE,KEY_NOTE__CONTENT,KEY_NOTE_COLOR);

        db.execSQL(strCreateTableNote);


        //===============TODOLIST=============================
        String strCreateTableToDo=
                String.format("CREATE TABLE %s" +
                                "(%s INTEGER PRIMARY KEY,%s INTEGER, %s TEXT, %s TEXT, %s TEXT,%s INTEGER,%s INTEGER)",
                        TABLE_TODO_NAME, KEY_TODO__ID, KEY_TODO__IDUSER, KEY_TODO__TITLE,KEY_TODO__CONTENT,KEY_TODO_TIME,KEY_TODO_STATUS,KEY_TODO_COLOR);

        db.execSQL(strCreateTableToDo);

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