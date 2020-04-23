package com.example.leaderboardapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;

public class Notification_Activity extends AppCompatActivity {
    String CHANNEL_ID="HealthQuestioning";
    int Health_Notification_id=0;
    PendingIntent ScheduledPendingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_);
        schedule_notification();

    }

    private void schedule_notification() {
        Intent notificationIntent = new Intent( this, NotificationSender. class ) ;
        notificationIntent.putExtra("Alarmtask",true);
        PendingIntent pendingIntent = PendingIntent. getBroadcast ( this, 0 , notificationIntent , PendingIntent. FLAG_UPDATE_CURRENT ) ;
        long futureInMillis = SystemClock. elapsedRealtime () + 1000;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context. ALARM_SERVICE ) ;
        assert alarmManager != null;
        alarmManager.setInexactRepeating(AlarmManager. ELAPSED_REALTIME_WAKEUP , futureInMillis, 2*AlarmManager.INTERVAL_HOUR, pendingIntent); ;
    }



}
