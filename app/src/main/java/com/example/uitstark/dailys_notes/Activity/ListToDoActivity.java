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
import com.example.uitstark.dailys_notes.Adapter.ToDoAdapter;
import com.example.uitstark.dailys_notes.DTO.BirthDay;
import com.example.uitstark.dailys_notes.DTO.Note;
import com.example.uitstark.dailys_notes.DTO.ToDo;
import com.example.uitstark.dailys_notes.DatabaseManage.BirthdayDAL;
import com.example.uitstark.dailys_notes.DatabaseManage.ToDoDAL;
import com.example.uitstark.dailys_notes.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ListToDoActivity extends AppCompatActivity implements View.OnClickListener, View.OnCreateContextMenuListener {

    Button btnAddToDo;
    ListView listViewToDo;
    TextView tvDone;
    List<ToDo> listToDo;
    List<ToDo> listToDoDone;
    ToDoDAL toDoDAL;

    public static final int ADDTODOCODE = 0;
    public static final int ADDTODORESULT = 1;

    private String currentUser;
    private String currentToDo;

    ToDoAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_todo);

        LinkView();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listToDo = new ArrayList<>();
        listToDoDone = new ArrayList<>();

        toDoDAL = new ToDoDAL(ListToDoActivity.this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Bundle bundle = getIntent().getExtras();
        currentUser = bundle.getString("idCurrentUser");
        try {
            LoadDataFromDatabase(currentUser);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        registerForContextMenu(listViewToDo);
        listViewToDo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ToDo toDoUpdate = null;
                toDoUpdate = listToDo.get(i);

                currentToDo = String.valueOf(toDoUpdate.getId());
                Bundle mBundle = new Bundle();
                mBundle.putString("idCurrentToDo", currentToDo);
                mBundle.putString("idCurrentUser", currentUser);
                Intent intent = new Intent(getApplicationContext(), UpdateToDoActivity.class);
                intent.putExtras(mBundle);

                startActivityForResult(intent, ADDTODOCODE);
            }
        });

        tvDone.setText(String.valueOf(listToDoDone.size()));
    }

    void LinkView() {
        btnAddToDo = findViewById(R.id.btnAddToDo);
        btnAddToDo.setOnClickListener(this);
        listViewToDo = findViewById(R.id.lvToDo);
        tvDone = findViewById(R.id.tv_doneToDoList);
    }

    private void LoadDataFromDatabase(String idCurrentUser) throws ParseException {
        int id = Integer.parseInt(idCurrentUser);
        listToDo = toDoDAL.getToDoOf(id);
        listToDoDone=toDoDAL.getToDoDoneOf(id);
        adapter = new ToDoAdapter(this, R.layout.customrow_listview_todo, listToDo);
        adapter.notifyDataSetChanged();
        listViewToDo.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADDTODOCODE && resultCode == ADDTODORESULT) {
            try {
                LoadDataFromDatabase(currentUser);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddToDo:

                Bundle mBundle = new Bundle();
                mBundle.putString("idCurrentUser", currentUser);
                Intent intent = new Intent(getApplicationContext(), ToDoActivity.class);
                intent.putExtras(mBundle);
                startActivityForResult(intent, ADDTODOCODE);
                break;
        }
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu_todo, menu);
    }

    public boolean onContextItemSelected(MenuItem item) {
        ToDoDAL toDoDAL = new ToDoDAL(ListToDoActivity.this);

        switch (item.getItemId()) {
            case R.id.menuDoneToDo:
                AdapterView.AdapterContextMenuInfo menuInfoDone = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                ToDo toDoDone = null;
                toDoDone = listToDo.get(menuInfoDone.position);
                toDoDAL.updateStatusToDo(toDoDone.getId(), Integer.parseInt(currentUser));
                adapter.notifyDataSetChanged();
                try {
                    LoadDataFromDatabase(currentUser);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                tvDone.setText(String.valueOf(listToDoDone.size()));
                return true;
            case R.id.menuXoaToDo:
                AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                ToDo toDo = null;
                toDo = listToDo.get(menuInfo.position);

                toDoDAL.deleteToDo(toDo.getId());
                listToDo.remove(toDo);
                listToDoDone.remove(toDo);
                adapter.notifyDataSetChanged();
                try {
                    LoadDataFromDatabase(currentUser);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                tvDone.setText(String.valueOf(listToDoDone.size()));
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
