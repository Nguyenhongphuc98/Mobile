package com.example.uitstark.dailys_notes.DatabaseManage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String strDropTableUser = String.format("DROP TABLE IF EXISTS %s", TABLE_BIRTHDAY_NAME);
        db.execSQL(strDropTableUser);
    }

    public void addBirthday(BirthDay birthDay) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_BIRTHDAY_IDUSER, birthDay.getId_user());
       // Log.e("IDUSER1",String.valueOf(birthDay.getId_user()));
        values.put(KEY_BIRTHDAY_NAME,birthDay.getName());
        values.put(KEY_BIRTHDAY_DATE, birthDay.getBornDay());
        values.put(KEY_BIRTHDAY_TIME,birthDay.getTimeRemind());
        values.put(KEY_BIRTHDAY_DETAIL,birthDay.getNote());
        values.put(KEY_BIRTHDAY_STATUS,birthDay.getStatus());
        values.put(KEY_BIRTHDAY_COLOR,birthDay.getColor());

        //if value is empty -> don't insert -> null
        db.insert(TABLE_BIRTHDAY_NAME, null, values);
        db.close();

        Log.d("TAGGG","save success");
    }

    public BirthDay getBirthday(int birthDayId) throws ParseException {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_BIRTHDAY_NAME, null,
                KEY_BIRTHDAY_ID + " = ?",
                new String[] { String.valueOf(birthDayId) },
                null, null, null);

        BirthDay birthDay =null;
        if(cursor != null) {
            Log.d("TESSS","ok");
            cursor.moveToFirst();
            int idBirthday=cursor.getInt(0);
            int idUserBirthday=cursor.getInt(1);
            String nameFriend=cursor.getString(2);
            String bornDate= cursor.getString(3);
            String timeRemind= cursor.getString(4);
            String detail=cursor.getString(5);
            int status=cursor.getInt(6);
            int color=cursor.getInt(7);

            birthDay= new BirthDay(idBirthday,idUserBirthday,nameFriend ,bornDate,timeRemind ,detail,status,color);
        }

        db.close();
        return birthDay;
    }

    public List<BirthDay> getBirthdayOf(int IdUser) throws ParseException {

        List<BirthDay>  birthDayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Log.e("IDUSER",String.valueOf(IdUser));

        //Cursor cursor = db.query(TABLE_BIRTHDAY_NAME, null, KEY_USER__ID + " = ?", new String[] { String.valueOf(IdUser) },null, null, null);
        Cursor cursor = db.query(TABLE_BIRTHDAY_NAME,
                null, KEY_BIRTHDAY_IDUSER + " = ?",
                new String[] { String.valueOf(IdUser) },
                null, null, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            int idBirthday=cursor.getInt(0);
            int idUserBirthday=cursor.getInt(1);
            String nameFriend=cursor.getString(2);
            String bornDate= cursor.getString(3);
            String timeRemind= cursor.getString(4);
            String detail=cursor.getString(5);
            int status=cursor.getInt(6);
            int color=cursor.getInt(7);
            BirthDay birthDay = new BirthDay(idBirthday,idUserBirthday,nameFriend ,bornDate,timeRemind ,detail,status,color);
            birthDayList.add(birthDay);
            cursor.moveToNext();
        }

        db.close();
        return birthDayList;
    }

    public List<BirthDay> getAllBirthday() throws ParseException {
       // Log.e("ERRRR","1");
        List<BirthDay>  birthDayList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_BIRTHDAY_NAME;

       // Log.e("ERRRR","2");
        SQLiteDatabase db = this.getReadableDatabase();
       // Log.e("ERRRR","3");
        Cursor cursor = db.rawQuery(query, null);
      //  Log.e("ERRRR","4");
        cursor.moveToFirst();



        while(!cursor.isAfterLast()) {
            int idBirthday=cursor.getInt(0);
            int idUserBirthday=cursor.getInt(1);
            String nameFriend=cursor.getString(2);
            String bornDate= cursor.getString(3);
            String timeRemind= cursor.getString(4);
            String detail=cursor.getString(5);
            int status=cursor.getInt(6);
            int color=cursor.getInt(7);
            BirthDay birthDay = new BirthDay(idBirthday,idUserBirthday,nameFriend ,bornDate,timeRemind ,detail,status,color);
            birthDayList.add(birthDay);
            cursor.moveToNext();
        }

        db.close();
        return birthDayList;
    }


    public void updateBirthday(BirthDay birthDay) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_BIRTHDAY_IDUSER, birthDay.getId_user());
        values.put(KEY_BIRTHDAY_NAME, birthDay.getName());
        values.put(KEY_BIRTHDAY_DATE, birthDay.getBornDay());
        values.put(KEY_BIRTHDAY_TIME, birthDay.getTimeRemind());
        values.put(KEY_BIRTHDAY_DETAIL,birthDay.getNote());
        values.put(KEY_BIRTHDAY_STATUS,birthDay.getStatus());
        values.put(KEY_BIRTHDAY_COLOR,birthDay.getStatus());

        db.update(TABLE_BIRTHDAY_NAME, values, KEY_BIRTHDAY_ID + " = ?", new String[] { String.valueOf(birthDay.getId()) });
        db.close();
    }

    public void deleteBirthday(int birthDayId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BIRTHDAY_NAME, KEY_BIRTHDAY_ID + " = ?", new String[] { String.valueOf(birthDayId) });
        db.close();
    }
}
