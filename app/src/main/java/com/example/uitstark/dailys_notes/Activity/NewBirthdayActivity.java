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
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.uitstark.dailys_notes.DTO.BirthDay;
import com.example.uitstark.dailys_notes.DatabaseManage.BirthdayDAL;
import com.example.uitstark.dailys_notes.R;
import com.example.uitstark.dailys_notes.ServiceManage.AlarmReceiver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewBirthdayActivity extends Activity implements View.OnClickListener{
    Button buttonChooseDateBirthday;
    Button buttonChooseTimeRemindBirthday;
    Button buttonSaveBirthday;
    TextView textViewDate;
    TextView textViewTime;
    EditText editTextName;
    EditText editTextRelationship;

    private int y,m,d;
    private int h,mi;

    BirthdayDAL birthdayDAL;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    Calendar  calendar;
    Calendar  calendarRemind;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);

        buttonChooseDateBirthday =findViewById(R.id.btnChooseDateBirthday);
        buttonChooseTimeRemindBirthday=findViewById(R.id.btnTimeRemindBirthday);
        buttonSaveBirthday=findViewById(R.id.btnSaveBirthDay);

        editTextName=findViewById(R.id.etName);
        editTextRelationship=findViewById(R.id.etRelationship);

        textViewDate=findViewById(R.id.tvDateBirthDay);
        textViewTime=findViewById(R.id.tvTimeBirthDay);

        buttonChooseDateBirthday.setOnClickListener(this);
        buttonChooseTimeRemindBirthday.setOnClickListener(this);
        buttonSaveBirthday.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==buttonChooseDateBirthday){

            calendar=Calendar.getInstance();
            d=calendar.get(Calendar.DAY_OF_MONTH);
            m=calendar.get(Calendar.MONTH)+1;
            y=calendar.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    textViewDate.setText(year+"-"+month+"-"+dayOfMonth);
//                    y=year;
//                    m=month;
//                    d=dayOfMonth;
                }
            },y,m,d);

            datePickerDialog.show();
//            datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
//                @Override
//                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                    y=year;
//                    m=month;
//                    d=dayOfMonth;
//                }
//            });
        }
        else
            if(v==buttonChooseTimeRemindBirthday){

                h=calendar.get(Calendar.HOUR);
                mi=calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        textViewTime.setText(hourOfDay+"-"+minute);
                      //  calendar.set(y,m,d,hourOfDay,minute,0);
                       // calendarRemind.set(y,m,d,hourOfDay,minute,0);
                    }
                }, h, mi, false);

                timePickerDialog.show();
            }

            else { //btn save birthday

                //save data to database
                birthdayDAL=new BirthdayDAL(this);
                //get current user. now we set default user 1
                String born=textViewDate.getText().toString();
                String time=textViewTime.getText().toString();
                String name= String.valueOf(editTextName.getText());
                String relationship=String.valueOf(editTextRelationship.getText());

                BirthDay birthDay=new BirthDay(1,name,born,time,relationship,0, Color.GREEN);
                birthdayDAL.addBirthday(birthDay);


                //set notify
                alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);
                Intent intent=new Intent(NewBirthdayActivity.this, AlarmReceiver.class);

                byte []data;
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                ObjectOutputStream os = null;
                try {
                    os = new ObjectOutputStream(out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    os.writeObject(birthDay);
                    data=out.toByteArray();
                    intent.putExtra("birthday",data);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                //sendBroadcast(intent);
                pendingIntent=PendingIntent.getBroadcast(NewBirthdayActivity.this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT    );
                alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                //alarmManager.set(AlarmManager.RTC_WAKEUP,calendarRemind.getTimeInMillis(),pendingIntent);





                //notifi save and come back- update listview birthday
               // Intent intentGoback=new Intent();

                setResult(ListBirthdayActivity.ADDBIRTHDAYRESULT);
                finish();
            }
    }
}
