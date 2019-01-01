package com.example.uitstark.dailys_notes.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class ListNoteActivity extends Activity  implements View.OnClickListener, View.OnCreateContextMenuListener {

    Button btnAddNote;
    ListView listViewNote;
    List<Note> listNote;
    NoteDAL noteDAL;

    public static final int ADDNOTECODE =0;
    public static final int ADDNOTERESULT =1;

    private String currenUser;

    NoteAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_note);

        LinkView();

        listNote=new ArrayList<>();
        noteDAL =new NoteDAL(ListNoteActivity.this);

        Bundle bundle = getIntent().getExtras();
        currenUser=bundle.getString("idCurrentUser");

        Log.e("ERRRR","in list note");
        try {
            LoadDataFromDatabase(currenUser);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        registerForContextMenu(listViewNote);
        // Toast.makeText(getApplicationContext(),"On list birthday",Toast.LENGTH_SHORT).show();

    }

    void LinkView(){
        btnAddNote =findViewById(R.id.btnAddNote);
        btnAddNote.setOnClickListener(this);
        listViewNote=findViewById(R.id.lvNote);
    }

    private void LoadDataFromDatabase(String idCurrentUser) throws ParseException {

        Toast.makeText(getApplicationContext(),idCurrentUser,Toast.LENGTH_SHORT).show();

        int id=Integer.parseInt(idCurrentUser);
        if(id==0)
            Toast.makeText(getApplicationContext(),"oooooo",Toast.LENGTH_SHORT).show();

        listNote= noteDAL.getNoteOf(id);
        //listBirthday= birthDayDAL.getBirthdayOf(0);
        Log.e("ERRRR","loaded list birthday from database");
        adapter=new NoteAdapter(this,R.layout.customrow_listview_note,listNote);
        adapter.notifyDataSetChanged();
        listViewNote.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ADDNOTECODE && resultCode==ADDNOTERESULT){
            try {
                LoadDataFromDatabase(currenUser);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Toast.makeText(getApplicationContext(),"ok da nhan duoc thong tin",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAddNote:

                Bundle mBundle = new Bundle();
                mBundle.putString("idCurrentUser",currenUser);

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
        //find out which menu item was pressed
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
            case R.id.menuSuaNote:
                //   BirthDay birthDay=new BirthDay();
                //   birthdayDAL.updateBirthday();
                return true;
            default:
                return false;
        }
    }
}
