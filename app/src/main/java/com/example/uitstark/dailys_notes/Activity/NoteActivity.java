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
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.uitstark.dailys_notes.DatabaseManage.NoteDAL;
import com.example.uitstark.dailys_notes.R;
import com.example.uitstark.dailys_notes.DTO.Note;
import java.util.Calendar;

public class NoteActivity extends AppCompatActivity implements View.OnClickListener{
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
            //notifi save and come back- update listview birthday
            setResult(ListNoteActivity.ADDNOTERESULT);
            finish();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:break;
        }

        return super.onOptionsItemSelected(item);
    }
}
