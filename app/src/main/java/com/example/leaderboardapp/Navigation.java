package com.example.leaderboardapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class Navigation extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        check_for_permissions();
        setContentView(R.layout.activity_navigation);
        checkAlarmTask();
    }



    public void openLeaderboard(View view) {
        Intent i = new Intent(this,LeaderboardActivity.class);
        startActivity(i);
    }

    public void openSensorMonitor(View view) {
        Intent i = new Intent(this,SensorMonitorActivity.class);
        startActivity(i);
    }

    private void check_for_permissions() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // No explanation needed; request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
       check_for_permissions();
    }

    public void openProfile(View view) {
        Intent i = new Intent(this,UserProfile.class);
        startActivity(i);
    }

    public void sendnotifications(View view) {
        Intent i = new Intent(this,Notification_Activity.class);
        startActivity(i);
    }
    public void checkAlarmTask()
    {
        boolean alarmUp = (PendingIntent.getBroadcast(getApplicationContext(), 0,
                new Intent(getApplicationContext(),NotificationSender.class).putExtra("Alarmtask",true),
                PendingIntent.FLAG_NO_CREATE) != null);

        if (alarmUp)
        {
            Toast.makeText(getApplicationContext(),"Task is already scheduled.",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"No scheduled tasks",Toast.LENGTH_SHORT).show();
        }
    }
}
