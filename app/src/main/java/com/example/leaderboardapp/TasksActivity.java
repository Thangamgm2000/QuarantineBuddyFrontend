package com.example.leaderboardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RatingBar;

public class TasksActivity extends AppCompatActivity {
    RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        ratingBar = findViewById(R.id.task_rating);
        ratingBar.setRating(4);
    }
}
