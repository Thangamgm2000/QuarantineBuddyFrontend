package com.example.leaderboardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class UserProfile extends AppCompatActivity {
    TextView userName,rewardPoints,currentActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        userName = findViewById(R.id.username);
        rewardPoints = findViewById(R.id.reward_points);
        currentActivity = findViewById(R.id.current_activity);
        userName.setText("Username: Svijay");
        rewardPoints.setText("Score: 120");
        currentActivity.setText("Current Activity: Meditation");
    }
}
