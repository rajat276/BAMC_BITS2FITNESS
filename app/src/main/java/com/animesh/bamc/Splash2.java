package com.animesh.bamc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.animesh.bamc.Interface.OnSwipeTouchListener;
import com.squareup.picasso.Picasso;

public class Splash2 extends AppCompatActivity {

    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);

        //getSupportActionBar().setTitle("About the App");
        Picasso.with(Splash2.this).load(R.drawable.boxing_final).fit().centerInside().into((ImageView)findViewById(R.id.ivsplash2));
        relativeLayout=(RelativeLayout)findViewById(R.id.s2relativeview);
        relativeLayout.setOnTouchListener(new OnSwipeTouchListener(Splash2.this){
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                startActivity(new Intent(Splash2.this,Disclaimer.class));
                finish();
            }
        });

    }
}
