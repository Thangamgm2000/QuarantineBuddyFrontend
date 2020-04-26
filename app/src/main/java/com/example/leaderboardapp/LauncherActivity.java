package com.example.leaderboardapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class LauncherActivity extends AppCompatActivity  {
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private static ProgressDialog mProgressDialog;
    private RecyclerView recoRecyclerView,dailyRecyclerView;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private String[] timelist = {"A","M","E"};
    private ArrayList<ChallengeTask> recoTasks;
    Random generator;
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
        generator = new Random();
        dailyRecyclerView = (RecyclerView) findViewById(R.id.dailytasks);
        dailyRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        dailyRecyclerView.setLayoutManager(mLayoutManager);
        load_daily_challenges();
        recoRecyclerView = (RecyclerView) findViewById(R.id.recommended);
        recoRecyclerView.setHasFixedSize(true);
        LinearLayoutManager recoLayoutManager = new LinearLayoutManager(getApplicationContext());
        recoRecyclerView.setLayoutManager(recoLayoutManager);
        load_recommended_challenges();
        //recoRecyclerView.setAdapter(recoAdapter);

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
                    case R.id.health_task:
                        openSensorMonitor();break;
                    case R.id.quarantine_bot:
                        openchatBot();break;
                    case R.id.about_menu:
                        openabout();break;
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
        Intent i = new Intent(this,profile.class);
        startActivity(i);
    }

    public void sendnotifications() {
        Intent i = new Intent(this,Notification_Activity.class);
        startActivity(i);
    }
    public void openchatBot()
    {
        Intent i = new Intent(this,ChatbotActivity.class);
        startActivity(i);
    }
    public void openabout()
    {
        Intent i = new Intent(this,AboutActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout dTemp = (DrawerLayout)findViewById(R.id.drawer_layout);
        if(dTemp.isDrawerOpen(GravityCompat.START))
        {
            dTemp.closeDrawer(GravityCompat.START);
        }
        else
        super.onBackPressed();
    }

    @Override
    protected void onRestart() {
        SharedPreferences sf = getSharedPreferences("currenttask",MODE_PRIVATE);
        String status = sf.getString("taskStatus","NA");
        if (status.equals("completed"))
        {
            ArrayList<ChallengeTask> recoTasks = new ArrayList<ChallengeTask>();
            recoTasks.add(new ChallengeTask("Yoga","Add later",
                    "Healthcare","Medium",getString(R.string.dalgonaimageurl)));
            TaskCardsAdapter recoAdapter = new TaskCardsAdapter(recoTasks);
            recoRecyclerView.setAdapter(recoAdapter);
        }
        super.onRestart();
    }
    void load_daily_challenges()
    {
        String URLstring=  getString(R.string.server)+"getDailyChallenges";
        final ArrayList<ChallengeTask> dailyTasks = new ArrayList<ChallengeTask>();
        showSimpleProgressDialog(this, "Loading...","Fetching the contents",false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("strrrrr", ">>" + response);

                        try {

                            JSONObject obj = new JSONObject(response);
                            JSONArray dataArray  = obj.getJSONArray("tasks");

                            for (int i = 1; i < dataArray.length(); i++) {
                                if(i==2)
                                    continue;
                                Log.d("itemnumber",""+i);
                                JSONObject dataobj = dataArray.getJSONObject(i);
                                String description = dataobj.getString("desc");
                                String imageurl = dataobj.getString("imgurl");
                                ChallengeTask taskModel = new ChallengeTask(description,"","","",imageurl);
                                Log.d("imgurl",imageurl);
                                dailyTasks.add(taskModel);

                            }
                            removeSimpleProgressDialog();
                            TaskCardsAdapter dailyAdapter = new TaskCardsAdapter(dailyTasks);
                            dailyRecyclerView.setAdapter(dailyAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        removeSimpleProgressDialog();
                        Log.d("Error.Response", error.toString());
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 500;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public static void removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void showSimpleProgressDialog(Context context, String title,
                                                String msg, boolean isCancelable) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show(context, title, msg);
                mProgressDialog.setCancelable(isCancelable);
            }

            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }

        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void load_recommended_challenges()
    {
        String URLstring=  getString(R.string.server)+"getSuggestion";
        final ArrayList<ChallengeTask> dailyTasks = new ArrayList<ChallengeTask>();


        //showSimpleProgressDialog(this, "Loading...","Fetching the contents",false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLstring,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("strrrrr", ">>" + response);

                        try {

                            JSONObject obj = new JSONObject(response);
                            String task_id = obj.getString("taskId");
                            setTask(task_id);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        //removeSimpleProgressDialog();
                        Log.d("Error.Response", error.toString());
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();

                params.put("uname", "thangam");
                int randomIndex = generator.nextInt(timelist.length);
                params.put("time",timelist[randomIndex]);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 500;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void setTask(final String task_id) {
        String URLstring=  getString(R.string.server)+"getTaskDetails";
        recoTasks = new ArrayList<ChallengeTask>();
        //showSimpleProgressDialog(this, "Loading...","Fetching the contents",false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLstring,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("strrrrr", ">>" + response);

                        try {

                            JSONObject obj = new JSONObject(response);
                            String taskName = obj.getString("taskName");
                            String taskDesc = obj.getString("taskDescription");
                            String taskCat = obj.getString("taskCategory");
                            String taskIntensity = obj.getString("taskRating");
                            String imageurl = obj.getString("taskImageUrl");
                            ChallengeTask taskModel = new ChallengeTask(taskName,taskDesc,taskCat,taskIntensity,imageurl);
                            Log.d("imgurl",imageurl);
                            recoTasks.add(taskModel);
                            //removeSimpleProgressDialog();
                            TaskCardsAdapter recoAdapter = new TaskCardsAdapter(recoTasks);
                            recoRecyclerView.setAdapter(recoAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        removeSimpleProgressDialog();
                        Log.d("Error.Response", error.toString());
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();

                params.put("TaskId", task_id);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 500;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



}
