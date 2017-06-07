package com.example.a1.conq;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import com.example.a1.conq.GameObject.GrafObject;
import com.example.a1.conq.GameObject.Map;
import com.example.a1.conq.async.*;
import com.example.a1.conq.entity.QuestionWrapper;
import com.google.gson.Gson;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GameActivity extends AppCompatActivity implements View.OnTouchListener {
    private int width;
    private int height;
    private Map map;
    private ImageView imageMap;
    private int idGame;
    private TextView redUser;
    private TextView greenUser;
    private TextView blueUser;
    private TextView curr;
    private int att;
    private ArrayList<GrafObject> redHolding;
    private ArrayList<GrafObject> greenHolding;
    private ArrayList<GrafObject> blueHolding;
    private ArrayList<GrafObject> attackHolding;
    static final private int QUESTION = 1;
    private ArrayList<GrafObject> areas;

    private ArrayList<String> progress;
    private ArrayList<ImageView> attackChips;
    private String answer;
    private boolean deff;
    private int currentStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        prepareToGame();
        Log.d("GAME", "START");
    }

    private void prepareToGame() {
        att = -1;
        answer = "";
        currentStep = 0;
        attackChips = new ArrayList<>();
        progress = new ArrayList<>();
        setUpMap();
        deff = false;
        curr = (TextView) findViewById(R.id.current);

        Display display = getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();

        redUser = (TextView) findViewById(R.id.red);
        greenUser = (TextView) findViewById(R.id.green);
        blueUser = (TextView) findViewById(R.id.blue);
        new StartGame().execute();
        redHolding = new ArrayList<>();
        greenHolding = new ArrayList<>();
        blueHolding = new ArrayList<>();
        attackHolding = new ArrayList<>();
        areas = map.getAreas();
        setPositionView();
        new GetSeq().execute();
    }

    private void setUpMap() {
        map = new Map();
        map.setHeight(1080);
        map.setWidth(1920);
        ArrayList<ImageView> imageViews = new ArrayList<>();
        imageViews.add((ImageView) findViewById(R.id.area1));
        imageViews.add((ImageView) findViewById(R.id.area2));
        imageViews.add((ImageView) findViewById(R.id.area3));
        imageViews.add((ImageView) findViewById(R.id.area4));
        imageViews.add((ImageView) findViewById(R.id.area5));
        imageViews.add((ImageView) findViewById(R.id.area6));
        imageViews.add((ImageView) findViewById(R.id.area7));
        imageViews.add((ImageView) findViewById(R.id.area8));
        imageViews.add((ImageView) findViewById(R.id.area9));
        imageViews.add((ImageView) findViewById(R.id.area10));
        imageViews.add((ImageView) findViewById(R.id.area11));
        imageViews.add((ImageView) findViewById(R.id.area12));
        imageViews.add((ImageView) findViewById(R.id.area13));
        imageViews.add((ImageView) findViewById(R.id.area14));
        imageViews.add((ImageView) findViewById(R.id.area15));
        map.setChipsArea(imageViews);
        imageMap = (ImageView) findViewById(R.id.map);
        imageMap.setOnTouchListener(this);
    }

    private void setPositionView() {
        FrameLayout fl = (FrameLayout) findViewById(R.id.fl);
        for (int i = 0; i < areas.size(); i++) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams((int) (100d / map.getHeight() * height),
                    (int) (100d / map.getWidth() * width));

            params.leftMargin = (int) (((areas.get(i).getCenter().getX() - 50d) / map.getWidth()) * width);
            params.topMargin = (int) ((areas.get(i).getCenter().getY() - 50d) / map.getHeight() * height);
            fl.removeView(areas.get(i).getChip());
            fl.addView(areas.get(i).getChip(), params);
        }
    }

    private void playGame() {
        Log.d("GAME", "play game");
        if (!progress.get(currentStep).equals(SingletonUser.getSingletonUser().getName())) {
            Log.d("GAME", "Wait user  attack");
            new WaitUserAttack(GameActivity.this).execute();
        } else {
            Log.d("GAME", "touch to ATTACK");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == QUESTION) {
            if (resultCode == RESULT_OK) {
                Log.d("GAME", "ANSWER");
                boolean b = data.getBooleanExtra("result", false);

                Log.d("GAME", "ANSWE" + b);
                if (b) {
                    conqArea(att, SingletonUser.getSingletonUser().getName());
                } else {
                    if (!SingletonUser.getSingletonUser().getName().equals(progress.get(currentStep)))
                        conqArea(att, progress.get(currentStep));

                }
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        newTurn();
                    }
                }, 2000);

            } else {
                Log.d("GAME", "SMTH WRONG");
            }
        }
    }

    public void showQuestion(QuestionWrapper qw) {
        Intent intent = new Intent(GameActivity.this, QuestionActivity.class);
        intent.putExtra("question", qw.getQuestion());
        intent.putExtra("a1", qw.getAnswer1());
        intent.putExtra("a2", qw.getAnswer2());
        intent.putExtra("a3", qw.getAnswer3());
        intent.putExtra("a4", qw.getAnswer4());
        intent.putExtra("att", att);

        startActivityForResult(intent, QUESTION);
    }

    private void endGame() {
        Log.d("GAME", "END");
    }

    public void checkState() {
        new CheckState(this).execute();
    }

    public ArrayList<String> getProgress() {
        return progress;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public void enemyAttack() {
        new DefeatOrNothing(this).execute();
    }

    public void newTurn() {
        if (currentStep < progress.size() - 1) {
            Log.d("11111111111111111", currentStep + " " + progress.get(currentStep) + " " + progress.get(currentStep + 1));
            currentStep++;
            deff = false;
            setCurrent();
            removeAttackChip();
            setChips();
            playGame();
        } else {
            endGame();
        }
    }

    private void removeAttackChip() {
        for (int i = 0; i < attackChips.size(); i++) {
            attackChips.get(i).setImageBitmap(null);
        }
        attackChips = new ArrayList<>();
    }

    public void prepareAttack() {
        if (redUser.getText().equals(SingletonUser.getSingletonUser().getName())) addToAttackHolding(redHolding);
        if (greenUser.getText().equals(SingletonUser.getSingletonUser().getName())) addToAttackHolding(greenHolding);
        if (blueUser.getText().equals(SingletonUser.getSingletonUser().getName())) addToAttackHolding(blueHolding);

    }

    private void addToAttackHolding(ArrayList<GrafObject> arrayList) {
        attackHolding = new ArrayList<>();

        for (int i = 0; i < arrayList.size(); i++) {
            ArrayList<GrafObject> near = arrayList.get(i).getNearbyAreas();
            for (int j = 0; j < near.size(); j++) {
                if ((!attackHolding.contains(near.get(j))) && (!arrayList.contains(near.get(j))))
                    attackHolding.add(near.get(j));
            }
        }
    }

    public void conqArea(int idArea, String winner) {
        GrafObject grafObject = null;
        for (int i = 0; i < redHolding.size(); i++)
            if (redHolding.get(i).getNumber() == idArea) {
                grafObject = redHolding.get(i);
                redHolding.remove(i);
            }
        for (int i = 0; i < greenHolding.size(); i++)
            if (greenHolding.get(i).getNumber() == idArea) {
                grafObject = greenHolding.get(i);
                greenHolding.remove(i);
            }
        for (int i = 0; i < blueHolding.size(); i++)
            if (blueHolding.get(i).getNumber() == idArea) {
                grafObject = blueHolding.get(i);
                blueHolding.remove(i);
            }
        for (int i = 0; i < areas.size(); i++)
            if (areas.get(i).getNumber() == idArea) {
                grafObject = areas.get(i);
                areas.remove(i);
            }
        if (redUser.getText().equals(winner)) redHolding.add(grafObject);
        if (greenUser.getText().equals(winner)) greenHolding.add(grafObject);
        if (blueUser.getText().equals(winner)) blueHolding.add(grafObject);
    }


    class GetSeq extends AsyncTask<Void, String, String> {

        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                Log.d("GAME", "get seq");
                URL url = new URL("http://10.0.3.2:8080/rest/game/usersOrder?gameId=" +
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
                    progress = strings;
                } finally {
                    urlConnection.disconnect();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            setCurrent();
            setChips();
            playGame();
        }
    }

    private void setCurrent() {
        curr.setText(progress.get(currentStep));
    }

    class StartGame extends AsyncTask<Void, String, String> {
        java.util.Map<String, Long> Jmap;

        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL("http://10.0.3.2:8080/rest/game/usersCastleLocation?gameId=" + idGame);
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

                    Log.d("NEO", response.toString());
                    Gson gson = new Gson();
                    Jmap = (gson.fromJson(response.toString(), java.util.Map.class));

                } finally {
                    urlConnection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Object[] array = Jmap.keySet().toArray();

            redUser.setText(array[0].toString());
            Number d = Jmap.get(array[0]);
            /*for(int i=0;i<=areas.size();i++){
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
            }*/

            setStartArea(redUser, array[0].toString(), d.intValue(), redHolding);
            d = Jmap.get(array[1]);
            setStartArea(greenUser, array[1].toString(), d.intValue(), greenHolding);
            d = Jmap.get(array[2]);
            setStartArea(blueUser, array[2].toString(), d.intValue(), blueHolding);

            //  setChips();


        }
    }

    private void setStartArea(TextView textView, String name, int idArea, ArrayList<GrafObject> holding) {
        textView.setText(name);

        for (int i = 0; i <= areas.size(); i++) {
            if (areas.get(i).getNumber() == idArea) {
                areas.get(i).setCastle(true);
                holding.add(areas.get(i));
                areas.remove(areas.get(i));
                break;
            }
        }
        if (name.equals(SingletonUser.getSingletonUser().getName())) {
            textView.setTypeface(null, Typeface.BOLD);
        }
    }

    private void setChips() {

        for (int i = 0; i < greenHolding.size(); i++) {
            if (greenHolding.get(i).isCastle()) {
                greenHolding.get(i).getChip().setImageResource(R.drawable.greencastle);
            } else
                greenHolding.get(i).getChip().setImageResource(R.drawable.greenchip);
        }
        for (int i = 0; i < blueHolding.size(); i++) {
            if (blueHolding.get(i).isCastle()) {
                blueHolding.get(i).getChip().setImageResource(R.drawable.bluecastle);
            } else
                blueHolding.get(i).getChip().setImageResource(R.drawable.bluechip);
        }
        for (int i = 0; i < redHolding.size(); i++) {
            if (redHolding.get(i).isCastle()) {
                redHolding.get(i).getChip().setImageResource(R.drawable.redcastle);
            } else
                redHolding.get(i).getChip().setImageResource(R.drawable.redchip);
        }
        if (progress.get(currentStep).equals(SingletonUser.getSingletonUser().getName())) {
            prepareAttack();
            FrameLayout fl = (FrameLayout) findViewById(R.id.fl);
            for (int i = 0; i < attackHolding.size(); i++) {

                ImageView imageView = new ImageView(this);
                imageView.setImageResource(R.drawable.attackchip);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams((int) (100d / map.getHeight() * height),
                        (int) (100d / map.getWidth() * width));

                params.leftMargin = (int) (((attackHolding.get(i).getCenter().getX() - 50d) / map.getWidth()) * width);
                params.topMargin = (int) ((attackHolding.get(i).getCenter().getY() - 50d) / map.getHeight() * height);
                attackChips.add(imageView);
                fl.addView(imageView, params);
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d("touch", "" + progress.get(currentStep) + " <> " + SingletonUser.getSingletonUser().getName());
        if (progress.get(currentStep).equals(SingletonUser.getSingletonUser().getName())) {

            int x = (int) (event.getX() / width * map.getWidth());
            int y = (int) (event.getY() / height * map.getHeight());

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    att = map.touch(x, y);
                    if (att > 0) {
                        boolean f = false;
                        for (int i = 0; i < attackHolding.size(); i++) {
                            if (attackHolding.get(i).getNumber() == att) {
                                f = true;
                                break;
                            }
                        }
                        Log.d("GAME", SingletonUser.getSingletonUser().getName() + " attack " + att);
                        if (f) {
                            new Attack(this).execute(att);
                        }
                    }
                    break;
            }
        }
        return true;
    }

}
