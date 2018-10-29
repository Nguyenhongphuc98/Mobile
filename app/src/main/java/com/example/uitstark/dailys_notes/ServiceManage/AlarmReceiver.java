package com.example.uitstark.dailys_notes.ServiceManage;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


import com.example.uitstark.dailys_notes.DTO.BirthDay;
import com.example.uitstark.dailys_notes.DTO.Global;
import com.example.uitstark.dailys_notes.R;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class AlarmReceiver extends BroadcastReceiver {
   NotificationManager notificationManager;

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
            String title=birthDay.getName();
            String content=birthDay.getNote();

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.icons_app)
                            .setContentTitle(title)
                            .setContentText(content);
            //co the set them conten cho notify o day
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1, mBuilder.build());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
