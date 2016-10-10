package com.animesh.bamc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Splash1 extends AppCompatActivity {

    TextView p1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash1);
        //Storing the information whether the user has used the app for the first time in Shared Preferences
        Boolean hasRun = getSharedPreferences("RUN",MODE_PRIVATE).getBoolean("isfirstrun",true);
        if(hasRun) {
            //app is running for the first time
            p1 = (TextView) findViewById(R.id.page1);

            p1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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
            //Pass the name of the user in the intent
            Intent i = new Intent(Splash1.this,display_calorie.class);
            i.putExtra("Name",name);
            startActivity(i);
            finish();
        }

    }
}
