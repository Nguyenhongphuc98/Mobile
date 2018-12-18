package com.example.uitstark.dailys_notes.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uitstark.dailys_notes.R;

public class Menu extends Activity {

    TextView editTextbirthday;
    TextView editLogOut;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        editTextbirthday= (TextView) findViewById(R.id.tvmenuremindbirthday);
        editLogOut=(TextView) findViewById(R.id.tvlogout);


        editLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"logout",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
            }
        });

        editTextbirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ListBirthdayActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onMyClick(View v) {
        Intent intent = new Intent(getApplicationContext(),ListBirthdayActivity.class);
        startActivity(intent);
    }

}
