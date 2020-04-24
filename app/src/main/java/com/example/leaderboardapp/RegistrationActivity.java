package com.example.leaderboardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RegistrationActivity extends AppCompatActivity {
    FloatingActionButton tickButton;
    String currentFrag="personal";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        tickButton = findViewById(R.id.floating_action_button);
        PersonalDetailsFragment firstFragment = new PersonalDetailsFragment();

        // In case this activity was started with special instructions from an
        // Intent, pass the Intent's extras to the fragment as arguments
        firstFragment.setArguments(getIntent().getExtras());

        // Add the fragment to the 'fragment_container' FrameLayout
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, firstFragment).commit();

    }

    public void nextFragment(View view) {
        if(currentFrag.equals("personal"))
        {
            currentFrag="health";
            HealthDetailsFragment nextFragment = new HealthDetailsFragment();
            nextFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, nextFragment).commit();
        }
        else if(currentFrag.equals("health"))
        {
            currentFrag="other";
            OtherDetailsFragment nextFragment = new OtherDetailsFragment();
            nextFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, nextFragment).commit();
        }
        else {
            registerUser();
        }

    }

    private void registerUser() {
        Intent i = new Intent(getApplicationContext(),LauncherActivity.class);
        startActivity(i);
    }
}
