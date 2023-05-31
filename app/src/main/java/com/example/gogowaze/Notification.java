package com.example.gogowaze;


import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

public class Notification extends Application {
    public static final String CHANNEL_ID = "channel1";
    private static NotificationManager notificationManager;

    private final String TAG = "LouisDedieu "+getClass().getSimpleName();

    @Override public void onCreate() {
        super.onCreate();
        createNotificationChannel("channel de fred", "Channel pour l'application de demo des notifications");
    }

    private void createNotificationChannel(String name, String desc) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d(TAG,"createNotifChannel");
            int importance = NotificationManager.IMPORTANCE_HIGH; // Set the importance level
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(desc);

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
            else {
                Log.d(TAG,"notificationManager"+notificationManager);
            }
        }
    }

    public static NotificationManager getNotificationManager() {
        return notificationManager;
    }
}