package com.example.leaderboardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class UserProfile extends AppCompatActivity {
    TextView userName,rewardPoints,currentActivity;
    ListView navigationlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        userName = findViewById(R.id.username);
        rewardPoints = findViewById(R.id.reward_points);
        currentActivity = findViewById(R.id.country);
        navigationlist = findViewById(R.id.navigationList);
        userName.setText("Username: Svijay");
        rewardPoints.setText("Score: 120");
        currentActivity.setText("Country: India");
        ArrayList<String> navItems = new ArrayList<String>();
        navItems.add("Meditation");
        navItems.add("My tasks");
        navItems.add("My health");
        NavigationListAdapter adapter = new NavigationListAdapter(getApplicationContext(),navItems);
        navigationlist.setAdapter(adapter);
    }
}
