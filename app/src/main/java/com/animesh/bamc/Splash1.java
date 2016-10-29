package com.animesh.bamc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.animesh.bamc.Interface.OnSwipeTouchListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;

public class Splash1 extends AppCompatActivity {

    RelativeLayout relativeLayout;
    Calendar c;
    boolean started = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash1);


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
        int dayOfMonth = 31;
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month-1);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        Date dateSpecifiedStart = c.getTime();

       //Get the event end date

        /*year = 2016;
        month = 11;
        dayOfMonth = 1;
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        Date dateSpecifiedEnd = c.getTime();*/

        Log.v("TODAY",String.valueOf(today));
        Log.v("STARTDATE",String.valueOf(dateSpecifiedStart));
        if(today.before(dateSpecifiedStart)){
           started = false;
            Log.v("CHECKEVENT",String.valueOf(started));
        }

        else {
           started = true;
            Log.v("CHECKEVENT",String.valueOf(started));
        }



        //Storing the information whether the user has used the app for the first time in Shared Preferences
        Boolean hasRun = getSharedPreferences("RUN",MODE_PRIVATE).getBoolean("isfirstrun",true);
        if(hasRun) {
            //app is running for the first time
            Picasso.with(Splash1.this).load(R.drawable.basketball_final).fit().centerInside().into((ImageView)findViewById(R.id.ivsplash1));
            relativeLayout=(RelativeLayout)findViewById(R.id.s1relativeView);
            relativeLayout.setOnTouchListener(new OnSwipeTouchListener(Splash1.this){
                @Override
                public void onSwipeLeft() {
                    super.onSwipeLeft();
                    startActivity(new Intent(Splash1.this, Splash2.class));
                    finish();
                }
            });
        }
        else {
            //app has already started for the first time
            //Start activity "display_calorie"

            //Read the name of the user from shared Preferences
            String name = getSharedPreferences("Name",MODE_PRIVATE).getString("name_of_user","");
            Boolean bool = getSharedPreferences("Started",MODE_PRIVATE).getBoolean("event_start",false);
            Intent i;
            //Pass the name of the user in the intent
            if(started) {
                        if(bool)
                        i = new Intent(Splash1.this, MainActivity.class);
                else
                            i = new Intent(Splash1.this,WelcomeScreen.class);
            } else
                i = new Intent(Splash1.this,WelcomeScreen.class);
            i.putExtra("Name",name);
            startActivity(i);
            finish();
        }

    }
}
