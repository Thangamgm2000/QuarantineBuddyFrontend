package com.example.leaderboardapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RegistrationActivity extends AppCompatActivity {
    FloatingActionButton tickButton;
    String currentFrag="personal";
    ProgressBar progressBar;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        tickButton = findViewById(R.id.floating_action_button);
        progressBar = findViewById(R.id.determinateBar);
        progressBar.setProgressBackgroundTintList(ColorStateList.valueOf(getColor(R.color.c5)));
        schedule_notification();
        PersonalDetailsFragment firstFragment = new PersonalDetailsFragment();

        // In case this activity was started with special instructions from an
        // Intent, pass the Intent's extras to the fragment as arguments
        firstFragment.setArguments(getIntent().getExtras());

        // Add the fragment to the 'fragment_container' FrameLayout
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, firstFragment).commit();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void nextFragment(View view) {
        if(currentFrag.equals("personal"))
        {
            progressBar.setIndeterminate(false);
            currentFrag="health";
            progressBar.setProgress(43,true);
            HealthDetailsFragment nextFragment = new HealthDetailsFragment();
            nextFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, nextFragment).commit();
        }
        else if(currentFrag.equals("health"))
        {
            currentFrag="other";
            progressBar.setProgress(72,true);
            OtherDetailsFragment nextFragment = new OtherDetailsFragment();
            nextFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, nextFragment).commit();
        }
        else {
            progressBar.setProgress(100,true);
            registerUser();
        }

    }

    private void registerUser() {
        Intent i = new Intent(getApplicationContext(),LauncherActivity.class);
        startActivity(i);
        this.finish();
    }
    private void schedule_notification() {
        Intent notificationIntent = new Intent( this, NotificationSender. class ) ;
        notificationIntent.putExtra("Alarmtask",true);
        PendingIntent pendingIntent = PendingIntent. getBroadcast ( this, 0 , notificationIntent , PendingIntent. FLAG_UPDATE_CURRENT ) ;
        long futureInMillis = SystemClock. elapsedRealtime () + 1000;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context. ALARM_SERVICE ) ;
        assert alarmManager != null;
        alarmManager.setInexactRepeating(AlarmManager. ELAPSED_REALTIME_WAKEUP , futureInMillis, 2*AlarmManager.INTERVAL_HOUR, pendingIntent); ;
    }
}
