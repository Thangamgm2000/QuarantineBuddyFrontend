package com.example.leaderboardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Navigation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
    }

    public void openLeaderboard(View view) {
        Intent i = new Intent(this,LeaderboardActivity.class);
        startActivity(i);
    }

    public void openSensorMonitor(View view) {
        Intent i = new Intent(this,SensorMonitorActivity.class);
        startActivity(i);
    }
}
