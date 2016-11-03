package com.bitsaa.bamc;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Register extends AppCompatActivity {

    EditText name,mobile,email,password;
    Button signUp;
    ProgressDialog mProgress;
    String emailUser,passUser,nameUser,phnoUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name=(EditText)findViewById(R.id.name);
        mobile=(EditText)findViewById(R.id.phone);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        signUp=(Button)findViewById(R.id.signup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarR);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Register");
        }
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmit();
            }
        });

        Drawable emailDrawable = getResources().getDrawable(R.drawable.ic_mail_outline_black_24dp);
        emailDrawable= DrawableCompat.wrap(emailDrawable);
        DrawableCompat.setTint(emailDrawable,getResources().getColor(R.color.drawIcon));
        DrawableCompat.setTintMode(emailDrawable, PorterDuff.Mode.SRC_IN);
        email.setCompoundDrawablesWithIntrinsicBounds(null,null,emailDrawable,null);

        Drawable passDrawable = getResources().getDrawable(R.drawable.ic_lock_outline_black_24dp);
        passDrawable= DrawableCompat.wrap(passDrawable);
        DrawableCompat.setTint(passDrawable,getResources().getColor(R.color.drawIcon));
        DrawableCompat.setTintMode(passDrawable, PorterDuff.Mode.SRC_IN);
        password.setCompoundDrawablesWithIntrinsicBounds(null,null,passDrawable,null);

        Drawable nameDrawable = getResources().getDrawable(R.drawable.ic_perm_identity_black_24dp);
        nameDrawable= DrawableCompat.wrap(nameDrawable);
        DrawableCompat.setTint(nameDrawable,getResources().getColor(R.color.drawIcon));
        DrawableCompat.setTintMode(nameDrawable, PorterDuff.Mode.SRC_IN);
        name.setCompoundDrawablesWithIntrinsicBounds(null,null,nameDrawable,null);

        Drawable mobileDrawable = getResources().getDrawable(R.drawable.ic_phone_black_24dp);
       mobileDrawable= DrawableCompat.wrap(mobileDrawable);
        DrawableCompat.setTint(mobileDrawable,getResources().getColor(R.color.drawIcon));
        DrawableCompat.setTintMode(mobileDrawable, PorterDuff.Mode.SRC_IN);
        mobile.setCompoundDrawablesWithIntrinsicBounds(null,null,mobileDrawable,null);
    }


    public boolean check(){
        boolean flag=true;
        if(name.getText().toString().trim().isEmpty()){flag=false;name.setError("This field cannot be empty");}
        if(password.getText().toString().trim().isEmpty()){flag=false;password.setError("This field cannot be empty");}
        if(email.getText().toString().trim().isEmpty()){flag=false;email.setError("This field cannot be empty");}
        if(mobile.getText().toString().trim().isEmpty()){flag=false;mobile.setError("This field cannot be empty");}
        return  flag;
    }

    public void onSubmit(){

        emailUser=email.getText().toString();
        passUser=password.getText().toString();
        nameUser=name.getText().toString();
        phnoUser=mobile.getText().toString();

        if(check()){
            //new RegisterUser(emailUser,passUser,nameUser,phnoUser).execute();
            isOnline();
        }
    }

    public void isOnline() {

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected())
            new RegisterUser(emailUser,passUser,nameUser,phnoUser).execute();
        else {
            Log.v("NOTCONN","TRUE");
            final Snackbar snackbar = Snackbar
                    .make(email, "No Internet Connection", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            isOnline();
                        }

                    });

            snackbar.show();
        }
    }




    public void  clear(){
        email.setText("");
        password.setText("");
        name.setText("");
        mobile.setText("");
        name.requestFocus();
    }

    class RegisterUser extends AsyncTask<Void, Void, String> {

        String emailUser,passUser,nameUser,phnoUser;

        RegisterUser(String emailUser,String passUser,String nameUser,String phnoUser){
            this.emailUser=emailUser;
            this.nameUser=nameUser;
            this.phnoUser=phnoUser;
            this.passUser=passUser;
        }

        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder sb=new StringBuilder();
            try {
                URL url = new URL("http://bamc.netne.net/registration.php");
                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                String data  = URLEncoder.encode("email", "UTF-8")
                        + "=" + URLEncoder.encode(emailUser, "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8")
                        + "=" + URLEncoder.encode(passUser, "UTF-8");
                data += "&" + URLEncoder.encode("number", "UTF-8")
                        + "=" + URLEncoder.encode(phnoUser, "UTF-8");
                data += "&" + URLEncoder.encode("name", "UTF-8")
                        + "=" + URLEncoder.encode(nameUser, "UTF-8");
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
            if(s.equalsIgnoreCase("User already exists"))
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
            else if(s.equalsIgnoreCase("Registration Failed"))
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
            else
            {
                Intent i=new Intent(Register.this,WelcomeScreen.class);
                // After successful registration, make "isfirstrun" to false
                getSharedPreferences("RUN", MODE_PRIVATE).edit().putBoolean("isfirstrun",false).commit();
                getSharedPreferences("EMAIL", MODE_PRIVATE).edit().putString("email",email.getText().toString().trim()).commit();
                i.putExtra("Name",s);
                startActivity(i);
                finish();
            }
            clear();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress=new ProgressDialog(Register.this);
            mProgress.setMessage("We are setting up your account !!");
            mProgress.setCancelable(false);
            mProgress.setIndeterminate(true);
            mProgress.setProgressStyle(R.style.ProgressDialog);
            mProgress.show();
        }
    }
}
