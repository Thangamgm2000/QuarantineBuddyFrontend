package com.example.leaderboardapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class LauncherActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
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
}
