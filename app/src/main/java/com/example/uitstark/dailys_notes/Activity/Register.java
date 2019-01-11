package com.example.uitstark.dailys_notes.Activity;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uitstark.dailys_notes.DTO.User;
import com.example.uitstark.dailys_notes.DatabaseManage.UserDAL;
import com.example.uitstark.dailys_notes.R;

public class Register extends AppCompatActivity {
    private Button btnRegister;
    private EditText edtFullName;
    private EditText edtUserName;
    private EditText edtPassword;
    private EditText getEdtPasswordConfirm;
    protected boolean checkPasswordValidation = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegister = findViewById(R.id.btnRegister);

        edtFullName = findViewById(R.id.fullNameRegister);
        edtUserName = findViewById(R.id.usernameRegister);
        edtPassword = findViewById(R.id.passwordRegister);
        getEdtPasswordConfirm = findViewById(R.id.passwordRegisterConfirm);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        final UserDAL dbUser = new UserDAL(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!emptyValidation()) {
                    if (edtPassword.getText().toString().equals(getEdtPasswordConfirm.getText().toString())) {
                        if(edtPassword.getText().toString().length()<6){
                            Toast.makeText(getApplicationContext(), "Password tối thiểu 6 kí tự", Toast.LENGTH_SHORT).show();
                        } else {
                            dbUser.addUser(new User(edtFullName.getText().toString(),edtUserName.getText().toString(), edtPassword.getText().toString() ));
                            Toast.makeText(getApplicationContext(), "Thêm người dùng thành công!", Toast.LENGTH_SHORT).show();
                            edtFullName.setText("");
                            edtUserName.setText("");
                            edtPassword.setText("");
                            getEdtPasswordConfirm.setText("");
                            Intent intent = new Intent(Register.this, Login.class);
                            startActivity(intent);
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Xin vui lòng kiểm tra lại password nhập vào", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Xin vui lòng nhập đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private boolean emptyValidation() {
        if (TextUtils.isEmpty(edtUserName.getText().toString()) || TextUtils.isEmpty(edtPassword.getText().toString()) || TextUtils.isEmpty(getEdtPasswordConfirm.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }

}
