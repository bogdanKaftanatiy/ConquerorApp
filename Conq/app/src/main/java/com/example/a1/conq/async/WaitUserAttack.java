package com.example.a1.conq.async;


import android.os.AsyncTask;
import android.os.Handler;
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

public class WaitUserAttack extends AsyncTask<Void,Void,Void>
{
    GameActivity game;
    public WaitUserAttack (GameActivity gameActivity){
        super();
        game=gameActivity;
    }
    protected void onPreExecute() {
    }
    @Override
    protected Void doInBackground(Void... params) {
        try {

            URL url = new URL("http://10.0.3.2:8080/rest/game/waitUserAttack?gameId="+
                    SingletonUser.getSingletonUser().getCurrentGame());
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



                Log.d("GAME","enemy attacked");
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
    protected void onPostExecute(Void result){
        game.enemyAttack();
    }
}