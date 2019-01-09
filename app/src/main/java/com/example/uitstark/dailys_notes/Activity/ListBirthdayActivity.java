package com.example.uitstark.dailys_notes.Activity;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.uitstark.dailys_notes.Adapter.BirthdayAdapter;
import com.example.uitstark.dailys_notes.DTO.BirthDay;
import com.example.uitstark.dailys_notes.DatabaseManage.BirthdayDAL;
import com.example.uitstark.dailys_notes.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ListBirthdayActivity extends Activity implements View.OnClickListener, View.OnCreateContextMenuListener {

    Button buttonAddBirthday;
    // ListView listViewBirthday;
    SwipeMenuListView listViewBirthday;
    TextView textViewNumberOfFriends;


    List<BirthDay> listBirthday;
    BirthdayDAL birthDayDAL;

    public static final int ADDBIRTHDAYCODE =0;
    public static final int ADDBIRTHDAYRESULT =1;

    public static final int EDITBIRTHDAYCODE =2;
    public static final int EDITBIRTHDAYRESULT =3;

    private String currenUser;

    BirthdayAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_birthday);

        LinkView();



        listBirthday=new ArrayList<>();
        birthDayDAL =new BirthdayDAL(ListBirthdayActivity.this);



        //registerForContextMenu(listViewBirthday);
        // Toast.makeText(getApplicationContext(),"On list birthday",Toast.LENGTH_SHORT).show();


        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem editItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                editItem.setBackground(new ColorDrawable(Color.rgb(0x00, 153,
                        255)));
                // set item width
                editItem.setWidth(170);
                // set item title
                // openItem.setTitle("Edit");
                //icon
                editItem.setIcon(R.drawable.edit);
                // set item title fontsize
                //  openItem.setTitleSize(18);
                // set item title font color
                //openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(editItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(170);
                // set a icon
                deleteItem.setIcon(R.drawable.delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };


        listViewBirthday.setMenuCreator(creator);

        listViewBirthday.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                BirthdayDAL birthdayDAL=new BirthdayDAL(ListBirthdayActivity.this);

                BirthDay birthDay=null;
                birthDay=listBirthday.get(position);

                switch (index) {
                    case 0:

                        Bundle bundle=new Bundle();
                        bundle.putString("idBirthDay",String.valueOf(birthDay.getId()));

                        Intent intent=new Intent(getApplicationContext(),EditBirthdayActivity.class);
                        intent.putExtras(bundle);

                        startActivityForResult(intent,EDITBIRTHDAYCODE);

                        Log.d("SWIPER", "onMenuItemClick: clicked item edit " + index);
                        break;


                    case 1:
                        Log.d("SWIPER", "onMenuItemClick: clicked item delete" + index);

//                        BirthDay birthDay=null;
//                        birthDay=listBirthday.get(position);

                        birthdayDAL.deleteBirthday(birthDay.getId());
                        listBirthday.remove(birthDay);
                        adapter.notifyDataSetChanged();
                        textViewNumberOfFriends.setText(String.valueOf(listBirthday.size()));

                        NotificationManager notifManager= (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                        notifManager.cancel(birthDay.getId());

                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });


        Bundle bundle = getIntent().getExtras();
        currenUser=bundle.getString("idCurrentUser");

        Log.e("ERRRR","in list birthday");
        try {
            LoadDataFromDatabase(currenUser);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    void LinkView(){
        buttonAddBirthday =findViewById(R.id.btnAddBirthday);
        buttonAddBirthday.setOnClickListener(this);
        //listViewBirthday=findViewById(R.id.lvBirthday);
        listViewBirthday=findViewById(R.id.LvBirthday);
        textViewNumberOfFriends=findViewById(R.id.tvNumberOfFriends);


    }

    private void LoadDataFromDatabase(String idCurrentUser) throws ParseException {

        Toast.makeText(getApplicationContext(),idCurrentUser,Toast.LENGTH_SHORT).show();

        int id=Integer.parseInt(idCurrentUser);
        if(id==0)
            Toast.makeText(getApplicationContext(),"oooooo",Toast.LENGTH_SHORT).show();

        listBirthday= birthDayDAL.getBirthdayOf(id);
        //listBirthday= birthDayDAL.getBirthdayOf(0);
        Log.e("ERRRR","loaded list birthday from database");
        adapter=new BirthdayAdapter(this,R.layout.customrow_listview_birthday,listBirthday);
        adapter.notifyDataSetChanged();
        listViewBirthday.setAdapter(adapter);

        textViewNumberOfFriends.setText(String.valueOf(listBirthday.size()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ADDBIRTHDAYCODE && resultCode==ADDBIRTHDAYRESULT){
            try {
                LoadDataFromDatabase(currenUser);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Toast.makeText(getApplicationContext(),"Da them 1 birthday",Toast.LENGTH_SHORT).show();
        }

        if(requestCode==EDITBIRTHDAYCODE && resultCode==EDITBIRTHDAYRESULT){
            try {
                LoadDataFromDatabase(currenUser);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Toast.makeText(getApplicationContext(),"Da sua 1 birthday",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAddBirthday:

                Bundle mBundle = new Bundle();
                mBundle.putString("idCurrentUser",currenUser);

                Intent intent=new Intent(getApplicationContext(),NewBirthdayActivity.class);
                intent.putExtras(mBundle);

                startActivityForResult(intent,ADDBIRTHDAYCODE);
                break;
        }
    }




    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contex_menu_birthday, menu);
    }

//    public boolean onContextItemSelected(MenuItem item) {
//        //find out which menu item was pressed
//        BirthdayDAL birthdayDAL=new BirthdayDAL(ListBirthdayActivity.this);
//
//        switch (item.getItemId()) {
//            case R.id.menuXoaBirthday:
//                AdapterView.AdapterContextMenuInfo menuInfo= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//                BirthDay birthDay=null;
//                birthDay=listBirthday.get(menuInfo.position);
//
//                birthdayDAL.deleteBirthday(birthDay.getId());
//                listBirthday.remove(birthDay);
//                adapter.notifyDataSetChanged();
//
//                NotificationManager notifManager= (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//                notifManager.cancel(birthDay.getId());
//
//                return true;
//
//            case R.id.menuSuaBirthday:
//             //   BirthDay birthDay=new BirthDay();
//             //   birthdayDAL.updateBirthday();
//                Intent intent = new Intent(getApplicationContext(),NewBirthdayActivity.class);
//                startActivityForResult(intent,ADDBIRTHDAYCODE);
//                return true;
//            default:
//                return false;
//        }
//    }
}
