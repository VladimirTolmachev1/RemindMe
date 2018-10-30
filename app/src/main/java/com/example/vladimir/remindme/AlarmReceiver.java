package com.example.vladimir.remindme;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String msg = intent.getStringExtra("alarmTxt");

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "default")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("RemindMe")
                .setContentText(msg)
                .setPriority(NotificationCompat.PRIORITY_HIGH);


        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(102, mBuilder.build());
    }
}
