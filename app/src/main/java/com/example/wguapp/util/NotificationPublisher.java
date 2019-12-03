package com.example.wguapp.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.wguapp.R;

public class NotificationPublisher extends BroadcastReceiver {
    public static String NOTIFICATION_ID = "notification-id" ;
    public static String NOTIFICATION = "notification" ;

    @Override
    public void onReceive (Context context , Intent intent) {

        NotificationManager nManager = context.getSystemService(NotificationManager.class);
        Notification notification = intent.getParcelableExtra(NOTIFICATION);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getResources().getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(Constants.NOTIFICATION_CHANNEL_ID, name, importance);
            channel.setDescription(description);
            nManager.createNotificationChannel(channel);
        }

        int id = intent.getIntExtra(NOTIFICATION_ID, 0 );
        assert nManager != null;
        nManager.notify(id , notification) ;
    }
}
