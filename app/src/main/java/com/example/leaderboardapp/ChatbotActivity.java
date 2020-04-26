package com.example.leaderboardapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatbotActivity extends AppCompatActivity {

    private ListView mListView;
    private FloatingActionButton mButtonSend;
    private EditText mEditTextMessage;
    private ImageView mImageView;
    private HashMap<String,String> answers;
    private ChatMessageAdapter mAdapter;@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);
        answers = new HashMap<String, String>();
        load_answers();
        mListView = (ListView) findViewById(R.id.listView);
        mButtonSend = (FloatingActionButton) findViewById(R.id.btn_send);
        mEditTextMessage = (EditText) findViewById(R.id.et_message);
        mImageView = (ImageView) findViewById(R.id.iv_image);
        mAdapter = new ChatMessageAdapter(this, new ArrayList<ChatMessage>());
        mListView.setAdapter(mAdapter);

//code for sending the message
        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                String message = mEditTextMessage.getText().toString();
                sendMessage(message);
                mEditTextMessage.setText("");
                mListView.setSelection(mAdapter.getCount() - 1);
            }
        });
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    private void sendMessage(String message) {
        ChatMessage chatMessage = new ChatMessage(message, true, false);
        mAdapter.add(chatMessage);
        //respond as Helloworld
        mimicOtherMessage(answers.getOrDefault(message.toLowerCase(),"Sorry I didn't get you. I will try to improve"));
    }

    private void mimicOtherMessage(String message) {
        ChatMessage chatMessage = new ChatMessage(message, false, false);
        mAdapter.add(chatMessage);
    }

    private void sendMessage() {
        ChatMessage chatMessage = new ChatMessage(null, true, true);
        mAdapter.add(chatMessage);

        mimicOtherMessage();
    }

    private void mimicOtherMessage() {
        ChatMessage chatMessage = new ChatMessage(null, false, true);
        mAdapter.add(chatMessage);
    }
    private void load_answers() {
        answers.put("what are the symptoms of corona virus","common symptoms include fever, tiredness, dry cough");
        answers.put("i am bored. what can i do?","Check out the activities in our app");
        answers.put("i am scared. what do i do?","Don’t worry, with all the necessary precations you are completely safe.");
        answers.put("where can i find official info about corona virus","https://www.who.int/emergencies/diseases/novel-coronavirus-2019");
        answers.put("what is social distancing?","Social distancing, also called “physical distancing,” means keeping space between yourself and other people outside of your home.");
        answers.put("how to practice social distancing?","1. Stay at least 6 feet (2 meters) from other people.\n 2. Do not gather in groups\n 3.Stay out of crowded places and avoid mass gatherings. ");
    }
}
