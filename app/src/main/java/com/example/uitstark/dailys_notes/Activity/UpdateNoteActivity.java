package com.example.uitstark.dailys_notes.Activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
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

import petrov.kristiyan.colorpicker.ColorPicker;

public class UpdateNoteActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnUpdateNote;
    Button btnUpdateColorPicker;
    Button btnBackNoteUpdate;
    EditText editTextUpdateTitle;
    EditText editTextUpdateContent;
    public int CODE_NOTE_COLOR = Color.CYAN;

    NoteDAL noteDAL;
    Note note;

    String currentUser;
    private String currentNote;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatenote);

        Bundle bundle = getIntent().getExtras();
        currentUser = bundle.getString("idCurrentUser");
        currentNote = bundle.getString("idCurrentNote");

        LinkView();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SetAction();

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        noteDAL = new NoteDAL(UpdateNoteActivity.this);
        try {
            LoadNoteDataFromDatabase(currentNote);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    void LinkView() {
        btnUpdateNote = findViewById(R.id.btnUpdateNote);
        btnUpdateColorPicker = findViewById(R.id.btnColorPickerUpdate);
        btnBackNoteUpdate=findViewById(R.id.btnBackNoteUpdate);
        editTextUpdateTitle = findViewById(R.id.edt_titleUpdateNote);
        editTextUpdateContent = findViewById(R.id.edt_contentUpdateNote);
    }

    private void LoadNoteDataFromDatabase(String idCurrentNote) throws ParseException {
        Toast.makeText(getApplicationContext(), idCurrentNote, Toast.LENGTH_SHORT).show();
        int id = Integer.parseInt(idCurrentNote);
        note = noteDAL.getNote(id);
        editTextUpdateTitle.setText(note.getTitle());
        editTextUpdateTitle.setTextColor(note.getColor());
        editTextUpdateContent.setText(note.getContent());
        editTextUpdateContent.setTextColor(note.getColor());

    }

    void SetAction() {
        btnUpdateNote.setOnClickListener(this);
        btnUpdateColorPicker.setOnClickListener(this);
        btnBackNoteUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == btnUpdateNote) {
            noteDAL = new NoteDAL(this);
            String title = String.valueOf(editTextUpdateTitle.getText());
            String content = String.valueOf(editTextUpdateContent.getText());
            Note note = new Note(Integer.parseInt(currentUser), title, content, CODE_NOTE_COLOR);
            noteDAL.updateNote(Integer.parseInt(currentNote), Integer.parseInt(currentUser), note);
            setResult(ListNoteActivity.ADDNOTERESULT);
            finish();

        }else if(v==btnUpdateColorPicker) {
            ColorPicker colorPicker = new ColorPicker(UpdateNoteActivity.this);
            colorPicker.show();
            colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                @Override
                public void onChooseColor(int position, int color) {
                    editTextUpdateContent.setTextColor(color);
                    editTextUpdateTitle.setTextColor(color);
                    CODE_NOTE_COLOR = color;
                }

                @Override
                public void onCancel() {

                }
            });
        } else {
            setResult(ListNoteActivity.ADDNOTERESULT);
            finish();
        }
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                onBackPressed();
//                return true;
//
//            default:
//                break;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
