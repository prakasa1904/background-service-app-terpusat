package com.terpusat.com.services.background;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.terpusat.com.services.MainActivity;
import com.terpusat.com.services.R;
import com.terpusat.com.services.database.SqlliteDriver;
import com.terpusat.com.services.monitor.GPSTracker;

/**
 * Created by prakasa on 03/06/16.
 */
public class MainServices extends Service {
    private static final int MY_NOTIFICATION_ID = 190492;
    SqlliteDriver mydb = new SqlliteDriver(this);
    GPSTracker gps = new GPSTracker(this);

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
        Cursor rest = mydb.getDataByName();
        rest.moveToFirst();
        String timeUpdate = rest.getString(rest.getColumnIndex(SqlliteDriver.PENGATURAN_COLUMN_TIME));
        String statusUpdate = rest.getString(rest.getColumnIndex(SqlliteDriver.PENGATURAN_COLUMN_STATUS));

        if( statusUpdate.matches("1") ) {
            // I want to restart this service again in
            AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarm.set(
                    alarm.RTC_WAKEUP,
                    System.currentTimeMillis() + (Integer.parseInt(timeUpdate) * 60 * 60),
                    PendingIntent.getService(this, 0, new Intent(this, MainServices.class), 0)
            );
        }else{
            Toast.makeText(this,"Service Stoped", Toast.LENGTH_LONG).show();
        }
    }

    /* Cuztom Function */
    private void sendNotification() {
        Cursor rest = mydb.getDataByName();
        rest.moveToFirst();
        String statusUpdate = rest.getString(rest.getColumnIndex(SqlliteDriver.PENGATURAN_COLUMN_STATUS));

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if( statusUpdate.matches("1") ) {
            Notification myNotication;
            Intent resultIntent = new Intent(this, MainActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(MainActivity.class);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            Notification.Builder builder = new Notification.Builder(this);

            builder.setAutoCancel(false);
            builder.setTicker("Tracker Mode On");
            builder.setContentTitle("Terpusat");
            builder.setContentText("GPS Mode Actived");
            builder.setSmallIcon(R.drawable.ic_people);
            builder.setContentIntent(resultPendingIntent);
            builder.setOngoing(true);
            //builder.setSubText("Change Setting");   //API level 16
            //builder.setNumber(100);
            builder.build();

            myNotication = builder.getNotification();
            manager.notify(MY_NOTIFICATION_ID, myNotication);
            if( gps.canGetLocation() )
                gps.getLocation();

        }else {
            manager.cancel(MY_NOTIFICATION_ID);

        }
    }
}
