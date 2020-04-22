package com.example.leaderboardapp;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class NotificationReceiver extends BroadcastReceiver {
    public static final String ACTION_NOTIFICATION_BUTTON = "Healthquestions";
    Context hostContext;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        hostContext = context;
        String action_source = intent.getAction();
        if(action_source.equals(ACTION_NOTIFICATION_BUTTON)) {
            Bundle bundle = intent.getExtras();
            String user_reply = bundle.getString("User_reply","null");
            if(!user_reply.equals("null")) {
                postUserResponse(user_reply);
                Toast.makeText(context, "received " + user_reply, Toast.LENGTH_SHORT).show();
            }
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.cancel(bundle.getInt("NotificationId"));
            schedule_notification();
        }
    }
    private void schedule_notification() {
        Intent notificationIntent = new Intent( hostContext, NotificationSender. class ) ;
        PendingIntent pendingIntent = PendingIntent. getBroadcast ( hostContext, 0 , notificationIntent , PendingIntent. FLAG_UPDATE_CURRENT ) ;
        long futureInMillis = SystemClock. elapsedRealtime () + AlarmManager.INTERVAL_HOUR*2 ;
        AlarmManager alarmManager = (AlarmManager) hostContext.getSystemService(Context. ALARM_SERVICE ) ;
        assert alarmManager != null;
        alarmManager.set(AlarmManager. ELAPSED_REALTIME_WAKEUP , futureInMillis , pendingIntent); ;
    }
    public void postUserResponse(final String userResponse)
    {
        String url = "http://70c88512.ngrok.io/getUserResponse";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("userid", "1");
                params.put("userResponse",userResponse);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(hostContext);
        queue.add(postRequest);
    }

}
