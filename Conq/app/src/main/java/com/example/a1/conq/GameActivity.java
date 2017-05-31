package com.example.a1.conq;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import com.example.a1.conq.GameObject.Map;

import java.io.File;


/**
 * Created by 1 on 30.05.2017.
 */
public class GameActivity extends AppCompatActivity implements View.OnTouchListener {
    int width;
    int height;
    Map map;
    ImageView imageMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        map = new Map();
        map.setHeight(1080);
        map.setWidth(1920);
        imageMap = (ImageView) findViewById(R.id.map);
        imageMap.setOnTouchListener(this);
      //  map.writeToFile(new File(getFilesDir(),"map.txt"));

        Display display = getWindowManager().getDefaultDisplay();
        width = display.getWidth();  // deprecated
        height = display.getHeight();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int x = (int)(event.getX()/width*map.getWidth());
        int y = (int) (event.getY()/height*map.getHeight());

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("touch",x+" "+y+" "+map.touch(x,y));
                break;
        }

        return true;
    }
}
