package com.example.a1.conq;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.example.a1.conq.async.FindGame;
import com.example.a1.conq.async.Login;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button newGame = (Button) findViewById(R.id.newGame);
        View.OnClickListener oclNewGame = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SingletonUser.getSingletonUser().getName()!=null){
                    new FindGame(MainActivity.this).execute();
                }
            }
        };
        newGame.setOnClickListener(oclNewGame);
        Button regAuto = (Button) findViewById(R.id.regauto);
        View.OnClickListener oclRegAuto = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Title");
                Context context =  MainActivity.this.getBaseContext();
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);
                final EditText inputLogin = new EditText(MainActivity.this);
                final EditText inputPass = new EditText(MainActivity.this);
                inputLogin.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                inputPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                layout.addView(inputLogin);
                layout.addView(inputPass);
                builder.setView(layout);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String l=inputLogin.getText().toString();
                        String p=inputPass.getText().toString();
                        new Login(l,p).execute();
                    }
                });
                builder.setNeutralButton("reg", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, Registration.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

            }
        };
        regAuto.setOnClickListener(oclRegAuto);
    }



}
