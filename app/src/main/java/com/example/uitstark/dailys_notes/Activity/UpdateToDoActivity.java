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
import android.widget.Toast;

import com.example.uitstark.dailys_notes.DTO.BirthDay;
import com.example.uitstark.dailys_notes.DTO.Convert;
import com.example.uitstark.dailys_notes.DTO.Global;
import com.example.uitstark.dailys_notes.DTO.ToDo;
import com.example.uitstark.dailys_notes.DatabaseManage.BirthdayDAL;
import com.example.uitstark.dailys_notes.DatabaseManage.NoteDAL;
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

public class UpdateToDoActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnUpdateToDo;
    Button buttonChooseTimeRemindUpdateToDo;
    Button btnBackToDoUpdate;
    TextView textViewTimeUpdate;
    EditText editTextUpdateTitle;
    EditText editTextUpdateContent;

    private int h, mi;

    ToDoDAL toDoDAL;
    ToDo toDo;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    Calendar calendar = Calendar.getInstance();
    Calendar timeRemind=Calendar.getInstance();

    String currentUser;
    private String currentToDo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatetodo);

        Bundle bundle = getIntent().getExtras();
        currentUser = bundle.getString("idCurrentUser");
        currentToDo = bundle.getString("idCurrentToDo");

        LinkView();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SetAction();
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        toDoDAL = new ToDoDAL(UpdateToDoActivity.this);
        try {
            LoadToDoDataFromDatabase(currentToDo);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    void LinkView() {
        buttonChooseTimeRemindUpdateToDo = findViewById(R.id.btnTimeRemindUpdateToDo);
        btnUpdateToDo = findViewById(R.id.btnUpdateToDo);
        btnBackToDoUpdate = findViewById(R.id.btnBackToDoUpdate);
        editTextUpdateTitle = findViewById(R.id.edt_titleUpdateToDo);
        editTextUpdateContent = findViewById(R.id.edt_contentUpdateToDo);
        textViewTimeUpdate = findViewById(R.id.tvTimeUpdateToDo);
    }

    private void LoadToDoDataFromDatabase(String idCurrentToDo) throws ParseException {
        int id = Integer.parseInt(idCurrentToDo);
        toDo = toDoDAL.getToDo(id);
        editTextUpdateTitle.setText(toDo.getTitle());
        editTextUpdateContent.setText(toDo.getContent());
        textViewTimeUpdate.setText(toDo.getTime());

    }

    void SetAction() {
        buttonChooseTimeRemindUpdateToDo.setOnClickListener(this);
        btnUpdateToDo.setOnClickListener(this);
        btnBackToDoUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == buttonChooseTimeRemindUpdateToDo) {

            h = calendar.get(Calendar.HOUR_OF_DAY);
            mi = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    textViewTimeUpdate.setText(hourOfDay + ":" + minute);
                    h = hourOfDay;
                    mi = minute;
                }
            }, h, mi, true);

            timePickerDialog.show();
        } else if(v==btnBackToDoUpdate)  {
            setResult(ListToDoActivity.ADDTODORESULT);
            finish();
        }
            else {
            toDoDAL = new ToDoDAL(this);
            String title = editTextUpdateTitle.getText().toString();
            String content = String.valueOf(editTextUpdateContent.getText());
            String time = textViewTimeUpdate.getText().toString();
            ToDo toDo = new ToDo(Integer.parseInt(currentUser), title, content, time,0, Color.WHITE);
            toDoDAL.updateToDo(Integer.parseInt(currentToDo), Integer.parseInt(currentUser), toDo);

            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            Intent intent = new Intent(UpdateToDoActivity.this, AlarmToDo.class);


            byte[] data;
            data = Convert.ConverttoByte(toDo);
            intent.putExtra("todo", data);
            timeRemind.set(Calendar.HOUR,h);
            timeRemind.set(Calendar.MINUTE,mi);
            timeRemind.set(Calendar.SECOND,0);
            long current=Calendar.getInstance().getTimeInMillis();
            long timeRemain= timeRemind.getTimeInMillis()-current;

            pendingIntent = PendingIntent.getBroadcast(UpdateToDoActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            alarmManager.set(AlarmManager.RTC_WAKEUP, timeRemain, pendingIntent);
            setResult(ListToDoActivity.ADDTODORESULT);
            finish();
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
