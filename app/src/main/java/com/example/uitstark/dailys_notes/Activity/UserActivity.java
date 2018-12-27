package com.example.uitstark.dailys_notes.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uitstark.dailys_notes.R;



public class UserActivity extends AppCompatActivity {
    private TextView tvUserNameMenu;
    private TextView tvRemindBirthday ;
    private TextView tvLogOut;

    private String currenUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        tvUserNameMenu=findViewById(R.id.username_menu);

        tvRemindBirthday=findViewById(R.id.tvmenuremindbirthday);
        tvLogOut=findViewById(R.id.tvlogout);


        Bundle bundle = getIntent().getExtras();

        if(bundle!=null) {
            tvUserNameMenu.setText(bundle.getString("user"));
           // Toast.makeText(getApplicationContext(),bundle.getString("id"),Toast.LENGTH_SHORT).show();
            currenUser=bundle.getString("id");
        }

        tvRemindBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("ERRRR","in user");

                Bundle mBundle = new Bundle();
                mBundle.putString("idCurrentUser",currenUser);

                Intent intent = new Intent(getApplicationContext(),ListBirthdayActivity.class);

                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });

        tvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
            }
        });


    }

}
