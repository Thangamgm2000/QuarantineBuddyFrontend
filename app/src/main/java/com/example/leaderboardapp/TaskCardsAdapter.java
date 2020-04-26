package com.example.leaderboardapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

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
                        SharedPreferences sf = context.getSharedPreferences("taskCommunicator",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sf.edit();
                        ChallengeTask current = taskList.get(0);
                        editor.putString("taskName",current.taskName);
                        editor.putString("taskDesc",current.taskDescription);
                        editor.putString("taskCat",current.taskCategory);
                        editor.putString("imgUrl",current.imageUrl);
                        editor.putString("rating",current.taskIntensity);
                        editor.apply();
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

        byte[] decodedString = Base64.decode(currentTask.imageUrl.split(",",2)[1], Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.taskIcon.setImageBitmap(decodedByte);
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
            taskIcon = (ImageView) itemLayoutView.findViewById(R.id.taskImage);
        }
    }
}
