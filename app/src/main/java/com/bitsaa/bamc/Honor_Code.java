package com.bitsaa.bamc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class Honor_Code extends AppCompatActivity {


    WebView honorCode;
    Button iAgree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_honor__code);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarH);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Honor code");
        }
        //getSupportActionBar().setTitle("Honor Code");

        honorCode = (WebView)findViewById(R.id.honorCode);
        iAgree = (Button)findViewById(R.id.agree);

        String text =  "<html><body style='margin:20;padding:60dp;'>"
                + "<p align=\"justify\" style='font-size:16;line-height:1.4'>"
                + getString(R.string.honorcode)
                + "</p> "
                + "</body></html>";

        honorCode.loadData(text, "text/html", "utf-8");
        iAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Honor_Code.this,Register.class));
                finish();
            }
        });

    }
}
