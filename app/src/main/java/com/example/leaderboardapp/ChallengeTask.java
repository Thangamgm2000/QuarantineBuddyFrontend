package com.example.leaderboardapp;

public class ChallengeTask {
    String taskName,taskDescription,taskCategory,taskIntensity;
    String imageUrl;
    public ChallengeTask(String taskName,String taskDescription, String taskCategory,String taskIntensity,String imageUrl)
    {
        this.taskName=taskName;
        this.taskDescription=taskDescription;
        this.taskCategory=taskCategory;
        this.taskIntensity = taskIntensity;
        this.imageUrl=imageUrl;
    }

}
