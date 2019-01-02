package com.example.uitstark.dailys_notes.ServiceManage;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


import com.example.uitstark.dailys_notes.Activity.NewBirthdayActivity;
import com.example.uitstark.dailys_notes.DTO.BirthDay;
import com.example.uitstark.dailys_notes.DTO.Global;
import com.example.uitstark.dailys_notes.R;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import static android.app.Notification.VISIBILITY_PUBLIC;
import static android.content.Context.ALARM_SERVICE;

public class AlarmReceiver extends BroadcastReceiver {

    NotificationManager notificationManager;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        //c thong tin se duoc tiep nhan o day
        //no co the chay ngam
        Log.d("TAGGGGGGGGGG:","da nhan duoc thong tin dang ky");

        BirthDay birthDay;
        byte [] data=intent.getByteArrayExtra("birthday");

        try {

//            MediaPlayer mediaPlayer =MediaPlayer.create(context,R.raw.short_sms);
//            mediaPlayer.start();
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(context, notification);
            r.play();

            birthDay= Global.ConverttoBirthday(data);
            String title="sinh nhật "+birthDay.getName();
            String content=birthDay.getNote();

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.icons_app)
                            .setContentTitle(title)
                            .setContentText(content)
                            .setVisibility(VISIBILITY_PUBLIC);
            //co the set them conten cho notify o day
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            int idNotify=birthDay.getId();
            notificationManager.notify(idNotify, mBuilder.build());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


//        //set next notify
//        alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);
//        Intent intent=new Intent(NewBirthdayActivity.this, AlarmReceiver.class);
//        pendingIntent=PendingIntent.getBroadcast(NewBirthdayActivity.this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT    );
//        alarmManager.set(AlarmManager.RTC_WAKEUP,timeRemain,pendingIntent);
    }
}
