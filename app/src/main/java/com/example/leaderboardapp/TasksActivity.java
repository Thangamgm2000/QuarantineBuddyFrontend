package com.example.leaderboardapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class TasksActivity extends AppCompatActivity {
    RatingBar ratingBar;
    SharedPreferences sf,sfcurrenttask;
    SharedPreferences.Editor sfeditor;
    Button acceptButton;
    ImageView taskIcon;
    TextView nameText,catText,decText;
    long minTime,restTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        ratingBar = findViewById(R.id.task_rating);
        ratingBar.setRating(4);
        minTime= 60 * 1000;
        restTime = 30*1000;
        acceptButton = findViewById(R.id.acceptTask);
        taskIcon = findViewById(R.id.task_icon);
        nameText = findViewById(R.id.taskName);
        catText = findViewById(R.id.taskCategory);
        decText = findViewById(R.id.taskdesc);
        setcontents();
        sf = getSharedPreferences("taskStatus",MODE_PRIVATE);
        sfcurrenttask = getSharedPreferences("currenttask",MODE_PRIVATE);
        String actStatus = sf.getString("taskStatus","completed");
        if(actStatus.equals("completed"))
        {
            acceptButton.setText("Accept");
        }
        else
            {
                acceptButton.setText("Complete");
            }
    }



    public void startOrEndTask(View view) {
        String actStatus = sf.getString("taskStatus","completed");
        sfeditor = sf.edit();
        if(actStatus.equals("completed"))
        {
            acceptButton.setText("Complete");
            sfeditor.putString("taskStatus","onProgress");
            long curtime= System.currentTimeMillis();
            long remTime = curtime+minTime;
            sfeditor.putLong("remTime",remTime);
            sfeditor.apply();
            SharedPreferences.Editor edit = sfcurrenttask.edit();
            edit.putString("currenttask","Dalgona coffee challenge");
            edit.apply();
        }
        else
        {
            long curtime = System.currentTimeMillis();
            long remTime = sf.getLong("remTime",0);
            if(curtime>=remTime)
            {
                display_rating();
                acceptButton.setText("Accept");
                sfeditor.putString("taskStatus","completed");
                sfeditor.remove("remTime");
                sfeditor.apply();
            }
            else
            {
                AlertDialog.Builder alertdialog = new AlertDialog.Builder(this)
                        .setTitle("Task completion")
                        .setMessage("Minimum estimated time of 10 mins is needed for this task. Remaining time is 5 mins. Complete the task and come back later" +
                                " or skip this task if you are not interested in this")

        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertdialog.show();
            }
        }
    }
    private void display_rating()
    {
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.ratings_popup, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = false; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation((View) acceptButton, Gravity.CENTER, 0, 0);
        Button rateButton = popupView.findViewById(R.id.Ratebutton);
        rateButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                closeActivity();
            }
        }));
    }
    private void closeActivity()
    {
        this.finish();
    }

    public void skipTask(View view) {
        closeActivity();
    }
    private void setcontents() {
        SharedPreferences sf1 = getSharedPreferences("taskCommunicator",MODE_PRIVATE);
        nameText.setText(sf1.getString("taskName","NA"));
        catText.setText(sf1.getString("taskCat","NA"));
        decText.setText(sf1.getString("taskDesc","NA"));
        byte[] decodedString = Base64.decode(sf1.getString("imgUrl",",.").split(",",2)[1], Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        taskIcon.setImageBitmap(decodedByte);
    }
}
