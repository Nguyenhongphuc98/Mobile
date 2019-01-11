package com.example.uitstark.dailys_notes.Activity;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.uitstark.dailys_notes.DTO.Convert;
import com.example.uitstark.dailys_notes.DTO.ToDo;
import com.example.uitstark.dailys_notes.DTO.User;
import com.example.uitstark.dailys_notes.DatabaseManage.ToDoDAL;
import com.example.uitstark.dailys_notes.DatabaseManage.UserDAL;
import com.example.uitstark.dailys_notes.R;
import com.example.uitstark.dailys_notes.ServiceManage.AlarmToDo;

import java.text.ParseException;
import java.util.Calendar;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener, View.OnCreateContextMenuListener {
    private Button btnSaveSetting;
    private EditText edtFullNameSetting;
    private EditText edtUserNameSetting;
    private EditText edtOldPassword;
    private EditText edtPasswordSetting;
    private EditText edtPasswordConfirmSetting;
    protected boolean checkPasswordValidation = false;
    String currentUser;

    User user;
    UserDAL userDAL= new UserDAL(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Bundle bundle = getIntent().getExtras();
        currentUser = bundle.getString("idCurrentUser");

        LinkView();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SetAction();

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        try {
            LoadNoteDataFromDatabase(currentUser);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void LoadNoteDataFromDatabase(String idCurrentUser) throws ParseException {

        int id = Integer.parseInt(idCurrentUser);
        user = userDAL.getUser(id);
        edtFullNameSetting.setText(user.getFullName());
        edtUserNameSetting.setText(user.getEmail());

    }

    void LinkView() {
        btnSaveSetting = findViewById(R.id.btnSaveSetting);

        edtFullNameSetting = findViewById(R.id.edt_fullNameSetting);
        edtUserNameSetting = findViewById(R.id.edt_usernameSetting);
        edtOldPassword=findViewById(R.id.edt_passwordOldSetting);
        edtPasswordSetting = findViewById(R.id.edt_passwordSetting);
        edtPasswordConfirmSetting = findViewById(R.id.edt_passwordSettingConfirm);
    }

    void SetAction() {
        btnSaveSetting.setOnClickListener(this);
    }

    private boolean emptyValidation() {
        if (TextUtils.isEmpty(edtUserNameSetting.getText().toString()) || TextUtils.isEmpty(edtPasswordSetting.getText().toString()) || TextUtils.isEmpty(edtPasswordConfirmSetting.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnSaveSetting) {
            final UserDAL dbUser = new UserDAL(this);
            if (!emptyValidation()) {
                User user =dbUser.queryUser(edtUserNameSetting.getText().toString(),edtOldPassword.getText().toString());
                if(user!=null) {
                    if(edtOldPassword.getText().toString().equals(edtPasswordSetting.getText().toString())==false&&edtOldPassword.getText().toString().equals(edtPasswordConfirmSetting.getText().toString())==false) {
                        if (edtPasswordSetting.getText().toString().equals(edtPasswordConfirmSetting.getText().toString())) {
                            if(edtPasswordSetting.getText().toString().length()<6){
                                Toast.makeText(getApplicationContext(), "Password tối thiểu 6 kí tự", Toast.LENGTH_SHORT).show();
                            } else{
                                String fullname = edtFullNameSetting.getText().toString();
                                String username = edtUserNameSetting.getText().toString();
                                String password = edtPasswordSetting.getText().toString();
                                User nuser = new User(Integer.parseInt(currentUser),fullname, username, password);
                                dbUser.updateUser(Integer.parseInt(currentUser), nuser);
                                Toast.makeText(getApplicationContext(), "Chỉnh sửa thông tin người dùng thành công!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SettingActivity.this, UserActivity.class);
                                startActivity(intent);
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Xin vui lòng kiểm tra lại mật khẩu mới nhập vào", Toast.LENGTH_SHORT).show();
                            edtOldPassword.setText("");
                            edtPasswordSetting.setText("");
                            edtPasswordConfirmSetting.setText("");
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Mật khẩu mới không được trùng với mật khẩu cũ.", Toast.LENGTH_SHORT).show();
                        edtOldPassword.setText("");
                        edtPasswordSetting.setText("");
                        edtPasswordConfirmSetting.setText("");
                    }


                } else {
                    Toast.makeText(getApplicationContext(), "Mật khẩu cũ không chính xác.Kiểm tra lại.", Toast.LENGTH_SHORT).show();

                    edtOldPassword.setText("");
                    edtPasswordSetting.setText("");
                    edtPasswordConfirmSetting.setText("");
                }


            } else {
                Toast.makeText(getApplicationContext(), "Xin vui lòng nhập đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
            }

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
