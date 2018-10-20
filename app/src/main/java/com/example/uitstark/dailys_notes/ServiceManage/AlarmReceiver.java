package com.example.uitstark.dailys_notes.ServiceManage;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


import com.example.uitstark.dailys_notes.DTO.BirthDay;
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
     //   BirthDay birthDay= (BirthDay) intent.getSerializableExtra(  "birthday");

        BirthDay birthDay;
        byte [] data=intent.getByteArrayExtra("birthday");
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = null;
        try {
            is = new ObjectInputStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            birthDay= (BirthDay) is.readObject();

            String title=birthDay.getName();
            String content=birthDay.getNote();

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.icon_notify)
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

        // if(birthDay!=null){
//            String title=birthDay.getName();
//            String content=birthDay.getNote();
//
//            NotificationCompat.Builder mBuilder =
//                    new NotificationCompat.Builder(context)
//                            .setSmallIcon(R.drawable.icon_notify)
//                            .setContentTitle(title)
//                            .setContentText(content);
//            //co the set them conten cho notify o day
//            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//            notificationManager.notify(1, mBuilder.build());
      //  }

    }
}
