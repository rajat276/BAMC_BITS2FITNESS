package com.animesh.bamc;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ApiFitData extends AppCompatActivity {

    Button addActivity;
    AutoCompleteTextView activity,calories;
    TextView cancel,save;
    Dialog dialog;
    ArrayList<HashMap<String,String>> allActivities;
    HashMap<String,String> extraActivities;
    HashMap<String,String> caloriesBurnt;
    View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_fit_data);
        extraActivities=new HashMap<>();
        caloriesBurnt=new HashMap<>();

        addActivity=(Button)findViewById(R.id.addActivity);
        v=getLayoutInflater().inflate(R.layout.addactivitydialog,null);
        activity=(AutoCompleteTextView)v.findViewById(R.id.activity);
        calories=(AutoCompleteTextView)v.findViewById(R.id.calories);
        cancel=(TextView)v.findViewById(R.id.cancel);
        save=(TextView)v.findViewById(R.id.save);
        dialog=new Dialog(ApiFitData.this);
        dialog.setTitle("Enter an activity");
        dialog.setContentView(v);

        addActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dialog.show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCalories();
                dialog.cancel();
                activity.setText("");
                calories.setText("");
                activity.requestFocus();
            }
        });

    }

    public void addCalories(){
      extraActivities.put("Activity",activity.getText().toString());
        caloriesBurnt.put("Calories",calories.getText().toString());
    }

    @Override
    public void onBackPressed() {
        if(dialog.isShowing()){
            dialog.dismiss();
        }
        else {
            super.onBackPressed();
        }
    }
}
