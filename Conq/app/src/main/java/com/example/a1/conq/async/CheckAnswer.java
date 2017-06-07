package com.example.a1.conq.async;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.example.a1.conq.GameActivity;
import com.example.a1.conq.QuestionActivity;
import com.example.a1.conq.SingletonUser;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CheckAnswer extends AsyncTask<Void,String,String>
{
    String ans;
    QuestionActivity questionActivity;
    public CheckAnswer(QuestionActivity questionActivity,String a){
        super();
        ans=a;
        this.questionActivity=questionActivity;
    }
    protected void onPreExecute() {
    }
    @Override
    protected String doInBackground(Void... params) {
        try {
            URL url = new URL("http://10.0.3.2:8080/rest/game/sendAnswer?gameId="+
                    SingletonUser.getSingletonUser().getCurrentGame()+
                    "&username="+SingletonUser.getSingletonUser().getName()+"&answer="+ans);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                Gson gson = new Gson();
                boolean b = (gson.fromJson(response.toString(), Boolean.class));
                Log.d("GAME","ANSWER " + b);
                Intent intent= questionActivity.getIntent();
                intent.putExtra("result",b);
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
    protected void onPostExecute(String result){
        Log.d("TTTTTTTTTTTTTTTTTTT","on Post execute check answer");
        Toast.makeText(questionActivity.getApplicationContext(),"Answer checked",Toast.LENGTH_LONG).show();
        questionActivity.myFinish();
        Log.d("TTTTTTTTTTTTTTTTTTT","on Post execute qa.finish");
    }
}