package com.example.uitstark.dailys_notes.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.example.uitstark.dailys_notes.R;



public class UserActivity extends AppCompatActivity {
    private TextView tvUserNameMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        tvUserNameMenu=findViewById(R.id.username_menu);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            tvUserNameMenu.setText(bundle.getString("user"));
        }
    }

}
