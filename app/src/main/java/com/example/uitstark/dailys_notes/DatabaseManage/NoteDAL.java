package com.example.uitstark.dailys_notes.DatabaseManage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.uitstark.dailys_notes.DTO.BirthDay;
import com.example.uitstark.dailys_notes.DTO.Note;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class NoteDAL extends DatabaseHandler {
    public NoteDAL(Context context) {
        super(context);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String strDropTableUser = String.format("DROP TABLE IF EXISTS %s", TABLE_NOTE_NAME);
        db.execSQL(strDropTableUser);
    }

    public void addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOTE__IDUSER, note.getId_user());
        // Log.e("IDUSER1",String.valueOf(birthDay.getId_user()));
        values.put(KEY_NOTE__TITLE,note.getTitle());
        values.put(KEY_NOTE__CONTENT,note.getContent());
        values.put(KEY_NOTE_COLOR,note.getColor());


        //if value is empty -> don't insert -> null
        db.insert(TABLE_NOTE_NAME, null, values);
        db.close();

        Log.d("TAGGG","save success");
    }

    public Note getNote(int noteID) throws ParseException {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NOTE_NAME, null, KEY_NOTE__ID + " = ?", new String[] { String.valueOf(noteID) },null, null, null);
        Note note =null;
        if(cursor != null) {
            cursor.moveToFirst();
            int idNote=cursor.getInt(0);
            int idUserNote=cursor.getInt(1);
            String title=cursor.getString(2);
            String content= cursor.getString(3);
            int color=cursor.getInt(4);


            note= new Note(idNote,idUserNote,title,content,color);
        }

        db.close();
        return note;
    }

    public List<Note> getNoteOf(int IdUser) throws ParseException {

        List<Note>  noteList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Log.e("IDUSER",String.valueOf(IdUser));

        //Cursor cursor = db.query(TABLE_BIRTHDAY_NAME, null, KEY_USER__ID + " = ?", new String[] { String.valueOf(IdUser) },null, null, null);
        Cursor cursor = db.query(TABLE_NOTE_NAME, null, KEY_NOTE__IDUSER + " = ?", new String[] { String.valueOf(IdUser) },null, null, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            int idNote=cursor.getInt(0);
            int idUserNote=cursor.getInt(1);
            String title=cursor.getString(2);
            String content= cursor.getString(3);
            int color=cursor.getInt(4);

            Note note = new Note(idNote,idUserNote,title,content,color);
            noteList.add(note);
            cursor.moveToNext();
        }

        db.close();
        return noteList;
    }

    public List<Note> getAllNote() throws ParseException {
        // Log.e("ERRRR","1");
        List<Note>  noteList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NOTE_NAME;

        // Log.e("ERRRR","2");
        SQLiteDatabase db = this.getReadableDatabase();
        // Log.e("ERRRR","3");
        Cursor cursor = db.rawQuery(query, null);
        //  Log.e("ERRRR","4");
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            int idNote=cursor.getInt(0);
            int idUserNote=cursor.getInt(1);
            String title=cursor.getString(2);
            String content= cursor.getString(3);
            int color=cursor.getInt(4);
            Note note = new Note(idNote,idUserNote,title,content,color);
            noteList.add(note);
            cursor.moveToNext();
        }

        db.close();
        return noteList;
    }


    public void updateNote(int idNote, int idUser,Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NOTE__IDUSER, idUser);
        values.put(KEY_NOTE__TITLE, note.getTitle());
        values.put(KEY_NOTE__CONTENT, note.getContent());
        values.put(KEY_NOTE_COLOR,note.getColor());
        db.update(TABLE_NOTE_NAME, values, KEY_NOTE__ID + " = ?", new String[] { String.valueOf(idNote) });
        db.close();
    }

    public void deleteNote(int noteID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTE_NAME, KEY_NOTE__ID + " = ?", new String[] { String.valueOf(noteID) });
        db.close();
    }
}
