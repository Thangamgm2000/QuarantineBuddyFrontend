package com.example.leaderboardapp;

public class ChallengeTask {
    String taskName,taskDescription,taskCategory,taskIntensity;
    int imageId;
    public ChallengeTask(String taskName,String taskDescription, String taskCategory,String taskIntensity,int image_Id)
    {
        this.taskName=taskName;
        this.taskDescription=taskDescription;
        this.taskCategory=taskCategory;
        this.taskIntensity = taskIntensity;
        this.imageId=image_Id;
    }

}
