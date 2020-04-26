package com.example.leaderboardapp;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class profile extends AppCompatActivity {

    TextView name,country,score,current;
    public static final int GET_FROM_GALLERY = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilexml);
        name=findViewById(R.id.tv_name);
        country=findViewById(R.id.country);
        score=findViewById(R.id.scorepoints);
        current=findViewById(R.id.current);
        // Name from database
        name.setText("shreevijay");
        // Country from Database
        country.setText("India");
        //score from database
        score.setText("0");
        //current activity
        current.setText("Dalgona Coffee");


    }
    public void editpro(View view){
        //image updating and sending to database
        startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
    }
    public void logoff(View view){
        //Normal intent to home page
        //Intent i = new Intent(profile.this,home.class);
        //        startActivity(i);
    }
    public void cact(View view){
        //if any activity to show a new page of current acitivity intent can be used
        Intent i = new Intent(this,TasksActivity.class);
        startActivity(i);
    }
    public void comact(View view){
        //Normal intent to completed activities page
        //Intent i = new Intent(profile.this,comactivities.class);
        //        startActivity(i);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                CircleImageView imageView = findViewById(R.id.profile_image);
                imageView.setImageBitmap(bitmap);
                //imageView.setBackgroundResource(R.drawable.circle_border);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream .toByteArray();
                String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                setprofilepic(encoded);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setprofilepic(final String encoded) {
        String url = getString(R.string.server)+"setProfilePic";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();

                params.put("Username", "thangam");
                params.put("IconUrl","data:image/png;base64,"+encoded);
                Log.d("IconUrl","data:image/png;base64,"+encoded);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(postRequest);
    }


}

