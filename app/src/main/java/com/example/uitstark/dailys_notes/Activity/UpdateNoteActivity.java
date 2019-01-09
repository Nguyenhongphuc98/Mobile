package com.example.uitstark.dailys_notes.Activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uitstark.dailys_notes.Adapter.NoteAdapter;
import com.example.uitstark.dailys_notes.DTO.Note;
import com.example.uitstark.dailys_notes.DatabaseManage.NoteDAL;
import com.example.uitstark.dailys_notes.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

public class UpdateNoteActivity  extends AppCompatActivity implements View.OnClickListener {
    Button btnUpdateNote;
    TextView textViewTitle;
    EditText editTextUpdateTitle;
    EditText editTextUpdateContent;

    NoteDAL noteDAL;
    Note note;

    String currentUser;
    private String currentNote;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatenote);

        Bundle bundle = getIntent().getExtras();
        currentUser=bundle.getString("idCurrentUser");
        currentNote= bundle.getString("idCurrentNote");

        LinkView();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SetAction();

        noteDAL =new NoteDAL(UpdateNoteActivity.this);
        try {
            LoadNoteDataFromDatabase(currentNote);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    void LinkView(){
        btnUpdateNote =findViewById(R.id.btnUpdateNote);

        editTextUpdateTitle=findViewById(R.id.edt_titleUpdateNote);
        editTextUpdateContent=findViewById(R.id.edt_contentUpdateNote);
    }
    private void LoadNoteDataFromDatabase(String idCurrentNote) throws ParseException {

        Toast.makeText(getApplicationContext(),idCurrentNote,Toast.LENGTH_SHORT).show();

        int id=Integer.parseInt(idCurrentNote);
        if(id==0)
            Toast.makeText(getApplicationContext(),"oooooo",Toast.LENGTH_SHORT).show();

        note= noteDAL.getNote(id);
        editTextUpdateTitle.setText(note.getTitle());
        editTextUpdateContent.setText(note.getContent());

    }

    void SetAction(){
        btnUpdateNote.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v==btnUpdateNote){
            noteDAL = new NoteDAL(this);
            String title= String.valueOf(editTextUpdateTitle.getText());
            String content= String.valueOf(editTextUpdateContent.getText());
            Note note = new Note(Integer.parseInt(currentUser),title,content);
            noteDAL.updateNote(Integer.parseInt(currentNote),Integer.parseInt(currentUser),note);
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
