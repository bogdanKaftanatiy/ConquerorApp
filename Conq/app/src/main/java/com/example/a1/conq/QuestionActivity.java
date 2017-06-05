package com.example.a1.conq;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.a1.conq.async.CheckAnswer;

import java.util.concurrent.TimeUnit;

public class QuestionActivity extends AppCompatActivity{

    private Intent answerIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("QQQQQQQQQQQQQ","start");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);

        answerIntent = getIntent();
        Button quit = (Button) findViewById(R.id.Q);
        View.OnClickListener quitAnswer= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 QuestionActivity.this.finish();
            }
        };
        quit.setOnClickListener(quitAnswer);
        final String question = answerIntent.getStringExtra("question");
        final String answer1 = answerIntent.getStringExtra("a1");
        final String answer2 = answerIntent.getStringExtra("a2");
        final String answer3 = answerIntent.getStringExtra("a3");
        final String answer4 = answerIntent.getStringExtra("a4");
        TextView q = (TextView) findViewById(R.id.question);
        q.setText(question);

        Button a1 = (Button) findViewById(R.id.answer1);
        Button a2 = (Button) findViewById(R.id.answer2);
        Button a3 = (Button) findViewById(R.id.answer3);
        Button a4 = (Button) findViewById(R.id.answer4);

        a1.setText(answer1);
        a2.setText(answer2);
        a3.setText(answer3);
        a4.setText(answer4);

        View.OnClickListener sendAnswer= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b =(Button) findViewById(v.getId());
                new CheckAnswer(QuestionActivity.this,b.getText().toString()).execute();

            }
        };
        a1.setOnClickListener(sendAnswer);
        a2.setOnClickListener(sendAnswer);
        a3.setOnClickListener(sendAnswer);
        a4.setOnClickListener(sendAnswer);
    }

    public void myFinish(){
        setResult(RESULT_OK, answerIntent);
        QuestionActivity.this.finish();
    }
}
