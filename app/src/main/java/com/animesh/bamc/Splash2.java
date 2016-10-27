package com.animesh.bamc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Splash2 extends AppCompatActivity {

    TextView p2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);

        getSupportActionBar().setTitle("About the App");

        p2=(TextView)findViewById(R.id.page2);

        p2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Splash2.this,Disclaimer.class));
                finish();
            }
        });
    }
}
