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
import com.example.uitstark.dailys_notes.DTO.Convert;
import com.example.uitstark.dailys_notes.DTO.Global;
import com.example.uitstark.dailys_notes.DTO.ToDo;
import com.example.uitstark.dailys_notes.R;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class AlarmToDo extends BroadcastReceiver {
    NotificationManager notificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        ToDo toDo;
        byte [] data=intent.getByteArrayExtra("todo");
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(context, notification);
            r.play();
            toDo= Convert.ConverttoToDo(data);
            String title=toDo.getTitle();
            String content=toDo.getContent();
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.icons_app)
                            .setContentTitle(title)
                            .setContentText(content);
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1, mBuilder.build());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
