package com.example.uitstark.dailys_notes.Activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.uitstark.dailys_notes.DTO.BirthDay;
import com.example.uitstark.dailys_notes.DTO.Global;
import com.example.uitstark.dailys_notes.DatabaseManage.BirthdayDAL;
import com.example.uitstark.dailys_notes.DatabaseManage.NoteDAL;
import com.example.uitstark.dailys_notes.R;
import com.example.uitstark.dailys_notes.DTO.Note;
import com.example.uitstark.dailys_notes.ServiceManage.AlarmReceiver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NoteActivity extends Activity implements View.OnClickListener{
    Button btnSaveNote;
    TextView textViewTitle;
    EditText editTextTitle;
    EditText editTextContent;

    NoteDAL noteDAL;
    PendingIntent pendingIntent;
    Calendar   calendar=Calendar.getInstance();

    String currentUser;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        Bundle bundle = getIntent().getExtras();
        currentUser=bundle.getString("idCurrentUser");

        LinkView();
        SetAction();
    }

    void LinkView(){
        btnSaveNote =findViewById(R.id.btnSaveNote);

        editTextTitle=findViewById(R.id.edt_titleNote);
        editTextContent=findViewById(R.id.edt_contentNote);
    }

    void SetAction(){
        btnSaveNote.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v==btnSaveNote){
            noteDAL = new NoteDAL(this);
            String title= String.valueOf(editTextTitle.getText());
            String content= String.valueOf(editTextContent.getText());
            Note note = new Note(Integer.parseInt(currentUser),title,content);
            noteDAL.addNote(note);
//            //notifi save and come back- update listview birthday
//            setResult(ListBirthdayActivity.ADDBIRTHDAYRESULT);
//            finish();
        }
    }
}
