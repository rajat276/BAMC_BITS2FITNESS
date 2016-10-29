package com.animesh.bamc;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.animesh.bamc.Adaptors.LeaderBoardAdapter;
import com.animesh.bamc.Interface.Participant_Details;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
import java.util.ArrayList;
import java.util.HashMap;
import android.os.AsyncTask;
import java.util.LinkedHashMap;


/**
 * Created by Animesh on 10/19/2016.
 */
public class LeaderboardActivity extends AppCompatActivity {
    ArrayList<Participant_Details> ParticipantArray;
    Participant_Details pd;
    RecyclerView recyclerView;
    LeaderBoardAdapter ldAdapter;
    ProgressDialog mProgress;
    String name,email;
    String filename = "myLeaderBoard";
    TextView rank;
    LinkedHashMap<String,String> savedLeaderBoard = new LinkedHashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_leader_board);
        rank = (TextView)findViewById(R.id.rankUser);
        ParticipantArray = new ArrayList<Participant_Details>();
       /* pd=new Participant_Details("Rajat","1","2000");
        ParticipantArray.add(pd);
        pd=new Participant_Details("Monil","2","200");
        ParticipantArray.add(pd);
        pd=new Participant_Details("Maddy","3","0");
        ParticipantArray.add(pd);*/
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        // ldAdapter =new LeaderBoardAdapter(this,ParticipantArray);
        //recyclerView.setAdapter(ldAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Leaderboard");
        }
        name = getSharedPreferences("Name",MODE_PRIVATE).getString("name_of_user","");
        email= getSharedPreferences("EMAIL",MODE_PRIVATE).getString("email","");
        String rankUser  = getSharedPreferences("RANK",MODE_PRIVATE).getString("rank_of_user","");
        rank.setText(rankUser);

        Log.v("NAMENOW",name);
        setLeaderBoard();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    public void setLeaderBoard() {

        //If connected to internet update the leaderboard
        if(isOnline())
            new  syncLeaderBoard().execute();

        else {
            // Not connected to Internet
            // Read values from the file
            Log.v("NOTCONNECTED","TRUE");
            LinkedHashMap<String,String> data= new LinkedHashMap<>();
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
            int i=0;
            //create a template for card
            for (LinkedHashMap.Entry<String, String> entry : data.entrySet()) {
                String key = entry.getKey();
                Log.v("LEADERB2",key);
                String value = entry.getValue();
                Log.v("LEADERB2",value);
                pd = new Participant_Details(key, String.valueOf(i+1), value);
                i++;
                ParticipantArray.add(pd);
            }
            ldAdapter = new LeaderBoardAdapter(getApplicationContext(), ParticipantArray);
            Log.v("LEADERB3",String.valueOf(ParticipantArray.size()));
            recyclerView.setAdapter(ldAdapter);
            String rankUser  = getSharedPreferences("RANK",MODE_PRIVATE).getString("rank_of_user","");
            rank.setText(rankUser);

        }


    }

    public boolean isOnline() {
        Boolean isConnected = false;
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected())
            isConnected = true;
        return isConnected;
    }





    class syncLeaderBoard extends AsyncTask<Void, Void, LinkedHashMap<String, String>> {


        LinkedHashMap<String, String> data = new LinkedHashMap<>();

        @Override
        protected LinkedHashMap<String, String> doInBackground(Void... voids) {

            StringBuilder sb = new StringBuilder();
            try {
                URL url = new URL("http://bamc.netne.net/myrank.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String line = null;
                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                Log.v("VALUES", sb.toString());
                data = getJSONResults(sb.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.v("LEADER",String.valueOf(e));
            } catch (IOException e) {
                e.printStackTrace();
                Log.v("LEADER1",String.valueOf(e));
            }

            return data;
        }

        @Override
        protected void onPostExecute(LinkedHashMap<String, String> sValues) {

            mProgress.cancel();

            int i = 0;
            //Log.v("LEADERB1", String.valueOf(sValues.size()));
            for (LinkedHashMap.Entry<String, String> entry : sValues.entrySet()) {
                String key = entry.getKey();
                Log.v("LEADERB2",key);
                String value = entry.getValue();
                Log.v("LEADERB2",value);
                pd = new Participant_Details(key, String.valueOf(i+1), value);
                i++;
                ParticipantArray.add(pd);
            }
            ldAdapter = new LeaderBoardAdapter(getApplicationContext(), ParticipantArray);
            Log.v("LEADERB3",String.valueOf(ParticipantArray.size()));
            recyclerView.setAdapter(ldAdapter);
            String rankUser  = getSharedPreferences("RANK",MODE_PRIVATE).getString("rank_of_user","");
            rank.setText(rankUser);

            //save LinkedHashMap in File for offline access
            try {
                FileOutputStream fOut = new FileOutputStream(new File(getFilesDir(),filename));
                ObjectOutputStream outputStream= new ObjectOutputStream(fOut);
                outputStream.writeObject(sValues);
                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        protected void onPreExecute() {
            super.onPreExecute();
            mProgress = new ProgressDialog(LeaderboardActivity.this);
            mProgress.setMessage("Updating LeaderBoard");
            mProgress.setCancelable(false);
            mProgress.setIndeterminate(true);
            mProgress.setProgressStyle(R.style.ProgressDialog);
            mProgress.show();
        }

        public LinkedHashMap<String,String> getJSONResults(String s) {
            int myRank=0;
            LinkedHashMap<String, String> results = new LinkedHashMap<>();

            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(s);
                Log.v("Hii", String.valueOf(jsonArray.length()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if(String.valueOf(jsonObject.getString("email")).equalsIgnoreCase(email)){
                        myRank=i;
                        getSharedPreferences("RANK", MODE_PRIVATE).edit().putString("rank_of_user",String.valueOf(myRank+1)).commit();
                        Log.v("MYRANK", String.valueOf(myRank));
                    }
                    if(i<10)
                        results.put(String.valueOf(jsonObject.getString("name")), String.valueOf(jsonObject.getString("total")));
                    //savedLeaderBoard.put(String.valueOf(jsonObject.getString("name")), String.valueOf(jsonObject.getString("total")));
                    //calories.add(jsonObject.getString("total"));
                    Log.v("LEADERB", String.valueOf(results.size()));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.v("Error", e.getMessage());
                    Log.v("Error", String.valueOf(i));
                }
            }
            return results;
        }

    }
}
