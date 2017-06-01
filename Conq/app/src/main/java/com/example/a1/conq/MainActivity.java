package com.example.a1.conq;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.example.a1.conq.entity.User;
import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    Button newGame;
    Button regAuto;

    String l;
    String p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        newGame = (Button) findViewById(R.id.newGame);
        View.OnClickListener oclNewGame = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SingletonUser.getSingletonUser().getName()!=null){
                    Intent intent = new Intent(MainActivity.this, GameActivity.class);
                    new FindGame(intent).execute();
                }
            }
        };
        newGame.setOnClickListener(oclNewGame);
        regAuto = (Button) findViewById(R.id.regauto);
        View.OnClickListener oclRegAuto = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Title");
                Context context =  MainActivity.this.getBaseContext();
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);
                final EditText inputLogin = new EditText(MainActivity.this);
                final EditText inputPass = new EditText(MainActivity.this);
                inputLogin.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                inputPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                layout.addView(inputLogin);
                layout.addView(inputPass);
                builder.setView(layout);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        l=inputLogin.getText().toString();
                        p=inputPass.getText().toString();
                        new Login().execute();
                    }
                });
                builder.setNeutralButton("reg", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, Registration.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

            }
        };
        regAuto.setOnClickListener(oclRegAuto);
    }
    class FindGame extends AsyncTask<Void,String,String>
    {
        Intent intent;
        public FindGame(Intent intent){
            super();
            this.intent=intent;
        }
        protected void onPreExecute() {

        }
        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL("http://10.0.3.2:8080/rest/game/getGame?username="+SingletonUser.getSingletonUser().getName());
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    InputStream response = urlConnection.getInputStream();
                    Scanner s = new Scanner(response).useDelimiter("\\A");
                    String result = s.hasNext() ? s.next() : "";
                    Log.d("MAIN","id game" + result);
                    int id = Integer.parseInt(result);
                    if(id>=0){
                        SingletonUser.getSingletonUser().setName(l);
                        SingletonUser.getSingletonUser().setCurrentGame(id);
                        startActivity(intent);
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

    class Login extends AsyncTask<Void,String,String>
    {

        protected void onPreExecute() {
            //display progress dialog.

        }
        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL("http://10.0.3.2:8080/rest/user/check?username="+l+"&password="+p);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    InputStream response = urlConnection.getInputStream();
                    Scanner s = new Scanner(response).useDelimiter("\\A");
                    String result = s.hasNext() ? s.next() : "";
                    if(result.equals("true")){
                        SingletonUser.getSingletonUser().setName(l);
                        Log.d("NEO",result+" "+l+" "+ p);
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
}
