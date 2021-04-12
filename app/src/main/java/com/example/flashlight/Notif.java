package com.example.flashlight;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class Notif extends Application {

    public static final String channel_id1 = "1";

    @Override
    public void onCreate()
    {
        super.onCreate();
        createNotifChannel();
    }

    public void createNotifChannel()
    {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel channel1 = new NotificationChannel(channel_id1, "Torch status", NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("Show the status of the torch");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
    }
}
