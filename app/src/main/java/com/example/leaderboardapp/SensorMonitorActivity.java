package com.example.leaderboardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SensorMonitorActivity extends AppCompatActivity {
    ArrayList<String> sensorvalues;
    double magnitude;
    Handler handler;
    Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorvalues = new ArrayList<String>();
        setContentView(R.layout.activity_sensor_monitor);
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnitude=0;
        SensorEventListener stepDetector = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent!= null){
                    float x_acceleration = sensorEvent.values[0];
                    float y_acceleration = sensorEvent.values[1];
                    float z_acceleration = sensorEvent.values[2];
                    String current_val=""+x_acceleration+","+y_acceleration;
                    double curr_mag = Math.pow(x_acceleration * x_acceleration + y_acceleration * y_acceleration, 0.5);
                    if(Math.abs(curr_mag -magnitude)>=1) {
                        sensorvalues.add(current_val);
                        magnitude= curr_mag;
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };
        sensorManager.registerListener(stepDetector, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        handler = new Handler();
        final int delay = 15000; //milliseconds

        handler.postDelayed(runnable = new Runnable(){
            public void run(){
                //do something
                sendSensorValues();
                sensorvalues.clear();
                Toast.makeText(getApplicationContext(),"Sensor values posted successfully",Toast.LENGTH_SHORT).show();
                handler.postDelayed(this, delay);

            }
        }, delay);
    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }

    public void sendSensorValues()
    {
        final JSONArray data= new JSONArray(sensorvalues);
        String url = "http://cc9dc015.ngrok.io/getSensorValues";
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
                params.put("userid", "1");
                params.put("sensorvalues",data.toString());
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(postRequest);
    }
}
