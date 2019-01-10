package com.example.uitstark.dailys_notes.DatabaseManage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.uitstark.dailys_notes.DTO.BirthDay;
import com.example.uitstark.dailys_notes.DTO.ToDo;
import com.example.uitstark.dailys_notes.DTO.User;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ToDoDAL extends DatabaseHandler {
    public ToDoDAL(Context context) {
        super(context);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String strDropTableUser = String.format("DROP TABLE IF EXISTS %s", TABLE_TODO_NAME);
        db.execSQL(strDropTableUser);
    }
    public void addToDo(ToDo toDo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TODO__IDUSER,toDo.getId_user());
        values.put(KEY_TODO__TITLE,toDo.getTitle());
        values.put(KEY_TODO__CONTENT, toDo.getContent());
        values.put(KEY_TODO_TIME,toDo.getTime());
        values.put(KEY_TODO_STATUS,0);
        values.put(KEY_TODO_COLOR,toDo.getColor());

        //if value is empty -> don't insert -> null
        db.insert(TABLE_TODO_NAME, null, values);
        db.close();
    }

    public ToDo getToDo(int todoID) throws ParseException {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TODO_NAME, null, KEY_TODO__ID + " = ?", new String[] { String.valueOf(todoID) },null, null, null);
        ToDo toDo =null;
        if(cursor != null) {
            cursor.moveToFirst();
            int idToDo=cursor.getInt(0);
            int idUserToDo=cursor.getInt(1);
            String title=cursor.getString(2);
            String content= cursor.getString(3);
            String time= cursor.getString(4);
            int status=cursor.getInt(5);
            int color=cursor.getInt(6);
            toDo= new ToDo(idToDo,idUserToDo,title,content,time,status,color);
        }

        db.close();
        return toDo;
    }

    public List<ToDo> getToDoOf(int IdUser) throws ParseException {

        List<ToDo>  toDoList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TODO_NAME, null,  KEY_TODO__IDUSER + " = ?" , new String[] { String.valueOf(IdUser) },null, null, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            int idToDo=cursor.getInt(0);
            int idUserToDo=cursor.getInt(1);
            String title=cursor.getString(2);
            String content= cursor.getString(3);
            String time= cursor.getString(4);
            int status=cursor.getInt(5);
            int color=cursor.getInt(6);
            ToDo toDo= new ToDo(idToDo,idUserToDo,title,content,time,status,color);
            toDoList.add(toDo);
            cursor.moveToNext();
        }

        db.close();
        return toDoList;
    }
    public List<ToDo> getToDoDoneOf(int IdUser) throws ParseException {

        List<ToDo>  toDoList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TODO_NAME, null, KEY_TODO_STATUS+" = 1 AND "+ KEY_TODO__IDUSER + " = ?" , new String[] { String.valueOf(IdUser) },null, null, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            int idToDo=cursor.getInt(0);
            int idUserToDo=cursor.getInt(1);
            String title=cursor.getString(2);
            String content= cursor.getString(3);
            String time= cursor.getString(4);
            int status=cursor.getInt(5);
            int color=cursor.getInt(6);
            ToDo toDo= new ToDo(idToDo,idUserToDo,title,content,time,status,color);
            toDoList.add(toDo);
            cursor.moveToNext();
        }

        db.close();
        return toDoList;
    }
    public List<ToDo> getAllToDo() throws ParseException {
        List<ToDo>  toDoList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_TODO_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            int idToDo=cursor.getInt(0);
            int idUserToDo=cursor.getInt(1);
            String title=cursor.getString(2);
            String content= cursor.getString(3);
            String time= cursor.getString(4);
            int status=cursor.getInt(5);
            int color=cursor.getInt(6);
            ToDo toDo= new ToDo(idToDo,idUserToDo,title,content,time,status,color);
            toDoList.add(toDo);
            cursor.moveToNext();
        }

        db.close();
        return toDoList;
    }


    public void updateToDo(int idToDo, int idUser,ToDo toDo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TODO__IDUSER, idUser);
        values.put(KEY_TODO__TITLE, toDo.getTitle());
        values.put(KEY_TODO__CONTENT, toDo.getContent());
        values.put(KEY_TODO_TIME, toDo.getTime());
        db.update(TABLE_TODO_NAME, values, KEY_TODO__ID + " = ?", new String[] { String.valueOf(idToDo) });
        db.close();
    }

    public void updateStatusToDo(int idToDo, int idUser) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TODO__IDUSER, idUser);
        values.put(KEY_TODO_STATUS,1);
        ;
        db.update(TABLE_TODO_NAME, values, KEY_TODO__ID + " = ?", new String[] { String.valueOf(idToDo) });
        db.close();
    }

    public void deleteToDo(int toDoId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TODO_NAME, KEY_TODO__ID + " = ?", new String[] { String.valueOf(toDoId) });
        db.close();
    }
}
