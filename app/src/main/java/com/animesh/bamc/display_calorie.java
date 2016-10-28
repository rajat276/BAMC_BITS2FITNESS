package com.animesh.bamc;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StreamCorruptedException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class display_calorie extends AppCompatActivity {

    TextView prevDate, currDate, nextDate, calories;
    Calendar c;
    int date, month, setDate;
    String date_today = "";
    int click = 0;
    LinkedHashMap<String, Integer> value;
    ProgressDialog mProgress;
    String name, email;
    RecyclerView recyclerView;
    int today_calorie = 0;
    HistoryAdapter historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);
/*
        prevDate = (TextView) findViewById(R.id.previousDate);
        currDate = (TextView) findViewById(R.id.currentDate);
        nextDate = (TextView) findViewById(R.id.nextDate);
        calories = (TextView) findViewById(R.id.totalCalorie);*/
        recyclerView = (RecyclerView) findViewById(R.id.historyRecycler);
        // ldAdapter =new LeaderBoardAdapter(this,ParticipantArray);
        //recyclerView.setAdapter(ldAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarhis);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("History");
        }
        //Get the value form the Home Page
        value = MainActivity.value;
        historyAdapter=new HistoryAdapter(display_calorie.this);
        recyclerView.setAdapter(historyAdapter);
        /*Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat("dd/MM");
        cal.add(Calendar.DAY_OF_YEAR, 0);
        date_today = s.format(new Date(cal.getTimeInMillis()));

        // used to set today's date
        //setDate(click);
        // check if its the first time that the app is running
        today_calorie = value.get(date_today);
        //update today's calories
        value.put(date_today, today_calorie);*/

        /*calories.setText(String.valueOf(today_calorie));
        prevDate.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                click -= 1;
                setDate(click);
            }
        });

        nextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click += 1;
                setDate(click);
            }
        });*/
    }




    public void setDate(int days) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat("dd/MM");
        cal.add(Calendar.DAY_OF_YEAR, days);
        currDate.setText(s.format(new Date(cal.getTimeInMillis())));
        displayCalorie(s.format(new Date(cal.getTimeInMillis())));
    }


    public void displayCalorie(String key) {
        if (value.containsKey(key)) {
            calories.setText(String.valueOf(value.get(key)));
        } else {
            calories.setText(String.valueOf(0));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflowmenu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

}




