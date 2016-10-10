package com.animesh.bamc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class LoginActivity extends AppCompatActivity {

    EditText userName,passWord;
    Button login;
    TextView register,forgotPass;
    ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setContentView(R.layout.activity_login);

        userName=(EditText)findViewById(R.id.username);
        passWord=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.login);
        register=(TextView)findViewById(R.id.Register);
        forgotPass=(TextView)findViewById(R.id.forgotPassword);


        Drawable emailDrawable = getResources().getDrawable(R.drawable.ic_mail_outline_black_24dp);
        emailDrawable= DrawableCompat.wrap(emailDrawable);
        DrawableCompat.setTint(emailDrawable,getResources().getColor(R.color.drawIcon));
        DrawableCompat.setTintMode(emailDrawable, PorterDuff.Mode.SRC_IN);
        userName.setCompoundDrawablesWithIntrinsicBounds(null,null,emailDrawable,null);

        Drawable passDrawable = getResources().getDrawable(R.drawable.ic_lock_outline_black_24dp);
        passDrawable= DrawableCompat.wrap(passDrawable);
        DrawableCompat.setTint(passDrawable,getResources().getColor(R.color.drawIcon));
        DrawableCompat.setTintMode(passDrawable, PorterDuff.Mode.SRC_IN);
        passWord.setCompoundDrawablesWithIntrinsicBounds(null,null,passDrawable,null);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,Register.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmit();
            }
        });


    }

    public boolean check(){
        boolean flag=true;
        if(userName.getText().toString().trim().isEmpty()){flag=false;userName.setError("This field cannot be empty");}
        if(passWord.getText().toString().trim().isEmpty()){flag=false;passWord.setError("This field cannot be empty");}
        return  flag;
    }

    public void onSubmit(){
        String email,pass;
        email=userName.getText().toString().trim();
        pass=passWord.getText().toString();
        if(check()){
            new AuthenticateUser(email,pass).execute();
        }
    }

    class AuthenticateUser extends AsyncTask<Void, Void, String> {
        String email,pass;
        AuthenticateUser(String email,String pass){
           this.email=email;
            this.pass=pass;
        }

        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder sb=new StringBuilder();
            try {
                URL url = new URL("http://bamc.netne.net/login.php");
                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                String data  = URLEncoder.encode("email", "UTF-8")
                        + "=" + URLEncoder.encode(email, "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8")
                        + "=" + URLEncoder.encode(pass, "UTF-8");
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
                Log.v("Hello",String.valueOf(e));
            }

            return sb.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mProgress.cancel();
            if(s.equalsIgnoreCase("Success")){
                Toast.makeText(getApplicationContext(),"Login Successfull",Toast.LENGTH_SHORT).show();
            }
            else if(s.equalsIgnoreCase("Invalid Password")){
                Toast.makeText(getApplicationContext(),"Invalid Password",Toast.LENGTH_SHORT).show();
            }
            else if(s.equalsIgnoreCase("Failed")){
                Toast.makeText(getApplicationContext(),"User doesn't exists",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this,ApiFitData.class));
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            super.onPreExecute();
            mProgress=new ProgressDialog(LoginActivity.this);
            mProgress.setMessage("Authenticating User");
            mProgress.setCancelable(false);
            mProgress.setIndeterminate(true);
            mProgress.setProgressStyle(R.style.ProgressDialog);
            mProgress.show();
        }
    }
}
