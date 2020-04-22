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
        /*createNotificationChannel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("How do you feel")
                .setContentText("Rate here, how do you feel?")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .addAction(R.drawable.notification_icon, "Good",
                        create_acion("Good",0))
                .addAction(R.drawable.notification_icon, "OK",
                        create_acion("OK",1))
                .addAction(R.drawable.notification_icon, "Bad",
                        create_acion("Bad",2));

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        builder.setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
// notificationId is a unique int for each notification that you must define
        notificationManager.notify(Health_Notification_id, builder.build());
*/

    }

    private void schedule_notification() {
        Intent notificationIntent = new Intent( this, NotificationSender. class ) ;
        PendingIntent pendingIntent = PendingIntent. getBroadcast ( this, 0 , notificationIntent , PendingIntent. FLAG_UPDATE_CURRENT ) ;
        long futureInMillis = SystemClock. elapsedRealtime () + 10000 ;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context. ALARM_SERVICE ) ;
        assert alarmManager != null;
        alarmManager.set(AlarmManager. ELAPSED_REALTIME_WAKEUP , futureInMillis , pendingIntent); ;
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "HealthQuestioning";
            String description = "To monitor user health";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    private PendingIntent create_acion(String choice,int requestCode)
    {
        Intent replyIntent = new Intent(this, NotificationReceiver.class);
        replyIntent.setAction(NotificationReceiver.ACTION_NOTIFICATION_BUTTON);
        replyIntent.putExtra("User_reply", choice);
        replyIntent.putExtra("NotificationId",Health_Notification_id);
        PendingIntent replyPendingIntent = PendingIntent.getBroadcast(this, requestCode, replyIntent, 0);
        return replyPendingIntent;
    }

}
