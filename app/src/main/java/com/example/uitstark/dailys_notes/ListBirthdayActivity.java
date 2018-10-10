package com.example.uitstark.dailys_notes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

public class ListBirthdayActivity extends Activity {
    Button buttonAddBirthday;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_birthday);

        buttonAddBirthday =findViewById(R.id.btnAddBirthday);
        buttonAddBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ListBirthdayActivity.this,NewBirthdayActivity.class);
                startActivity(intent);
            }
        });
    }
}
