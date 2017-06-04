package com.example.a1.conq.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.example.a1.conq.GameActivity;
import com.example.a1.conq.SingletonUser;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.AbstractMap;


public class CheckState extends AsyncTask<Void,Void,Void>
{
    GameActivity game;
    public CheckState(GameActivity gameActivity){
        super();
        game=gameActivity;
    }
    protected void onPreExecute() {
    }
    @Override
    protected Void doInBackground(Void... params) {
        try {
            URL url = new URL("http://10.0.3.2:8080/rest/game/getLastChanges?gameId="+
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

                Gson gson = new Gson();
                java.util.Map.Entry<Long, String> entry;
                entry = (gson.fromJson(response.toString(),  AbstractMap.SimpleEntry.class));
                Number key = entry.getKey();
                String value = entry.getValue();
                if(!value.equals("")){
                    game.conqArea(key.intValue(),value);
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
    protected void onPostExecute(Void result){

        game.newTurn();
    }
}