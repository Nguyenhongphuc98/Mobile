package com.example.uitstark.dailys_notes.DatabaseManage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.uitstark.dailys_notes.DTO.BirthDay;
import com.example.uitstark.dailys_notes.DTO.User;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BirthdayDAL extends DatabaseHandler {
    public BirthdayDAL(Context context) {
        super(context);
    }

    private static final String TABLE_BIRTHDAY_NAME = "birthday";

    private static final String KEY_BIRTHDAY_ID = "id";
    private static final String KEY_BIRTHDAY_IDUSER = "iduser";
    private static final String KEY_BIRTHDAY_NAME = "friendname";
    private static final String KEY_BIRTHDAY_TIME = "time";
    private static final String KEY_BIRTHDAY_DATE = "date";
    private static final String KEY_USER__DETAIL     = "detail";
    private static final String KEY_USER__STATUS = "status"; //1 da cap nhat len sever, 0 chua


    @Override
    public void onCreate(SQLiteDatabase db) {
        String strCreateTableBirthday=
                String.format("CREATE TABLE %s" +
                                "(%s INTEGER PRIMARY KEY,%s INTEGER, %s TEXT, %s TEXT, %s TEXT, %s TEXT, % INTEGER)",
                        TABLE_BIRTHDAY_NAME, KEY_BIRTHDAY_ID,KEY_BIRTHDAY_IDUSER, KEY_BIRTHDAY_NAME, KEY_BIRTHDAY_DATE, KEY_BIRTHDAY_TIME,KEY_USER__DETAIL,KEY_USER__STATUS);
        db.execSQL(strCreateTableBirthday);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String strDropTableUser = String.format("DROP TABLE IF EXISTS %s", TABLE_BIRTHDAY_NAME);
        db.execSQL(strDropTableUser);
    }

    public void addBirthday(BirthDay birthDay) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_BIRTHDAY_IDUSER, birthDay.getId_user());
        values.put(KEY_BIRTHDAY_NAME,birthDay.getName());
        values.put(KEY_BIRTHDAY_DATE, birthDay.getBornDay().toString());
        values.put(KEY_BIRTHDAY_TIME,birthDay.getTimeRemind().toString());
        values.put(KEY_USER__DETAIL,birthDay.getNote());
        values.put(KEY_USER__STATUS,birthDay.getStatus());

        //if value is empty -> don't insert -> null
        db.insert(TABLE_BIRTHDAY_NAME, null, values);
        db.close();
    }

    public BirthDay getBirthday(int birthDayId) throws ParseException {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_BIRTHDAY_NAME, null, KEY_BIRTHDAY_ID + " = ?", new String[] { String.valueOf(birthDayId) },null, null, null);
        if(cursor != null)
            cursor.moveToFirst();

        int idBirthday=cursor.getInt(0);
        int idUserBirthday=cursor.getInt(1);
        String nameFriend=cursor.getString(2);
        Date bornDate= new SimpleDateFormat().parse(cursor.getString(3));
        Time timeRemind= (Time) new SimpleDateFormat("HH:mm").parse(cursor.getString(4));
        String detail=cursor.getString(5);
        int status=cursor.getInt(6);
        BirthDay birthDay = new BirthDay(idBirthday,idUserBirthday,nameFriend ,bornDate,timeRemind ,detail,status);
        return birthDay;
    }

    public List<BirthDay> getAllBirthday() throws ParseException {
        List<BirthDay>  birthDayList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_BIRTHDAY_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false) {
            int idBirthday=cursor.getInt(0);
            int idUserBirthday=cursor.getInt(1);
            String nameFriend=cursor.getString(2);
            Date bornDate= new SimpleDateFormat().parse(cursor.getString(3));
            Time timeRemind= (Time) new SimpleDateFormat("HH:mm").parse(cursor.getString(4));
            String detail=cursor.getString(5);
            int status=cursor.getInt(6);
            BirthDay birthDay = new BirthDay(idBirthday,idUserBirthday,nameFriend ,bornDate,timeRemind ,detail,status);
            birthDayList.add(birthDay);
            cursor.moveToNext();
        }
        return birthDayList;
    }


    public void updateBirthday(BirthDay birthDay) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_BIRTHDAY_IDUSER, birthDay.getId_user());
        values.put(KEY_BIRTHDAY_NAME, birthDay.getName());
        values.put(KEY_BIRTHDAY_DATE, birthDay.getBornDay().toString());
        values.put(KEY_BIRTHDAY_TIME, birthDay.getTimeRemind().toString());
        values.put(KEY_USER__DETAIL, birthDay.getNote());
        values.put(KEY_USER__STATUS, birthDay.getStatus());

        db.update(TABLE_BIRTHDAY_NAME, values, KEY_BIRTHDAY_ID + " = ?", new String[] { String.valueOf(birthDay.getId()) });
        db.close();
    }

    public void deleteBirthday(int birthDayId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BIRTHDAY_NAME, KEY_BIRTHDAY_ID + " = ?", new String[] { String.valueOf(birthDayId) });
        db.close();
    }
}
