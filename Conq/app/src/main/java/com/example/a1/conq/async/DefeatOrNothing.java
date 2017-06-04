package com.example.a1.conq.async;

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


public  class DefeatOrNothing extends AsyncTask<Void,String,String>
{

    private QuestionWrapper questionWrapper;
    private GameActivity game;
    private boolean deff;
    public DefeatOrNothing (GameActivity gameActivity){
        super();
        game=gameActivity;
        deff=false;
    }
    protected void onPreExecute() {
    }
    @Override
    protected String doInBackground(Void... params) {
        try {

            Log.d("GAME","execute DefeatOrNothing");
            URL url = new URL("http://10.0.3.2:8080/rest/game/checkMove?gameId="+
                    SingletonUser.getSingletonUser().getCurrentGame()+"&username="+
                    SingletonUser.getSingletonUser().getName());
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
                if(questionWrapper==null){
                    Log.d("GAME","NOTHING");
                }else{
                    Log.d("GAME","DEFF" + questionWrapper.getQuestion() );
                    deff=true;
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
    protected void onPostExecute(String result){
        if(deff) game.showQuestion(questionWrapper) ;
        else
            game.checkState();
    }
}
