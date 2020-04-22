package com.example.leaderboardapp;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

public class NotificationReceiver extends BroadcastReceiver {
    public static final String ACTION_NOTIFICATION_BUTTON = "Healthquestions";
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        String action_source = intent.getAction();
        if(action_source.equals(ACTION_NOTIFICATION_BUTTON)) {
            Bundle bundle = intent.getExtras();
            String user_reply = bundle.getString("User_reply","null");
            if(!user_reply.equals("null"))
                Toast.makeText(context, "received "+user_reply, Toast.LENGTH_SHORT).show();
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.cancel(bundle.getInt("NotificationId"));
        }
    }

}
