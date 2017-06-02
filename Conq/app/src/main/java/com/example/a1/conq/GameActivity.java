package com.example.a1.conq;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;

import com.example.a1.conq.GameObject.GrafObject;
import com.example.a1.conq.GameObject.Map;
import com.example.a1.conq.entity.QuestionWrapper;
import com.example.a1.conq.entity.User;
import com.google.gson.Gson;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;


public class GameActivity extends AppCompatActivity implements View.OnTouchListener {
    int width;
    int height;
    Map map;
    ImageView imageMap;
    int idGame;
    TextView redUser;
    TextView greenUser;
    TextView blueUser;
    TextView curr;
    int att;
    ArrayList<GrafObject> redHolding;
    ArrayList<GrafObject> greenHolding;
    ArrayList<GrafObject> blueHolding;

    ArrayList<GrafObject> areas;

    ArrayList<String> progress;

    Button a1 ;
    Button a2 ;
    Button a3 ;
    Button a4;
    String answer;
    boolean deff;
    int currentStep;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        att=-1;
        answer="";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        currentStep=0;
        progress=new ArrayList<>();
        map = new Map();
        map.setHeight(1080);
        map.setWidth(1920);
        deff =false;
        curr = (TextView) findViewById(R.id.current);
        ArrayList<ImageView> imageViews=new ArrayList<>();
        imageViews.add((ImageView)findViewById(R.id.area1));
        imageViews.add((ImageView)findViewById(R.id.area2));
        imageViews.add((ImageView)findViewById(R.id.area3));
        imageViews.add((ImageView)findViewById(R.id.area4));
        imageViews.add((ImageView)findViewById(R.id.area5));
        imageViews.add((ImageView)findViewById(R.id.area6));
        imageViews.add((ImageView)findViewById(R.id.area7));
        imageViews.add((ImageView)findViewById(R.id.area8));
        imageViews.add((ImageView)findViewById(R.id.area9));
        imageViews.add((ImageView)findViewById(R.id.area10));
        imageViews.add((ImageView)findViewById(R.id.area11));
        imageViews.add((ImageView)findViewById(R.id.area12));
        imageViews.add((ImageView)findViewById(R.id.area13));
        imageViews.add((ImageView)findViewById(R.id.area14));
        imageViews.add((ImageView)findViewById(R.id.area15));
        map.setChipsArea(imageViews);
        imageMap = (ImageView) findViewById(R.id.map);
        imageMap.setOnTouchListener(this);
        Display display = getWindowManager().getDefaultDisplay();
        width = display.getWidth();  // deprecated
        height = display.getHeight();
        Log.d("123",width+" "+height);
        idGame=SingletonUser.getSingletonUser().getCurrentGame();
        redUser = (TextView) findViewById(R.id.red);
        greenUser = (TextView) findViewById(R.id.green);
        blueUser = (TextView) findViewById(R.id.blue);
        new StartGame().execute();
        redHolding=new ArrayList<>();
        greenHolding=new ArrayList<>();
        blueHolding=new ArrayList<>();
        areas=map.getAreas();
        setPositionView();
        new GetSeq().execute();
        a1 = (Button) findViewById(R.id.answer1);
        a2 = (Button) findViewById(R.id.answer2);
        a3 = (Button) findViewById(R.id.answer3);
        a4 = (Button) findViewById(R.id.answer4);
        View.OnClickListener sendAnswer= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b =(Button) findViewById(v.getId());
                new CheckAnswer(b.getText().toString()).execute();
                LinearLayout ll = (LinearLayout) findViewById(R.id.questionTable);
                ll.setVisibility(View.INVISIBLE);
            }
        };
        a1.setOnClickListener(sendAnswer);
        a2.setOnClickListener(sendAnswer);
        a3.setOnClickListener(sendAnswer);
        a4.setOnClickListener(sendAnswer);

        Log.d("GAME", "START");
    }
    private void setPositionView(){
        FrameLayout fl = (FrameLayout) findViewById(R.id.fl);
        for(int i=0;i<areas.size();i++){
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams((int)(100d/map.getHeight()*height),
                    (int) (100d/map.getWidth()*width));
            Log.d("1234",""+(int)(((areas.get(i).getCenter().getX() -50d)/map.getHeight())*height));
            params.leftMargin = (int)(((areas.get(i).getCenter().getX() -50d)/map.getHeight())*height);
            params.topMargin  = (int)((areas.get(i).getCenter().getY()-50d)/map.getWidth()*width);
            fl.removeView(areas.get(i).getChip());
            fl.addView(areas.get(i).getChip(), params);
        }
    }
    private  void playGame(){
        Log.d("GAME","play game d ot n");
        if(!progress.get(currentStep).equals(SingletonUser.getSingletonUser().getName())) {
            Log.d("GAME","DefeatOrNothing");
            new DefeatOrNothing().execute();
        }else{
            Log.d("GAME","touch to ATTACK");
        }
    }
    class DefeatOrNothing extends AsyncTask<Void,String,String>
    {
        QuestionWrapper questionWrapper;

        protected void onPreExecute() {
        }
        @Override
        protected String doInBackground(Void... params) {
            try {
                deff=false;
                Log.d("GAME","execute DefeatOrNothing");
                URL url = new URL("http://10.0.3.2:8080/rest/game/checkMove?gameId="+
                        SingletonUser.getSingletonUser().currentGame+"&username="+
                        SingletonUser.getSingletonUser().getName());
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                Log.d("GAME","execute DefeatOrNothing URL");
                try {
                    Log.d("GAME","execute DefeatOrNothing try");
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(urlConnection.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    Gson gson = new Gson();
                    Log.d("GAME","before que");
                    questionWrapper = (gson.fromJson(response.toString(), QuestionWrapper.class));
                    if(questionWrapper.getQuestion().equals("")){

                        Log.d("GAME","NOTHING");
                    }else{
                        Log.d("GAME","DEFF" + questionWrapper.getQuestion() );
                        deff=true;
                    }
                    Log.d("GAME","after que");
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
           if(deff) showQuestion(questionWrapper) ;
               else
               new CheckState().execute();
        }
    }

    private void showQuestion(QuestionWrapper qw){
        LinearLayout ll = (LinearLayout) findViewById(R.id.questionTable);
        TextView q = (TextView) findViewById(R.id.question);
        q.setText(qw.getQuestion());

        a1.setText(qw.getAnswer1());
        a2.setText(qw.getAnswer2());
        a3.setText(qw.getAnswer3());
        a4.setText(qw.getAnswer4());

        ll.setVisibility(View.VISIBLE);

    }
    private void endGame(){
        Log.d("GAME","END");
    }
    class CheckAnswer extends AsyncTask<Void,String,String>
    {
        String ans;
        public CheckAnswer(String a){
            super();
            ans=a;
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
                    Log.d("GAME","ANSWE" + b);
                    if(b){
                        conqArea(att,SingletonUser.getSingletonUser().getName());
                    }
                    if(deff){
                        conqArea(att,progress.get(currentStep));
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
            newTurn();
        }
    }
    private void newTurn(){
        if(currentStep<progress.size()){
            currentStep++;
            deff=false;
            setCurrent();
            setChips();
            playGame();
        }else{
            endGame();
        }
    }
    private void conqArea(int idArea, String winner){
        GrafObject grafObject=null;
        for(int i=0;i<redHolding.size();i++)
            if (redHolding.get(i).getNumber()==idArea){
                grafObject=redHolding.get(i);
                redHolding.remove(i);
        }
        for(int i=0;i<greenHolding.size();i++)
            if (greenHolding.get(i).getNumber()==idArea){
                grafObject=greenHolding.get(i);
                greenHolding.remove(i);
            }
        for(int i=0;i<blueHolding.size();i++)
            if (blueHolding.get(i).getNumber()==idArea){
                grafObject=blueHolding.get(i);
                blueHolding.remove(i);
            }
        for(int i=0;i<areas.size();i++)
            if (areas.get(i).getNumber()==idArea){
                grafObject=areas.get(i);
                areas.remove(i);
            }
        if(redUser.getText().equals(winner))  redHolding.add(grafObject);
        if(greenUser.getText().equals(winner))  greenHolding.add(grafObject);
        if(blueUser.getText().equals(winner))  blueHolding.add(grafObject);
    }

    class CheckState extends AsyncTask<Void,String,String>
    {

        protected void onPreExecute() {
        }
        @Override
        protected String doInBackground(Void... params) {
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
                    Log.d("OOOOOOOOOOOOOO","response "+response);
                    entry = (gson.fromJson(response.toString(), java.util.Map.Entry.class));
                    Number key = entry.getKey();
                    String value = entry.getValue();
                    if(!value.equals("")){
                        conqArea(key.intValue(),value);
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
            newTurn();
        }
    }
    class GetSeq extends AsyncTask<Void,String,String>
    {

        protected void onPreExecute() {
        }
        @Override
        protected String doInBackground(Void... params) {
            try {
                Log.d("GAME","get seq");
                URL url = new URL("http://10.0.3.2:8080/rest/game/usersOrder?gameId="+
                        SingletonUser.getSingletonUser().currentGame);
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
                    ArrayList<String> strings = (gson.fromJson(response.toString(), ArrayList.class));
                    progress=strings;
                    setCurrent();
                    playGame();

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

        }
    }
    private void setCurrent(){
        curr.setText(progress.get(currentStep));
    }
    class GetQ extends AsyncTask<Void,String,String>
    {

        protected void onPreExecute() {
        }
        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL("http://10.0.3.2:8080/rest/user/check?username=");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    InputStream response = urlConnection.getInputStream();
                    Scanner s = new Scanner(response).useDelimiter("\\A");
                    String result = s.hasNext() ? s.next() : "";
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
        }
    }
    class StartGame extends AsyncTask<Void,String,String>
    {
        java.util.Map<String,Long> Jmap;
        protected void onPreExecute() {
        }
        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL("http://10.0.3.2:8080/rest/game/usersCastleLocation?gameId="+idGame);
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

                    Log.d("NEO",response.toString());
                    Gson gson = new Gson();
                    Jmap = (gson.fromJson(response.toString(), java.util.Map.class));

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
            Object [] array= Jmap.keySet().toArray();

            redUser.setText(array[0].toString());

            Number d  = Jmap.get(array[0]);
            for(int i=0;i<=areas.size();i++){
                if(areas.get(i).getNumber()==d.intValue()) {
                    areas.get(i).setCastle(true);
                    redHolding.add(areas.get(i));
                    areas.remove(areas.get(i));
                    break;
                }
            }

            greenUser.setText(array[1].toString());
            d  = Jmap.get(array[1]);
            for(int i=0;i<=areas.size();i++){
                if(areas.get(i).getNumber()==d.intValue()) {
                    areas.get(i).setCastle(true);
                    greenHolding.add(areas.get(i));
                    areas.remove(areas.get(i));

                    break;
                }
            }

            blueUser.setText(array[2].toString());
            d  = Jmap.get(array[2]);
            for(int i=0;i<=areas.size();i++){
                if(areas.get(i).getNumber()==d.intValue()) {
                    areas.get(i).setCastle(true);
                    blueHolding.add(areas.get(i));
                    areas.remove(areas.get(i));
                    break;
                }
            }
            setChips();


        }
    }

    private void setChips(){

        for(int i=0;i<greenHolding.size();i++){
            if(greenHolding.get(i).isCastle()){
                greenHolding.get(i).getChip().setImageResource(R.drawable.greencastle);
            }else
                greenHolding.get(i).getChip().setImageResource(R.drawable.greenchip);
        }
        for(int i=0;i<blueHolding.size();i++){
            if(blueHolding.get(i).isCastle()){
                blueHolding.get(i).getChip().setImageResource(R.drawable.bluecastle);
            }else
                blueHolding.get(i).getChip().setImageResource(R.drawable.bluechip);
        }
        for(int i=0;i<redHolding.size();i++){
            if(redHolding.get(i).isCastle()){
                redHolding.get(i).getChip().setImageResource(R.drawable.redcastle);
            }else
                redHolding.get(i).getChip().setImageResource(R.drawable.redchip);
        }
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d("touch",""+ progress.get(currentStep)+" <> " + SingletonUser.getSingletonUser().getName());
        if(progress.get(currentStep).equals(SingletonUser.getSingletonUser().getName())) {

            int x = (int) (event.getX() / width * map.getWidth());
            int y = (int) (event.getY() / height * map.getHeight());

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    att=map.touch(x,y);
                    Log.d("123",""+att);
                    //=====================================================================
                    new Attack().execute();
                    //=====================================================================
                    break;
            }
        }
        return true;
    }
    class Attack extends AsyncTask<Void,String,String>
    {
        QuestionWrapper questionWrapper;
        protected void onPreExecute() {
        }
        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL("http://10.0.3.2:8080/rest/game/attackTerritory?gameId="+
                        SingletonUser.getSingletonUser().currentGame+"&username="+
                        SingletonUser.getSingletonUser().getName()+"&territoryNumber=" +att);
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


                    Log.d("123",questionWrapper.getQuestion() );
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
            showQuestion(questionWrapper);
        }
    }
}
