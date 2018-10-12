package com.example.uitstark.dailys_notes.ServiceManage;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


import com.example.uitstark.dailys_notes.R;

public class AlarmReceiver extends BroadcastReceiver {
   NotificationManager notificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        //c thong tin se duoc tiep nhan o day
        //no co the chay ngam
        Log.d("TAGGGGGGGGGG:","da nhan duoc thong tin dang ky");

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.icon_notify)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");
        //co the set them conten cho notify o day
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, mBuilder.build());
    }
}
