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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.uitstark.dailys_notes.DTO.BirthDay;
import com.example.uitstark.dailys_notes.DTO.Global;
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
import java.util.List;

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
    Calendar   calendar=Calendar.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);

        LinkView();
        SetAction();
    }

    void LinkView(){
        buttonChooseDateBirthday =findViewById(R.id.btnChooseDateBirthday);
        buttonChooseTimeRemindBirthday=findViewById(R.id.btnTimeRemindBirthday);
        buttonSaveBirthday=findViewById(R.id.btnSaveBirthDay);

        editTextName=findViewById(R.id.etName);
        editTextRelationship=findViewById(R.id.etRelationship);

        textViewDate=findViewById(R.id.tvDateBirthDay);
        textViewTime=findViewById(R.id.tvTimeBirthDay);
    }

    void SetAction(){
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
                    textViewDate.setText(year+"-"+(month+1)+"-"+dayOfMonth);
                    y=year;
                    m=month;
                    d=dayOfMonth;
                }
            },y,m,d);

            datePickerDialog.show();
        }
        else
            if(v==buttonChooseTimeRemindBirthday){

                h=calendar.get(Calendar.HOUR_OF_DAY);
                mi=calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        textViewTime.setText(hourOfDay+"-"+minute);
                        h=hourOfDay;
                        mi=minute;
                    }
                }, h, mi, true);

                timePickerDialog.show();
            }

            else { //btn save birthday

                //save data to database
                birthdayDAL=new BirthdayDAL(this);
                //caculate id user to save, default 1

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
//                ByteArrayOutputStream out = new ByteArrayOutputStream();
//                ObjectOutputStream os = null;
//                try {
//                    os = new ObjectOutputStream(out);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    os.writeObject(birthDay);
//                    data=out.toByteArray();
//                    intent.putExtra("birthday",data);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

                data= Global.ConverttoByte(birthDay);
                intent.putExtra("birthday",data);

                //cacula time;
                long timeRemain;
                long yearMili=(y-calendar.get(Calendar.YEAR))*365*24*60*60*1000;
                long monthMili=(m-calendar.get(Calendar.MONTH))*calendar.get(Calendar.MONTH)*24*60*60*1000;
                long dayMili=(d-calendar.get(Calendar.DAY_OF_MONTH))*24*60*60*1000;
                long hourMili=(h-calendar.get(Calendar.HOUR_OF_DAY))*60*60*1000;
                long minuteMili=(mi-calendar.get(Calendar.MINUTE))*60*1000;
                timeRemain=calendar.getTimeInMillis()+yearMili+monthMili+dayMili+hourMili+minuteMili;

//                Log.d("TAAAAAAYEAR",String.valueOf(yearMili));
//                Log.d("TAAAAAAMONTH",String.valueOf(monthMili));
//                Log.d("TAAAAAAday",String.valueOf(dayMili));
//                Log.d("TAAAAAAhour",String.valueOf(hourMili));
//                Log.d("TAAAAAAminuteH",String.valueOf(minuteMili));

                //sendBroadcast(intent);
                pendingIntent=PendingIntent.getBroadcast(NewBirthdayActivity.this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT    );
                alarmManager.set(AlarmManager.RTC_WAKEUP,timeRemain,pendingIntent);


                //notifi save and come back- update listview birthday
                setResult(ListBirthdayActivity.ADDBIRTHDAYRESULT);
                finish();
            }
    }
}
