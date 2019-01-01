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
import com.example.uitstark.dailys_notes.Adapter.ToDoAdapter;
import com.example.uitstark.dailys_notes.DTO.BirthDay;
import com.example.uitstark.dailys_notes.DTO.ToDo;
import com.example.uitstark.dailys_notes.DatabaseManage.BirthdayDAL;
import com.example.uitstark.dailys_notes.DatabaseManage.ToDoDAL;
import com.example.uitstark.dailys_notes.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ListToDoActivity extends Activity  implements View.OnClickListener, View.OnCreateContextMenuListener {

    Button btnAddToDo;
    ListView listViewToDo;
    List<ToDo> listToDo;
    ToDoDAL toDoDAL;

    public static final int ADDTODOCODE =0;
    public static final int ADDTODORESULT =1;

    private String currenUser;

    ToDoAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_todo);

        LinkView();

        listToDo=new ArrayList<>();
        toDoDAL =new ToDoDAL(ListToDoActivity.this);

        Bundle bundle = getIntent().getExtras();
        currenUser=bundle.getString("idCurrentUser");
        try {
            LoadDataFromDatabase(currenUser);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        registerForContextMenu(listViewToDo);
        // Toast.makeText(getApplicationContext(),"On list birthday",Toast.LENGTH_SHORT).show();

    }

    void LinkView(){
        btnAddToDo =findViewById(R.id.btnAddToDo);
        btnAddToDo.setOnClickListener(this);
        listViewToDo=findViewById(R.id.lvToDo);
    }

    private void LoadDataFromDatabase(String idCurrentUser) throws ParseException {

        Toast.makeText(getApplicationContext(),idCurrentUser,Toast.LENGTH_SHORT).show();

        int id=Integer.parseInt(idCurrentUser);
        if(id==0)
            Toast.makeText(getApplicationContext(),"oooooo",Toast.LENGTH_SHORT).show();

        listToDo= toDoDAL.getToDoOf(id);
        adapter=new ToDoAdapter(this,R.layout.customrow_listview_todo,listToDo);
        adapter.notifyDataSetChanged();
        listViewToDo.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ADDTODOCODE && resultCode==ADDTODORESULT){
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
            case R.id.btnAddToDo:

                Bundle mBundle = new Bundle();
                mBundle.putString("idCurrentUser",currenUser);
                Intent intent=new Intent(getApplicationContext(),ToDoActivity.class);
                intent.putExtras(mBundle);
                startActivityForResult(intent,ADDTODOCODE);
                break;
        }
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu_todo, menu);
    }

    public boolean onContextItemSelected(MenuItem item) {
        //find out which menu item was pressed
        ToDoDAL toDoDAL=new ToDoDAL(ListToDoActivity.this);

        switch (item.getItemId()) {
            case R.id.menuXoaToDo:
                AdapterView.AdapterContextMenuInfo menuInfo= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                ToDo toDo=null;
                toDo=listToDo.get(menuInfo.position);

                toDoDAL.deleteToDo(toDo.getId());
                listToDo.remove(toDo);
                adapter.notifyDataSetChanged();
                return true;
            case R.id.menuSuaToDo:
                return true;
            default:
                return false;
        }
    }
}
