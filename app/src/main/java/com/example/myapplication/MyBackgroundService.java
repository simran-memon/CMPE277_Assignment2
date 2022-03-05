package com.example.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;

public class MyBackgroundService extends Service {

    private String TAGNAME = "BackgroundService";
    NotificationCompat.Builder builder;
    NotificationManagerCompat notificationManager;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.e("Service", "Background service started" );
       Bundle extras = intent.getExtras();

            ArrayList<String> from = (ArrayList<String>) extras.get("allFiles");
          Log.e("Service", from + "aa");
       // }
        for (String fileName:from ) {

            new FileDownloader().execute(fileName);
        }


        createNotificationChannel();

        builder = new NotificationCompat.Builder(this, "105");
        builder.setSmallIcon(R.drawable.icdownload)
                .setContentTitle("File Download")
                .setContentText("In Progress")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        notificationManager = NotificationManagerCompat.from(getApplicationContext());


        notificationManager.notify(1008, builder.build());


        stopSelf();
    return super.onStartCommand(intent, flags, startId);
    }
    private void createNotificationChannel() {
       
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("105", "105", importance);
            
            
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    @Override
    public void onDestroy() {

        Log.e(TAGNAME, "on Destroy");
        builder.setContentText("FileDownloaded");
        notificationManager.notify(1008, builder.build());
        
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
