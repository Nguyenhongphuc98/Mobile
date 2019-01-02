package com.example.uitstark.dailys_notes.Activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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

public class NewBirthdayActivity extends AppCompatActivity implements View.OnClickListener{
    //Button buttonChooseDateBirthday;
    //Button buttonChooseTimeRemindBirthday;

    ImageView ivDate,ivTime;
    Button buttonSaveBirthday;
    //TextView textViewDate;
    // TextView textViewTime;
    EditText etDate,etTime;
    EditText editTextName;
    EditText editTextRelationship;

    private int y,m,d;
    private int h,mi;

    BirthdayDAL birthdayDAL;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    Calendar calendar=Calendar.getInstance(); //current
    Calendar timeBirthday=Calendar.getInstance();
    final long timeRepeat =31104000000L;//12*30*24*60*60*1000

    String currentUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);

        Bundle bundle = getIntent().getExtras();
        currentUser=bundle.getString("idCurrentUser");

        LinkView();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        SetAction();
    }

    void LinkView(){
        // buttonChooseDateBirthday =findViewById(R.id.btnChooseDateBirthday);
        // buttonChooseTimeRemindBirthday=findViewById(R.id.btnTimeRemindBirthday);

        ivDate= findViewById(R.id.ivDate);
        ivTime=findViewById(R.id.ivTime);
        buttonSaveBirthday=findViewById(R.id.btnSaveBirthDay);

        editTextName=findViewById(R.id.etName);
        editTextRelationship=findViewById(R.id.etRelationship);

        // textViewDate=findViewById(R.id.tvDateBirthDay);
        // textViewTime=findViewById(R.id.tvTimeBirthDay);
        etDate=findViewById(R.id.etDate);
        etTime=findViewById(R.id.etTime);
    }

    void SetAction(){
        //buttonChooseDateBirthday.setOnClickListener(this);
        //buttonChooseTimeRemindBirthday.setOnClickListener(this);
        ivDate.setOnClickListener(this);
        ivTime.setOnClickListener(this);
        buttonSaveBirthday.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ivDate: {
                d = calendar.get(Calendar.DAY_OF_MONTH);
                m = calendar.get(Calendar.MONTH);
                y = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        etDate.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
                        y = year;
                        m = month;
                        d = dayOfMonth;
                    }
                }, y, m, d);

                datePickerDialog.show();
                break;
            }

            case R.id.ivTime: {
                //Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
                h = calendar.get(Calendar.HOUR_OF_DAY);
                mi = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        etTime.setText(hourOfDay + ":" + minute);
                        h = hourOfDay;
                        mi = minute;
                    }
                }, h, mi, true);
                timePickerDialog.show();
                break;
            }

            case R.id.btnSaveBirthDay:
            {
                //save data to database
                birthdayDAL = new BirthdayDAL(this);
                //caculate id user to save, default 1

                String born = etDate.getText().toString();
                String time = etTime.getText().toString();
                String name = String.valueOf(editTextName.getText());
                String relationship = String.valueOf(editTextRelationship.getText());

                BirthDay birthDay = new BirthDay(Integer.parseInt(currentUser), name, born, time, relationship, 0, Color.GREEN);
                //BirthDay birthDay=new BirthDay(0,name,born,time,relationship,0, Color.GREEN);
                birthdayDAL.addBirthday(birthDay);


                //set notify
                alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                Intent intent = new Intent(NewBirthdayActivity.this, AlarmReceiver.class);

                byte[] data;
                data = Global.ConverttoByte(birthDay);
                intent.putExtra("birthday", data);

                //cacula time;
//                long timeRemain;
//                long yearMili = (y - calendar.get(Calendar.YEAR)) * 365 * 24 * 60 * 60 * 1000;
//                long monthMili = (m - calendar.get(Calendar.MONTH)) * calendar.get(Calendar.MONTH) * 24 * 60 * 60 * 1000;
//                long dayMili = (d - calendar.get(Calendar.DAY_OF_MONTH)) * 24 * 60 * 60 * 1000;
//                long hourMili = (h - calendar.get(Calendar.HOUR_OF_DAY)) * 60 * 60 * 1000;
//                long minuteMili = (mi - calendar.get(Calendar.MINUTE)) * 60 * 1000;
//                timeRemain = calendar.getTimeInMillis() + yearMili + monthMili + dayMili + hourMili + minuteMili;

                //Calendar timeRemind=Calendar.getInstance();
                timeBirthday.set(Calendar.MONTH,m);
                timeBirthday.set(Calendar.YEAR,Calendar.getInstance().get(Calendar.YEAR)); //current year, first time remind
                timeBirthday.set(Calendar.DAY_OF_MONTH,d);
                timeBirthday.set(Calendar.HOUR_OF_DAY,h);
                timeBirthday.set(Calendar.MINUTE,mi);
                timeBirthday.set(Calendar.SECOND,0);

                long current=Calendar.getInstance().getTimeInMillis();
                long timeRemain= timeBirthday.getTimeInMillis()-current;


                //sendBroadcast(intent);
                pendingIntent = PendingIntent.getBroadcast(NewBirthdayActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime()+ timeRemain,timeRepeat, pendingIntent);


                //notifi save and come back- update listview birthday
                setResult(ListBirthdayActivity.ADDBIRTHDAYRESULT);
                finish();

                break;
            }
        }

//        if(v==buttonChooseDateBirthday){
//            d=calendar.get(Calendar.DAY_OF_MONTH);
//            m=calendar.get(Calendar.MONTH);
//            y=calendar.get(Calendar.YEAR);
//
//            DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
//                @Override
//                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                    textViewDate.setText(year+"-"+(month+1)+"-"+dayOfMonth);
//                    y=year;
//                    m=month;
//                    d=dayOfMonth;
//                }
//            },y,m,d);
//
//            datePickerDialog.show();
//        }
//        else
//        if(v==buttonChooseTimeRemindBirthday){
//
//            h=calendar.get(Calendar.HOUR_OF_DAY);
//            mi=calendar.get(Calendar.MINUTE);
//
//            TimePickerDialog timePickerDialog=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
//                @Override
//                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                    textViewTime.setText(hourOfDay+"-"+minute);
//                    h=hourOfDay;
//                    mi=minute;
//                }
//            }, h, mi, true);
//
//            timePickerDialog.show();
//        }
//
//        else { //btn save birthday
//
//            //save data to database
//            birthdayDAL=new BirthdayDAL(this);
//            //caculate id user to save, default 1
//
//            String born=textViewDate.getText().toString();
//            String time=textViewTime.getText().toString();
//            String name= String.valueOf(editTextName.getText());
//            String relationship=String.valueOf(editTextRelationship.getText());
//
//            BirthDay birthDay=new BirthDay(Integer.parseInt(currentUser),name,born,time,relationship,0, Color.GREEN);
//            //BirthDay birthDay=new BirthDay(0,name,born,time,relationship,0, Color.GREEN);
//            birthdayDAL.addBirthday(birthDay);
//
//
//            //set notify
//            alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);
//            Intent intent=new Intent(NewBirthdayActivity.this, AlarmReceiver.class);
//
//            byte []data;
//            data= Global.ConverttoByte(birthDay);
//            intent.putExtra("birthday",data);
//
//            //cacula time;
//            long timeRemain;
//            long yearMili=(y-calendar.get(Calendar.YEAR))*365*24*60*60*1000;
//            long monthMili=(m-calendar.get(Calendar.MONTH))*calendar.get(Calendar.MONTH)*24*60*60*1000;
//            long dayMili=(d-calendar.get(Calendar.DAY_OF_MONTH))*24*60*60*1000;
//            long hourMili=(h-calendar.get(Calendar.HOUR_OF_DAY))*60*60*1000;
//            long minuteMili=(mi-calendar.get(Calendar.MINUTE))*60*1000;
//            timeRemain=calendar.getTimeInMillis()+yearMili+monthMili+dayMili+hourMili+minuteMili;
//
////                Log.d("TAAAAAAYEAR",String.valueOf(yearMili));
////                Log.d("TAAAAAAMONTH",String.valueOf(monthMili));
////                Log.d("TAAAAAAday",String.valueOf(dayMili));
////                Log.d("TAAAAAAhour",String.valueOf(hourMili));
////                Log.d("TAAAAAAminuteH",String.valueOf(minuteMili));
//
//            //sendBroadcast(intent);
//            pendingIntent=PendingIntent.getBroadcast(NewBirthdayActivity.this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT    );
//            alarmManager.set(AlarmManager.RTC_WAKEUP,timeRemain,pendingIntent);
//
//
//            //notifi save and come back- update listview birthday
//            setResult(ListBirthdayActivity.ADDBIRTHDAYRESULT);
//            finish();
//        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:break;
        }

        return super.onOptionsItemSelected(item);
    }
}
