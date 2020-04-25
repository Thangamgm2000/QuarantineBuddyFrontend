package com.example.leaderboardapp;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class profile extends AppCompatActivity {

    TextView name,country,score,current;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilexml);
        name=findViewById(R.id.tv_name);
        country=findViewById(R.id.country);
        score=findViewById(R.id.scorepoints);
        current=findViewById(R.id.current);
        // Name from database
        name.setText("name");
        // Country from Database
        country.setText("country");
        //score from database
        score.setText("score");
        //current activity
        current.setText("Activity");


    }
    public void editpro(View view){
        //image updating and sending to database
    }
    public void logoff(View view){
        //Normal intent to home page
        //Intent i = new Intent(profile.this,home.class);
        //        startActivity(i);
    }
    public void cact(View view){
        //if any activity to show a new page of current acitivity intent can be used
    }
    public void comact(View view){
        //Normal intent to completed activities page
        //Intent i = new Intent(profile.this,comactivities.class);
        //        startActivity(i);
    }



}

