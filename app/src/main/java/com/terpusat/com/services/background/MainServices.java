package com.terpusat.com.services.background;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.terpusat.com.services.MainActivity;
import com.terpusat.com.services.R;

/**
 * Created by prakasa on 03/06/16.
 */
public class MainServices extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Toast.makeText(this,"Service created", Toast.LENGTH_LONG).show();
        this.sendNotification();
        stopSelf();

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        // I want to restart this service again in one hour
        AlarmManager alarm = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarm.set(
                alarm.RTC_WAKEUP,
                System.currentTimeMillis() + (1 * 60 * 60),
                PendingIntent.getService(this, 0, new Intent(this, MainServices.class), 0)
        );
    }

    /* Cuztom Function */
    private void sendNotification() {
        Notification myNotication;

        Intent resultIntent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(this);
        NotificationManager  manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        builder.setAutoCancel(false);
        builder.setTicker("Tracker Mode On");
        builder.setContentTitle("Terpusat Status");
        builder.setContentText("Anda sedang dalam mode GPS");
        builder.setSmallIcon(R.drawable.ic_people);
        builder.setContentIntent(resultPendingIntent);
        builder.setOngoing(true);
        builder.setSubText("Lihat selengkapnya...");   //API level 16
        //builder.setNumber(100);
        builder.build();

        myNotication = builder.getNotification();
        manager.notify(11, myNotication);
    }
}
