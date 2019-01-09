package com.example.uitstark.dailys_notes.Activity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.uitstark.dailys_notes.DTO.BirthDay;
import com.example.uitstark.dailys_notes.DTO.Global;
import com.example.uitstark.dailys_notes.DatabaseManage.BirthdayDAL;
import com.example.uitstark.dailys_notes.R;
import com.example.uitstark.dailys_notes.ServiceManage.AlarmReceiver;

import java.text.ParseException;
import java.util.Calendar;

public class EditBirthdayActivity extends AppCompatActivity implements View.OnClickListener{

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

    String currentBithday;
    BirthDay birthDay=new BirthDay();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);

        Bundle bundle = getIntent().getExtras();
        currentBithday=bundle.getString("idBirthDay");

        LinkView();
        SetAction();

        try {
            LoadDefaultData();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



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

    void LoadDefaultData() throws ParseException {
        Log.d("TESSS",currentBithday);

        birthdayDAL = new BirthdayDAL(this);
        birthDay=birthdayDAL.getBirthday(Integer.parseInt(currentBithday));
       // birthDay=birthdayDAL.getBirthday(7);

     //   editTextName.setText("ahihi");
        editTextName.setText(birthDay.getName());
        etDate.setText(birthDay.getBornDay());
        etTime.setText(birthDay.getTimeRemind());
        editTextRelationship.setText(birthDay.getNote());

        String date=birthDay.getBornDay();
        y=Integer.parseInt( date.split("/")[0]);
        m=Integer.parseInt( date.split("/")[1])-1;
        d=Integer.parseInt( date.split("/")[2]);

        String time=birthDay.getTimeRemind();
        h=Integer.parseInt( time.split(":")[0]);
        mi=Integer.parseInt( time.split(":")[1]);

//        Toast.makeText(getApplicationContext(),date,Toast.LENGTH_SHORT).show();
       // Toast.makeText(getApplicationContext(),y+" "+m+" "+d+" "+h+" "+mi,Toast.LENGTH_LONG).show();
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
                //cancl old birthday
                NotificationManager notifManager= (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                notifManager.cancel(birthDay.getId());


                //caculate id user to save, default 1

                String born = etDate.getText().toString();
                String time = etTime.getText().toString();
                String name = String.valueOf(editTextName.getText());
                String relationship = String.valueOf(editTextRelationship.getText());

                //BirthDay birthDay = new BirthDay(Integer.parseInt(currentUser), name, born, time, relationship, 0, Color.GREEN);
                //BirthDay birthDay=new BirthDay(0,name,born,time,relationship,0, Color.GREEN);

                birthDay.setName(name);
                birthDay.setTimeRemind(time);
                birthDay.setBornDay(born);
                birthDay.setNote(relationship);

                birthdayDAL.updateBirthday(birthDay);


                //set notify
                alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                Intent intent = new Intent(EditBirthdayActivity.this, AlarmReceiver.class);

                byte[] data;
                data = Global.ConverttoByte(birthDay);
                intent.putExtra("birthday", data);


                timeBirthday.set(Calendar.MONTH,m);
                timeBirthday.set(Calendar.YEAR,Calendar.getInstance().get(Calendar.YEAR)); //current year, first time remind
                timeBirthday.set(Calendar.DAY_OF_MONTH,d);
                timeBirthday.set(Calendar.HOUR_OF_DAY,h);
                timeBirthday.set(Calendar.MINUTE,mi);
                timeBirthday.set(Calendar.SECOND,0);

                long current=Calendar.getInstance().getTimeInMillis();
                long timeRemain= timeBirthday.getTimeInMillis()-current;


                //sendBroadcast(intent);
                pendingIntent = PendingIntent.getBroadcast(EditBirthdayActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime()+ timeRemain,timeRepeat, pendingIntent);


                //notifi save and come back- update listview birthday
                setResult(ListBirthdayActivity.EDITBIRTHDAYRESULT);
                finish();

                break;
            }
        }
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
