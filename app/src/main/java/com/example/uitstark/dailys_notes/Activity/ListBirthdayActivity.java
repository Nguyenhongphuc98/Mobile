package com.example.uitstark.dailys_notes.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.uitstark.dailys_notes.Adapter.BirthdayAdapter;
import com.example.uitstark.dailys_notes.DTO.BirthDay;
import com.example.uitstark.dailys_notes.DatabaseManage.BirthdayDAL;
import com.example.uitstark.dailys_notes.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ListBirthdayActivity extends Activity  implements View.OnClickListener{
    Button buttonAddBirthday;
    ListView listViewBirthday;
    List<BirthDay> listBirthday;
    BirthdayDAL birthDayDAL;
    public static final int ADDBIRTHDAYCODE =0;
    public static final int ADDBIRTHDAYRESULT =1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_birthday);

        buttonAddBirthday =findViewById(R.id.btnAddBirthday);
        buttonAddBirthday.setOnClickListener(this);
        listViewBirthday=findViewById(R.id.lvBirthday);

        listBirthday=new ArrayList<>();
        birthDayDAL =new BirthdayDAL(ListBirthdayActivity.this);

        try {
            LoadDataFromDatabase();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void LoadDataFromDatabase() throws ParseException {
        listBirthday= birthDayDAL.getAllBirthday();
        BirthdayAdapter adapter=new BirthdayAdapter(this,R.layout.customrow_listview_birthday,listBirthday);
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
            Toast.makeText(ListBirthdayActivity.this,"ok da nhan duoc thong tin",Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAddBirthday:
                Intent intent=new Intent(ListBirthdayActivity.this,NewBirthdayActivity.class);
                startActivityForResult(intent,ADDBIRTHDAYCODE);
                break;
    }
}
}
