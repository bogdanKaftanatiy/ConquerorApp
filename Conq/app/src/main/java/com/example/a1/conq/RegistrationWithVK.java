package com.example.a1.conq;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.a1.conq.entity.User;
import com.google.gson.Gson;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.*;
import com.vk.sdk.api.model.VKList;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegistrationWithVK extends AppCompatActivity {
    Button ok;
    TextView passVK;
    TextView confirmPassVK;
    String login;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        login="";
        setContentView(R.layout.regestrationwithvk);

        ok = (Button) findViewById(R.id.okvk);
        View.OnClickListener oclNewGame = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPass()) {
                    new RegistrationWithVK.MyDownloadTask().execute();
                }

            }
        };
        ok.setOnClickListener(oclNewGame);
        Button cancel = (Button) findViewById(R.id.cancelvk);
        View.OnClickListener oclCancel = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
        cancel.setOnClickListener(oclCancel);
        passVK = (TextView) findViewById(R.id.passwordVK);
        confirmPassVK = (TextView) findViewById(R.id.confirmPassVK);
        VKSdk.login(this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                final VKRequest request =   VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, "first_name, last_name"));
                request.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);
                        VKList list  = (VKList) response.parsedModel;
                        login =login+list.get(0)+"_";
                        login =login+list.get(1);
                    }
                });
                Toast.makeText(getApplicationContext(),"Please, enter password",Toast.LENGTH_LONG).show();
            }
            @Override
            public void onError(VKError error) {
                Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    class MyDownloadTask extends AsyncTask<Void,String,String>
    {
        protected void onPreExecute() {
        }
        @Override
        protected String doInBackground(Void... params) {
            User newUser = new User(login,passVK.getText().toString());
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
                    Log.d("REGISTRATION",urlConnection.getResponseCode()+"");
                    if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        Toast.makeText(getApplicationContext(),"R S, your login: "+login,Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(),"R F",Toast.LENGTH_LONG).show();
                    }
                }
                finally {
                    urlConnection.disconnect();
                }
            }
            catch (IOException e) {
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
        if(!passVK.getText().toString().equals(confirmPassVK.getText().toString())){
            AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationWithVK.this);
            builder.setTitle("ERROR!")
                    .setMessage("pass<>pass")
                    .setCancelable(false)
                    .setNegativeButton("OK",
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

