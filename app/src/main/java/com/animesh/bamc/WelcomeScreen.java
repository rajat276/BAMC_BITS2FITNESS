package com.animesh.bamc;

import android.content.Intent;
import android.content.SharedPreferences;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class WelcomeScreen extends AppCompatActivity {

    TextView nameUser,eventStatus;
    Button cnt;
    String name;
    Calendar c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        Intent intent=getIntent();
        name=intent.getStringExtra("Name");
        nameUser=(TextView)findViewById(R.id.name);
        eventStatus =(TextView)findViewById(R.id.eventStatus);
        cnt=(Button)findViewById(R.id.cnt);

        //Get today's date
        c= java.util.Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        Date today = c.getTime();
        long todayInMillis = c.getTimeInMillis();

        //Get the event start date

        int year = 2016;
        int month = 10;
        int dayOfMonth = 27;
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month-1);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        Date dateSpecifiedStart = c.getTime();

       /*Get the event end date

        year = 2016;
        month = 11;
        dayOfMonth = 14;
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        Date dateSpecifiedEnd = c.getTime()*/

        if(!dateSpecifiedStart.before(today)){
            eventStatus.setVisibility(View.VISIBLE);
            cnt.setVisibility(View.GONE);
            cnt.setEnabled(false);
        }

        else {
            eventStatus.setVisibility(View.GONE);
            cnt.setVisibility(View.VISIBLE);
            cnt.setEnabled(true);
        }




        if(name!=""){
            nameUser.setText(name);
        }

        cnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent i=new Intent(WelcomeScreen.this,display_calorie.class);
                getSharedPreferences("Started", MODE_PRIVATE).edit().putBoolean("event_start",true).commit();
                Intent i = new Intent(WelcomeScreen.this,MainActivity.class);
                //i.putExtra("Name",name);
                startActivity(i);
                finish();
            }
        });



       // SharedPreferences name_of_user=getSharedPreferences("userName",0);
        //name_of_user.edit().putString("Name",name).commit();
        getSharedPreferences("Name", MODE_PRIVATE).edit().putString("name_of_user",name).commit();
    }
}
