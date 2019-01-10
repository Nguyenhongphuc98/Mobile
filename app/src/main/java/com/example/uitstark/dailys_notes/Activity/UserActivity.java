package com.example.uitstark.dailys_notes.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uitstark.dailys_notes.R;


public class UserActivity extends AppCompatActivity {
    private TextView tvUserNameMenu;
    private TextView tvRemindBirthday;
    private TextView tvDiary;
    private TextView tvToDo;
    private TextView tvSettingAccount;
    private TextView tvLogOut;

    private String currenUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        tvUserNameMenu = findViewById(R.id.username_menu);
        tvDiary = findViewById(R.id.tvDiary);
        tvToDo = findViewById(R.id.tvToDoList);
        tvSettingAccount = findViewById(R.id.tvSettingAccount);
        tvRemindBirthday = findViewById(R.id.tvmenuremindbirthday);
        tvLogOut = findViewById(R.id.tvlogout);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            tvUserNameMenu.setText(bundle.getString("user"));
            currenUser = bundle.getString("id");
        }

        tvRemindBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle mBundle = new Bundle();
                mBundle.putString("idCurrentUser", currenUser);

                Intent intent = new Intent(getApplicationContext(), ListBirthdayActivity.class);

                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });

        tvDiary.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Bundle mBundle = new Bundle();
                mBundle.putString("idCurrentUser", currenUser);
                Intent intent = new Intent(getApplicationContext(), ListNoteActivity.class);
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });

        tvToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle mBundle = new Bundle();
                mBundle.putString("idCurrentUser", currenUser);
                Intent intent = new Intent(getApplicationContext(), ListToDoActivity.class);
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });

        tvSettingAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle mBundle = new Bundle();
                mBundle.putString("idCurrentUser", currenUser);
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });
        tvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });


    }

}
