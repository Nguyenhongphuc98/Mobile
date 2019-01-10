package com.example.uitstark.dailys_notes.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uitstark.dailys_notes.Adapter.BirthdayAdapter;
import com.example.uitstark.dailys_notes.Adapter.NoteAdapter;
import com.example.uitstark.dailys_notes.DTO.BirthDay;
import com.example.uitstark.dailys_notes.DTO.Note;
import com.example.uitstark.dailys_notes.DatabaseManage.BirthdayDAL;
import com.example.uitstark.dailys_notes.DatabaseManage.NoteDAL;
import com.example.uitstark.dailys_notes.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ListNoteActivity extends AppCompatActivity  implements View.OnClickListener, View.OnCreateContextMenuListener {

    Button btnAddNote;
    ListView listViewNote;
    List<Note> listNote;
    NoteDAL noteDAL;

    public static final int ADDNOTECODE =0;
    public static final int ADDNOTERESULT =1;

    private String currentUser;
    private String currentNote;

    NoteAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_note);

        LinkView();
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listNote=new ArrayList<>();
        noteDAL =new NoteDAL(ListNoteActivity.this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Bundle bundle = getIntent().getExtras();
        currentUser=bundle.getString("idCurrentUser");

        try {
            LoadDataFromDatabase(currentUser);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        registerForContextMenu(listViewNote);
        listViewNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Note noteUpdate=null;
                noteUpdate=listNote.get(i);

                currentNote= String.valueOf(noteUpdate.getId());
                Bundle mBundle = new Bundle();
                mBundle.putString("idCurrentNote",currentNote);
                mBundle.putString("idCurrentUser",currentUser);
                Intent intent=new Intent(getApplicationContext(),UpdateNoteActivity.class);
                intent.putExtras(mBundle);

                startActivityForResult(intent,ADDNOTECODE);

            }
        });
    }

    void LinkView(){
        btnAddNote =findViewById(R.id.btnAddNote);
        btnAddNote.setOnClickListener(this);
        listViewNote=findViewById(R.id.lvNote);
    }

    private void LoadDataFromDatabase(String idCurrentUser) throws ParseException {
        int id=Integer.parseInt(idCurrentUser);
        listNote= noteDAL.getNoteOf(id);
        adapter=new NoteAdapter(this,R.layout.customrow_listview_note,listNote);
        adapter.notifyDataSetChanged();
        listViewNote.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ADDNOTECODE && resultCode==ADDNOTERESULT){
            try {
                LoadDataFromDatabase(currentUser);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAddNote:

                Bundle mBundle = new Bundle();
                mBundle.putString("idCurrentUser",currentUser);

                Intent intent=new Intent(getApplicationContext(),NoteActivity.class);
                intent.putExtras(mBundle);

                startActivityForResult(intent,ADDNOTECODE);
                break;
        }
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu_note, menu);
    }


    public boolean onContextItemSelected(MenuItem item) {
        NoteDAL noteDAL=new NoteDAL(ListNoteActivity.this);

        switch (item.getItemId()) {
            case R.id.menuXoaNote:
                AdapterView.AdapterContextMenuInfo menuInfo= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                Note note=null;
                note=listNote.get(menuInfo.position);

                noteDAL.deleteNote(note.getId());
                listNote.remove(note);
                adapter.notifyDataSetChanged();
                return true;
            default:
                return false;
        }
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId())
//        {
//            case android.R.id.home:
//                onBackPressed();
//                return true;
//
//            default:break;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
