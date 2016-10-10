package com.animesh.bamc;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    TextView prevDate,currDate,nextDate,calories,profile,sync;
    Calendar c;
    int date,month,setDate;
    String filename = "myFile";
    String date_today="";
    int click =0 ;
    LinkedHashMap<String,Integer> value = new LinkedHashMap<>();
    ProgressDialog mProgress;
    String name,email ;

    int today_calorie = 9000; // upload the data from  Google Fit API


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_calorie);

        prevDate=(TextView)findViewById(R.id.previousDate);
        currDate=(TextView)findViewById(R.id.currentDate);
        nextDate=(TextView)findViewById(R.id.nextDate);
        calories=(TextView)findViewById(R.id.totalCalorie);

        profile=(TextView)findViewById(R.id.profile);
        sync=(TextView)findViewById(R.id.sync);

            //read email and name from Shared Preferences
        email = getSharedPreferences("EMAIL",MODE_PRIVATE).getString("email","");
        name=  getSharedPreferences("Name",MODE_PRIVATE).getString("name_of_user","");

            //Event Start Date in Time(Millis)
            Calendar startEvent = Calendar.getInstance();
            startEvent.set(Calendar.MONTH,10);
            startEvent.set(Calendar.DAY_OF_MONTH,8);
            long startTime= startEvent.getTimeInMillis();

            //Event End Date in Time(Millis)
            Calendar endEvent = Calendar.getInstance();
            endEvent.set(Calendar.MONTH,10);
            endEvent.set(Calendar.DAY_OF_MONTH,21);
            long endTime= endEvent.getTimeInMillis();


        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat("dd/MM");
        cal.add(Calendar.DAY_OF_YEAR, 0);
            long currTime = cal.getTimeInMillis();

            if(currTime == startTime ) prevDate.setEnabled(false);
            if(currTime >= endTime) nextDate.setEnabled(false);
        //currDate.setText(s.format(new Date(cal.getTimeInMillis())));
        date_today = s.format(new Date(cal.getTimeInMillis()));

            // used to set today's date
        setDate(click);
             // check if its the first time that the app is running
            Boolean firstTime = getSharedPreferences("DATA",MODE_PRIVATE).getBoolean("first_time",true);
            if(firstTime){
               value =  initialize_values();
                prevDate.setEnabled(false);
                nextDate.setEnabled(false);
                getSharedPreferences("DATA",MODE_PRIVATE).edit().putBoolean("first_time",false).commit();
            }
            else {
                // read linkedHashMap from File
                value = readCalories();
            }
            //update today's calories
            value.put(date_today,today_calorie);
            calories.setText(String.valueOf(today_calorie));
        prevDate.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view){
                click-=1;
                setDate(click);
            }
        });

        nextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click+=1;
                setDate(click);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayProfile();
            }
        });


        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                syncToDatabase();
            }
        });


    }

    public void displayProfile(){

    }

    public void displayHistory(){

    }

    public void syncToDatabase(){

        Gson gson = new Gson();
        String json = gson.toJson(value,LinkedHashMap.class);
        Log.v("VALUE_JSON!",String.valueOf(value.size()));
         Log.v("VALUE_JSON",json);
        new syncToServer(json).execute();
    }

    public void setDate(int days) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat("dd/MM");
        cal.add(Calendar.DAY_OF_YEAR, days);
        currDate.setText(s.format(new Date(cal.getTimeInMillis())));
        displayCalorie(s.format(new Date(cal.getTimeInMillis())));
    }


    public LinkedHashMap<String,Integer> initialize_values() {

        //if the app is running for first time initialize the LinkedHashMap
        LinkedHashMap<String,Integer> initialMap = new LinkedHashMap<>();
        initialMap.put("09/10",0);
        initialMap.put("10/10",0);
        initialMap.put("11/10",0);
        initialMap.put("12/10",0);
        initialMap.put("13/10",0);
        initialMap.put("14/10",0);
        initialMap.put("15/10",0);
        initialMap.put("16/10",0);
        initialMap.put("17/10",0);
        initialMap.put("18/10",0);
        initialMap.put("19/10",0);
        initialMap.put("20/10",0);
        initialMap.put("21/10",0);
        initialMap.put("22/10",0);

        //write the LinkedHashMap into File
        try {
            FileOutputStream fOut = new FileOutputStream(new File(getFilesDir(),filename));
            ObjectOutputStream outputStream= new ObjectOutputStream(fOut);
            outputStream.writeObject(initialMap);
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return initialMap;
    }

    public void displayCalorie(String key){
            if(value.containsKey(key)){
                calories.setText(String.valueOf(value.get(key)));
            }
            else {
                calories.setText(String.valueOf(0));
            }
    }

    public LinkedHashMap<String,Integer> readCalories(){
        LinkedHashMap<String,Integer> data= new LinkedHashMap<>();
        String path = getFilesDir()+ "/" + filename;
        try {

            FileInputStream fInp = new FileInputStream(path);
            ObjectInputStream inputStream = new ObjectInputStream(fInp);
           data = (LinkedHashMap)inputStream.readObject();
            inputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return data;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflowmenu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();
         if(id==R.id.action_sync){

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // when the app is closed update the LinkedHashMap and store it in the file
        if(value.containsKey(date_today)){
            value.put(date_today,today_calorie);
        }
        try {
            FileOutputStream fOut = new FileOutputStream(new File(getFilesDir(),filename));
            ObjectOutputStream outputStream= new ObjectOutputStream(fOut);
            outputStream.writeObject(value);
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    class syncToServer extends AsyncTask<Void, Void, String> {
       String json;
        syncToServer(String json){
            this.json =json;
        }

        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder sb=new StringBuilder();
            try {
                URL url = new URL("http://bamc.netne.net/calories_display.php");
                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                String data  =  URLEncoder.encode("map", "UTF-8")
                        + "=" + URLEncoder.encode(json, "UTF-8");
                data += "&" + URLEncoder.encode("name", "UTF-8")
                        + "=" + URLEncoder.encode(name, "UTF-8");
                data += "&" + URLEncoder.encode("email", "UTF-8")
                        + "=" + URLEncoder.encode(email, "UTF-8");


                httpURLConnection.setDoOutput(true);
                OutputStream wr = httpURLConnection.getOutputStream();
                BufferedWriter brwriter=new BufferedWriter(new OutputStreamWriter(wr,"UTF-8"));
                brwriter.write( data );
                brwriter.flush();
                brwriter.close();
                wr.close();
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

                String line = null;
                // Read Server Response
                while((line = reader.readLine()) != null)
                {
                    sb.append(line);
                    break;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return sb.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mProgress.cancel();
            if(s.equalsIgnoreCase("Yes") || s.equalsIgnoreCase("Yess"))
                Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_SHORT).show();
            else
                if(s.equalsIgnoreCase("No") || s.equalsIgnoreCase("Noo"))
                    Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                else {
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
                    Log.v("ShowError",s);
            }
        }

        protected void onPreExecute() {
            super.onPreExecute();
            super.onPreExecute();
            mProgress=new ProgressDialog(display_calorie.this);
            mProgress.setMessage("Syncing to server");
            mProgress.setCancelable(false);
            mProgress.setIndeterminate(true);
            mProgress.setProgressStyle(R.style.ProgressDialog);
            mProgress.show();
        }
    }



}
