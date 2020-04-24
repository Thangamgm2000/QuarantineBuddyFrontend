package com.example.leaderboardapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class LauncherActivity extends AppCompatActivity {
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        check_for_permissions();
        dl = (DrawerLayout)findViewById(R.id.drawer_layout);
        t = new ActionBarDrawerToggle(this, dl,R.string.OpenDrawer, R.string.Close);
        dl.addDrawerListener(t);
        t.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setNavigation();
        RecyclerView dailyRecyclerView = (RecyclerView) findViewById(R.id.dailytasks);
        dailyRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        dailyRecyclerView.setLayoutManager(mLayoutManager);
        ArrayList<ChallengeTask> dailyTasks = new ArrayList<ChallengeTask>();
        dailyTasks.add(new ChallengeTask("Dalgona Coffee","Add later",
                "LifeSkills","Medium"));
        dailyTasks.add(new ChallengeTask("A short walk","Add later",
                "Physical ","Light"));
        TaskCardsAdapter dailyAdapter = new TaskCardsAdapter(dailyTasks);
        dailyRecyclerView.setAdapter(dailyAdapter);
        RecyclerView recoRecyclerView = (RecyclerView) findViewById(R.id.recommended);
        dailyRecyclerView.setHasFixedSize(true);
        LinearLayoutManager recoLayoutManager = new LinearLayoutManager(getApplicationContext());
        recoRecyclerView.setLayoutManager(recoLayoutManager);
        ArrayList<ChallengeTask> recoTasks = new ArrayList<ChallengeTask>();
        recoTasks.add(new ChallengeTask("A short walk","Add later",
                "Physical ","Light"));
        TaskCardsAdapter recoAdapter = new TaskCardsAdapter(recoTasks);
        recoRecyclerView.setAdapter(recoAdapter);

    }
    private void setNavigation()
    {

        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.leaderboard:
                        openLeaderboard();break;
                    case R.id.profile:
                        openProfile();break;
                    case R.id.testNotify:
                        sendnotifications();break;
                    case R.id.gpsmonitor:
                        openSensorMonitor();break;
                    default:
                        return true;
                }


                return true;

            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    public void openLeaderboard() {
        Intent i = new Intent(this,LeaderboardActivity.class);
        startActivity(i);
    }

    public void openSensorMonitor() {
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

    public void openProfile() {
        Intent i = new Intent(this,UserProfile.class);
        startActivity(i);
    }

    public void sendnotifications() {
        Intent i = new Intent(this,Notification_Activity.class);
        startActivity(i);
    }
}
