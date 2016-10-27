package com.animesh.bamc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class Disclaimer extends AppCompatActivity {

    WebView disclaimer;
    Button iAgree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disclaimer);

        disclaimer= (WebView)findViewById(R.id.disclaimer);
        iAgree = (Button)findViewById(R.id.agree);

        getSupportActionBar().setTitle("Disclaimer");


        String text =  "<html><body style='margin:20;padding:60dp;'>"
              + "<p align=\"justify\" style='font-size:16;line-height:1.4'>"
                         + getString(R.string.disclaimer)
                         + "</p> "
                        + "</body></html>";

        disclaimer.loadData(text, "text/html", "utf-8");
        iAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Disclaimer.this,Honor_Code.class));
                finish();
            }
        });
    }
}


