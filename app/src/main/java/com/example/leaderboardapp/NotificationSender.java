package com.example.leaderboardapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

public class NotificationSender extends BroadcastReceiver {
    Context hostContext;
    String CHANNEL_ID="HealthQuestioning";
    int Health_Notification_id=0;
    public static final String ACTION_NOTIFICATION_BUTTON = "AskHealthquestions";
    @Override
    public void onReceive(Context context, Intent intent) {
        hostContext = context;
        createNotificationChannel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(hostContext, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("How do you feel")
                .setContentText("Rate here, how do you feel?")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .addAction(R.drawable.notification_icon, "Good",
                        create_action("Good",0))
                .addAction(R.drawable.notification_icon, "OK",
                        create_action("OK",1))
                .addAction(R.drawable.notification_icon, "Bad",
                        create_action("Bad",2));

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(hostContext);
        builder.setColor(ContextCompat.getColor(hostContext, R.color.colorPrimary));
// notificationId is a unique int for each notification that you must define
        notificationManager.notify(Health_Notification_id, builder.build());
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
            NotificationManager notificationManager =  hostContext.getSystemService(NotificationManager.class);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }
    }
    private PendingIntent create_action(String choice, int requestCode)
    {
        Intent replyIntent = new Intent(hostContext, NotificationReceiver.class);
        replyIntent.setAction(NotificationReceiver.ACTION_NOTIFICATION_BUTTON);
        replyIntent.putExtra("User_reply", choice);
        replyIntent.putExtra("NotificationId",Health_Notification_id);
        PendingIntent replyPendingIntent = PendingIntent.getBroadcast(hostContext, requestCode, replyIntent, 0);
        return replyPendingIntent;
    }
}
