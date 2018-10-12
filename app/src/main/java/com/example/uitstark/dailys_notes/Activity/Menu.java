package com.example.uitstark.dailys_notes.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.uitstark.dailys_notes.R;

public class Menu extends Activity {

    TextView editTextbirthday;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        editTextbirthday= (TextView) findViewById(R.id.tvmenuremindbirthday);
        editTextbirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Menu.this,ListBirthdayActivity.class);
                startActivity(intent);
            }
        });
    }


}
