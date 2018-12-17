package com.example.uitstark.dailys_notes.Activity;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uitstark.dailys_notes.DTO.User;
import com.example.uitstark.dailys_notes.DatabaseManage.UserDAL;
import com.example.uitstark.dailys_notes.R;

public class Register  extends AppCompatActivity {
    private Button btnRegister;
    private EditText edtFullName;
    private EditText edtUserName;
    private EditText edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegister= findViewById(R.id.btnRegister);

        edtFullName= findViewById(R.id.fullNameRegister);
        edtUserName= findViewById(R.id.usernameRegister);
        edtPassword= findViewById(R.id.passwordRegister);

        final UserDAL dbUser = new UserDAL(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!emptyValidation()) {
                    dbUser.addUser(new User(edtUserName.getText().toString(), edtPassword.getText().toString(),edtFullName.getText().toString()));
                    Toast.makeText(Register.this, "Added User", Toast.LENGTH_SHORT).show();
                    edtUserName.setText("");
                    edtPassword.setText("");
                    Intent intent = new Intent(Register.this,Login.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(Register.this, "Empty Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private boolean emptyValidation() {
        if (TextUtils.isEmpty(edtUserName.getText().toString()) || TextUtils.isEmpty(edtPassword.getText().toString())) {
            return true;
        }else {
            return false;
        }
    }

}
