package com.example.a1.conq.async;

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

public class CheckAnswer extends AsyncTask<Integer,String,String>
{
    String ans;
    GameActivity game;
    public CheckAnswer(GameActivity gameActivity,String a){
        super();
        ans=a;
        game=gameActivity;
    }
    protected void onPreExecute() {
    }
    @Override
    protected String doInBackground(Integer... params) {
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
                Log.d("GAME","ANSWE" + b);
                if(b){
                    game.conqArea(params[0].intValue(),SingletonUser.getSingletonUser().getName());
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
    protected void onPostExecute(String result){
        game.newTurn();
    }
}