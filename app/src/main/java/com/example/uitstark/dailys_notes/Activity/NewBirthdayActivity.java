package com.example.uitstark.dailys_notes.Activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.uitstark.dailys_notes.R;
import com.example.uitstark.dailys_notes.ServiceManage.AlarmReceiver;

import java.util.Calendar;

public class NewBirthdayActivity extends Activity implements View.OnClickListener{
    Button buttonChooseDateBirthday;
    Button buttonChooseTimeRemindBirthday;
    Button buttonSaveBirthday;
    TextView textViewDate;
    TextView textViewTime;

    private int y,m,d;
    private int h,mi;

    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    final Calendar  calendar=Calendar.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);

        buttonChooseDateBirthday =findViewById(R.id.btnChooseDateBirthday);
        buttonChooseTimeRemindBirthday=findViewById(R.id.btnTimeRemindBirthday);
        buttonSaveBirthday=findViewById(R.id.btnSaveBirthDay);

        textViewDate=findViewById(R.id.tvDateBirthDay);
        textViewTime=findViewById(R.id.tvTimeBirthDay);

        buttonChooseDateBirthday.setOnClickListener(this);
        buttonChooseTimeRemindBirthday.setOnClickListener(this);
        buttonSaveBirthday.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v==buttonChooseDateBirthday){
            d=calendar.get(Calendar.DAY_OF_MONTH);
            m=calendar.get(Calendar.MONTH);
            y=calendar.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    textViewDate.setText("birthday:"+dayOfMonth+"-"+month+"-"+year);
                }
            },y,m,d);

            datePickerDialog.show();
        }
        else

            if(v==buttonChooseTimeRemindBirthday){

                h=calendar.get(Calendar.HOUR);
                mi=calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        textViewTime.setText("time remind:"+hourOfDay+"-"+minute);
                    }
                },h,mi,false);

                timePickerDialog.show();
            }

            else { //btn save birthday
                alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);
                Intent intent=new Intent(NewBirthdayActivity.this, AlarmReceiver.class);
                pendingIntent=PendingIntent.getBroadcast(NewBirthdayActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);

                Intent intentGoback=new Intent();
                setResult(ListBirthdayActivity.ADDBIRTHDAYRESULT);
                finish();
            }
    }
}
