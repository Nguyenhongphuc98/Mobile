package com.example.uitstark.dailys_notes.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.example.uitstark.dailys_notes.DTO.BirthDay;
import com.example.uitstark.dailys_notes.DatabaseManage.BirthdayDAL;
import com.example.uitstark.dailys_notes.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ListBirthdayActivity extends Activity  implements View.OnClickListener, View.OnCreateContextMenuListener {
    Button buttonAddBirthday;
    ListView listViewBirthday;
    List<BirthDay> listBirthday;
    BirthdayDAL birthDayDAL;
    public static final int ADDBIRTHDAYCODE =0;
    public static final int ADDBIRTHDAYRESULT =1;

    BirthdayAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_birthday);

        LinkView();

        listBirthday=new ArrayList<>();
        birthDayDAL =new BirthdayDAL(ListBirthdayActivity.this);

        try {
            LoadDataFromDatabase();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        registerForContextMenu(listViewBirthday);
    }

    void LinkView(){
        buttonAddBirthday =findViewById(R.id.btnAddBirthday);
        buttonAddBirthday.setOnClickListener(this);
        listViewBirthday=findViewById(R.id.lvBirthday);
    }

    private void LoadDataFromDatabase() throws ParseException {
        listBirthday= birthDayDAL.getAllBirthday();
        adapter=new BirthdayAdapter(this,R.layout.customrow_listview_birthday,listBirthday);
        adapter.notifyDataSetChanged();
        listViewBirthday.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ADDBIRTHDAYCODE && resultCode==ADDBIRTHDAYRESULT){
            try {
                LoadDataFromDatabase();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Toast.makeText(getApplicationContext(),"ok da nhan duoc thong tin",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAddBirthday:
                Intent intent=new Intent(getApplicationContext(),NewBirthdayActivity.class);
                startActivityForResult(intent,ADDBIRTHDAYCODE);
                break;
    }
}

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contex_menu_birthday, menu);
    }

    public boolean onContextItemSelected(MenuItem item) {
        //find out which menu item was pressed
        BirthdayDAL birthdayDAL=new BirthdayDAL(ListBirthdayActivity.this);

        switch (item.getItemId()) {
            case R.id.menuXoaBirthday:
                AdapterView.AdapterContextMenuInfo menuInfo= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                BirthDay birthDay=null;
                birthDay=listBirthday.get(menuInfo.position);

                birthdayDAL.deleteBirthday(birthDay.getId());
                listBirthday.remove(birthDay);
                adapter.notifyDataSetChanged();
                return true;
            case R.id.menuSuaBirthday:
             //   BirthDay birthDay=new BirthDay();
             //   birthdayDAL.updateBirthday();
                return true;
            default:
                return false;
        }
    }
}
