package com.example.a1.conq.async;

import android.os.AsyncTask;
import android.util.Log;
import com.example.a1.conq.SingletonUser;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public  class Login extends AsyncTask<Void,String,String>
{
    String l,p;
    public Login(String login, String password){
        l=login;
        p=password;
    }
    protected void onPreExecute() {
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
                    Log.d("LogIn",result+" "+l+" "+ p);
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
