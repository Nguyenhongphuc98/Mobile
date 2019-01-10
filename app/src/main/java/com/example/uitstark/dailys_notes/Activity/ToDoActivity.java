package com.example.uitstark.dailys_notes.Activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.uitstark.dailys_notes.DTO.BirthDay;
import com.example.uitstark.dailys_notes.DTO.Convert;
import com.example.uitstark.dailys_notes.DTO.Global;
import com.example.uitstark.dailys_notes.DTO.ToDo;
import com.example.uitstark.dailys_notes.DatabaseManage.BirthdayDAL;
import com.example.uitstark.dailys_notes.DatabaseManage.ToDoDAL;
import com.example.uitstark.dailys_notes.R;
import com.example.uitstark.dailys_notes.ServiceManage.AlarmReceiver;
import com.example.uitstark.dailys_notes.ServiceManage.AlarmToDo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ToDoActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnSaveToDo;
    Button buttonChooseTimeRemindToDo;
    Button btnBackToDo;
    TextView textViewTime;
    EditText editTextTitle;
    EditText editTextContent;

    private int h, mi;

    ToDoDAL toDoDAL;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    Calendar calendar = Calendar.getInstance();
    Calendar timeRemind=Calendar.getInstance();
    String currentUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        Bundle bundle = getIntent().getExtras();
        currentUser = bundle.getString("idCurrentUser");

        LinkView();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SetAction();
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    void LinkView() {
        buttonChooseTimeRemindToDo = findViewById(R.id.btnTimeRemindToDo);
        btnSaveToDo = findViewById(R.id.btnSaveToDo);
        btnBackToDo = findViewById(R.id.btnBackToDo);
        editTextTitle = findViewById(R.id.edt_titleToDo);
        editTextContent = findViewById(R.id.edt_contentToDo);
        textViewTime = findViewById(R.id.tvTimeToDo);
    }

    void SetAction() {
        buttonChooseTimeRemindToDo.setOnClickListener(this);
        btnSaveToDo.setOnClickListener(this);
        btnBackToDo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == buttonChooseTimeRemindToDo) {

            h = calendar.get(Calendar.HOUR_OF_DAY);
            mi = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    textViewTime.setText(hourOfDay + ":" + minute);
                    h = hourOfDay;
                    mi = minute;
                }
            }, h, mi, true);

            timePickerDialog.show();
        }else if(v==btnBackToDo) {
            setResult(ListToDoActivity.ADDTODORESULT);
            finish();
        }
            else {
            toDoDAL = new ToDoDAL(this);
            String title = editTextTitle.getText().toString();
            String content = String.valueOf(editTextContent.getText());
            String time = textViewTime.getText().toString();
            ToDo toDo = new ToDo(Integer.parseInt(currentUser), title, content, time,0, Color.WHITE);
            toDoDAL.addToDo(toDo);


            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            Intent intent = new Intent(ToDoActivity.this, AlarmToDo.class);


            byte[] data;
            data = Convert.ConverttoByte(toDo);
            intent.putExtra("todo", data);
            timeRemind.set(Calendar.HOUR,h);
            timeRemind.set(Calendar.MINUTE,mi);
            timeRemind.set(Calendar.SECOND,0);
            long current=Calendar.getInstance().getTimeInMillis();
            long timeRemain= timeRemind.getTimeInMillis()-current;

            pendingIntent = PendingIntent.getBroadcast(ToDoActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            alarmManager.set(AlarmManager.RTC_WAKEUP, timeRemain, pendingIntent);
            setResult(ListToDoActivity.ADDTODORESULT);
            finish();
        }
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                onBackPressed();
//                return true;
//
//            default:
//                break;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
