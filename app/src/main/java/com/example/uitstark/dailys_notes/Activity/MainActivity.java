package com.example.uitstark.dailys_notes.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.uitstark.dailys_notes.R;

public class MainActivity extends AppCompatActivity {

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        handler=new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//
//        Intent intent=new Intent(MainActivity.this,Login.class);
//               // Log.e("Check","in handler");
//                startActivity(intent);
//         }
//         };
//
//        final Thread thread=new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(300);
//                    handler.sendEmptyMessage(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

        Intent intent=new Intent(MainActivity.this,Login.class);
              // Log.e("Check","in handler");
               startActivity(intent);

    }
}
