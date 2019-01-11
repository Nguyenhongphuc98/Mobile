package com.example.uitstark.dailys_notes.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Context;
import com.example.uitstark.dailys_notes.DTO.User;
import com.example.uitstark.dailys_notes.DatabaseManage.UserDAL;
import com.example.uitstark.dailys_notes.R;

public class Login extends Activity {

    private Button btnLogin;
    private Button btnRegister;
    private EditText edtUserName;
    private EditText edtPassword;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin=findViewById(R.id.btnLogin);
        btnRegister=findViewById(R.id.btnRegister_Login);

        edtUserName= findViewById(R.id.username_login);
        edtPassword=findViewById(R.id.password_login);


        final UserDAL dbUser = new UserDAL(Login.this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!emptyValidation()) {
                    User user =dbUser.queryUser(edtUserName.getText().toString(),edtPassword.getText().toString());
                    if(user!=null) {
                        Bundle mBundle = new Bundle();
                        mBundle.putString("user",user.getFullName());
                        mBundle.putString("id",String.valueOf(user.getId()));

                        Intent intent = new Intent(getApplicationContext(),UserActivity.class);
                        intent.putExtras(mBundle);
                        startActivity(intent);
                        //Toast.makeText(getApplicationContext(),"Welcome " + user.getEmail(),Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_SHORT).show();

                        edtUserName.setText("");
                        edtPassword.setText("");
                    }
                } else{
                    Toast.makeText(getApplicationContext(), "Empty Fields", Toast.LENGTH_SHORT).show();

                }
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),Register.class);
                startActivity(intent);
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
