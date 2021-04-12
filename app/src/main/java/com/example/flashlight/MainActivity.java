package com.example.flashlight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import static com.example.flashlight.Notif.channel_id1;

public class MainActivity extends AppCompatActivity {

    CameraManager mCameraManager;
    String mCameraId;

    private NotificationManagerCompat notificationManagerCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            mCameraId = mCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        notificationManagerCompat = NotificationManagerCompat.from(this);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        notificationManagerCompat.cancel(1);

        try {
            mCameraManager.setTorchMode(mCameraId, false);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public void Light(View v) {
        Switch toggle = findViewById(R.id.switch1);
        Boolean state = toggle.isChecked();

        try {
            mCameraManager.setTorchMode(mCameraId, state);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        sendNotification(state);
    }

    public void sendNotification(boolean state)
    {
        if (state) {
            Intent intent=new Intent(this, MainActivity.class);
            PendingIntent pendingIntent=PendingIntent.getActivity(this, 0, intent, 0);

            Notification notification = new NotificationCompat.Builder(this, channel_id1)
                    .setSmallIcon(R.drawable.torch)
                    .setContentTitle("Flashlight")
                    .setContentText("Flashlight is turned on")
                    .setContentIntent(pendingIntent)
                    .setOngoing(true)
                    .build();

            notificationManagerCompat.notify(1, notification);
        }
        else{
            notificationManagerCompat.cancel(1);
        }
    }
}