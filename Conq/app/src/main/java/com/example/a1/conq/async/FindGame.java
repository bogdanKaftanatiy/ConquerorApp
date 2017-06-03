package com.example.a1.conq.async;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import com.example.a1.conq.GameActivity;
import com.example.a1.conq.MainActivity;
import com.example.a1.conq.SingletonUser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by 1 on 03.06.2017.
 */
public class FindGame extends AsyncTask<String,String,String>
{
    MainActivity  mainActivity;
    Intent intent;
    public FindGame( MainActivity  mainActivity){
        super();
        this.mainActivity=mainActivity;
        intent = new Intent(mainActivity, GameActivity.class);
    }
    protected void onPreExecute() {

    }
    @Override
    protected String doInBackground(String... params) {
        try {
            URL url = new URL("http://10.0.3.2:8080/rest/game/getGame?username="+ SingletonUser.getSingletonUser().getName());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream response = urlConnection.getInputStream();
                Scanner s = new Scanner(response).useDelimiter("\\A");
                String result = s.hasNext() ? s.next() : "";
                Log.d("MAIN","id game" + result);
                int id = Integer.parseInt(result);
                if(id>=0){
                    SingletonUser.getSingletonUser().setName(params[0]);
                    SingletonUser.getSingletonUser().setCurrentGame(id);
                    mainActivity.startActivity(intent);
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