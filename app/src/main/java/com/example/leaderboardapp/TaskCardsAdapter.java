package com.example.leaderboardapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskCardsAdapter extends RecyclerView.Adapter<TaskCardsAdapter.ViewHolder> {
    ArrayList<ChallengeTask> taskList;
    Context  context;
    public TaskCardsAdapter(ArrayList<ChallengeTask> taskList )
    {
        this.taskList = taskList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.task_cardview, parent,false);
        context = parent.getContext();
        itemLayoutView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(context,TasksActivity.class);
                        context.startActivity(i);
                    }
                }
        );
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChallengeTask currentTask = taskList.get(position);
        holder.nameText.setText(currentTask.taskName);
        if(!currentTask.taskCategory.equals(""))
        holder.catText.setText("Category: "+currentTask.taskCategory);
        else
            holder.catText.setText("");
        if(!currentTask.taskIntensity.equals(""))
        holder.intenText.setText("Intensity: "+currentTask.taskIntensity);
        else
            holder.intenText.setText("");
        holder.taskIcon.setImageResource(currentTask.imageId);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameText,catText,intenText;
        public ImageView taskIcon;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            nameText = (TextView) itemLayoutView.findViewById(R.id.taskName);
            catText = (TextView) itemLayoutView.findViewById(R.id.taskCategory);
            intenText = (TextView) itemLayoutView.findViewById(R.id.taskIntensity);
            taskIcon = (ImageView) itemLayoutView.findViewById(R.id.taskImage);
        }
    }
}
