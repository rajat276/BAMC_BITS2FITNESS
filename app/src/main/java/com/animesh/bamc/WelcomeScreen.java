package com.animesh.bamc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeScreen extends AppCompatActivity {

    TextView nameUser;
    Button cnt;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        Intent intent=getIntent();
        name=intent.getStringExtra("Name");
        nameUser=(TextView)findViewById(R.id.name);
        cnt=(Button)findViewById(R.id.cnt);
        if(name!=""){
            nameUser.setText(name);
        }

        cnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(WelcomeScreen.this,display_calorie.class);
                i.putExtra("Name",name);
                startActivity(i);
                finish();
            }
        });

       // SharedPreferences name_of_user=getSharedPreferences("userName",0);
        //name_of_user.edit().putString("Name",name).commit();
        getSharedPreferences("Name", MODE_PRIVATE).edit().putString("name_of_user",name).commit();
    }
}
