package com.example.a1.conq.async;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import com.example.a1.conq.GameActivity;
import com.example.a1.conq.SingletonUser;
import com.example.a1.conq.entity.QuestionWrapper;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Attack extends AsyncTask<Integer,String,String>
{
    QuestionWrapper questionWrapper;
    GameActivity game;
    public Attack (GameActivity gameActivity){
        super();
        game=gameActivity;
    }
    protected void onPreExecute() {
    }
    @Override
    protected String doInBackground(Integer... params) {
        try {
            URL url = new URL("http://10.0.3.2:8080/rest/game/attackTerritory?gameId="+
                    SingletonUser.getSingletonUser().getCurrentGame()+"&username="+
                    SingletonUser.getSingletonUser().getName()+"&territoryNumber=" +params[0].intValue());
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
                questionWrapper = (gson.fromJson(response.toString(), QuestionWrapper.class));

                Log.d("GAME","Attack with question: "+questionWrapper.getQuestion() );
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
        game.showQuestion(questionWrapper);
    }
}