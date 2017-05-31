package com.example.a1.conq;


import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.a1.conq.entity.User;
import com.google.gson.Gson;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Registration extends AppCompatActivity  {
    Button ok;
    Button cancel;
    TextView pass;
    TextView confirmPass;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.registration);

        ok = (Button) findViewById(R.id.ok);
        View.OnClickListener oclNewGame = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPass()) {
                    new MyDownloadTask().execute();
                }

            }
        };
        ok.setOnClickListener(oclNewGame);

        pass = (TextView) findViewById(R.id.pass);
        confirmPass = (TextView) findViewById(R.id.confirmPass);
        login = (TextView) findViewById(R.id.login);

    }
    class MyDownloadTask extends AsyncTask<Void,String,String>
    {

        protected void onPreExecute() {
            //display progress dialog.

        }
        @Override
        protected String doInBackground(Void... params) {
            User newUser = new User(login.getText().toString(),pass.getText().toString());
            Gson gson = new Gson();
            String json = gson.toJson(newUser);
            try {
                URL url = new URL("http://10.0.3.2:8080/rest/user");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                try {
                    DataOutputStream localDataOutputStream = new DataOutputStream(urlConnection.getOutputStream());
                    localDataOutputStream.writeBytes(json);
                    localDataOutputStream.flush();
                    localDataOutputStream.close();
                    Log.d("NEO",urlConnection.getResponseCode()+"");
                    if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        Log.d("NEO","all OK, Neo");
                    } else {
                        Log.d("NEO","Wake up, Neo");
                    }
                }
                finally {
                    urlConnection.disconnect();
                }
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(String result)
        {

        }
    }
    private  boolean checkPass(){
        Log.d("pass",login.getText().toString());
        Log.d("pass",pass.getText().toString());
        Log.d("pass",confirmPass.getText().toString());

        if(!pass.getText().toString().equals(confirmPass.getText().toString())){
        AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
        builder.setTitle("Важное сообщение!")
                .setMessage("Покормите кота!")
                .setCancelable(false)
                .setNegativeButton("ОК, иду на кухню",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
        return false;
        }
        return  true;

    }
}
