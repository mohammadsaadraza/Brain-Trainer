package com.example.saadahmed.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int a;
    int b;
    int correctAnswer=0;
    int totalQuestions=0;
    Random rand;
    Button startButton;
    TextView seconds;
    TextView answers;
    TextView question;
    int ANSWER;
    boolean gameActive=false;
    GridLayout gd;

    public void updateSeconds(int secondsLeft){
        seconds.setText(Integer.toString(secondsLeft)+"s");
    }
    public void updateAnswers(){
        answers.setText(String.format("%s/%s", Integer.toString(correctAnswer), Integer.toString(totalQuestions)));
    }
    public void resetSeconds(){
        seconds.setText("30s");
    }
    public void resetAnswers(){
        answers.setText("0/0");
    }
    public void updateQuestionsAndOptions(){
        a=rand.nextInt(101);
        b=rand.nextInt(101);
        ANSWER=a+b;
        String text=String.format("%s + %s", Integer.toString(a), Integer.toString(b));
        question.setText(text);
        int temp=rand.nextInt(4);
        Button b0=findViewById(R.id.button0);
        Button b1=findViewById(R.id.button1);
        Button b2=findViewById(R.id.button2);
        Button b3=findViewById(R.id.button3);
        ArrayList<Button> buttonArrayList=new ArrayList<Button>();
        buttonArrayList.add(b0);
        buttonArrayList.add(b1);
        buttonArrayList.add(b2);
        buttonArrayList.add(b3);

        buttonArrayList.get(temp).setText(Integer.toString(ANSWER));
        for(int i=0;i<4;i++){
            if(i!=temp)
                buttonArrayList.get(i).setText(Integer.toString(rand.nextInt(203)));
        }

    }

    public void startTimer(){
        new CountDownTimer(30*1000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                updateSeconds((int)millisUntilFinished/1000);
                updateAnswers();
            }

            @Override
            public void onFinish() {
                gameActive=false;
                Button playAgain=findViewById(R.id.playAgain);
                playAgain.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(),"TIME OVER!!",Toast.LENGTH_SHORT).show();
            }
        }.start();
    }

    public void start(View view){
        startButton.setVisibility(View.INVISIBLE);
        startTimer();
        gameActive=true;
        seconds.setVisibility(View.VISIBLE);
        answers.setVisibility(View.VISIBLE);
        question.setVisibility(View.VISIBLE);
        gd.setVisibility(View.VISIBLE);
    }
    public void playAgain(View view){
        gameActive=true;
        correctAnswer=0;
        totalQuestions=0;
        startTimer();
        updateQuestionsAndOptions();
        resetAnswers();
        resetSeconds();

    }

    public void guess(View view){
        Button option=(Button)view;
        String tappedOption=option.getTag().toString();

        if(gameActive){
            if(Integer.parseInt(option.getText().toString())==ANSWER){
                Toast.makeText(this,"Correct!!",Toast.LENGTH_SHORT).show();
                correctAnswer+=1;
                totalQuestions+=1;
                updateQuestionsAndOptions();
            }else{
                Toast.makeText(this,"Wrong!!",Toast.LENGTH_SHORT).show();
                totalQuestions+=1;
                updateQuestionsAndOptions();
            }
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rand=new Random();


        startButton=findViewById(R.id.startButton);
        seconds=findViewById(R.id.seconds);
        question=findViewById(R.id.question);
        answers=findViewById(R.id.score);
        gd=findViewById(R.id.gridLayout);
        updateQuestionsAndOptions();
    }
}
