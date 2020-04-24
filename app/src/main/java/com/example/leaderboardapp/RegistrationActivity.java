package com.example.leaderboardapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RegistrationActivity extends AppCompatActivity {
    FloatingActionButton tickButton;
    String currentFrag="personal";
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        tickButton = findViewById(R.id.floating_action_button);
        progressBar = findViewById(R.id.determinateBar);
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
    }
}
