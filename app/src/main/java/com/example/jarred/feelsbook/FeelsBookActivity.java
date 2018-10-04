package com.example.jarred.feelsbook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class FeelsBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feels_book);


        Button surpriseBtn = (Button) findViewById(R.id.surpriseBtn);
        Button fearBtn = (Button) findViewById(R.id.fearBtn);
        Button sadBtn = (Button) findViewById(R.id.sadBtn);
        Button joyBtn = (Button) findViewById(R.id.joyBtn);
        Button angerBtn = (Button) findViewById(R.id.angerBtn);
        Button loveBtn = (Button) findViewById(R.id.loveBtn);

        surpriseBtn.setTag("Surprise");
        fearBtn.setTag("Fear");
        sadBtn.setTag("Sad");
        joyBtn.setTag("Joy");
        angerBtn.setTag("Anger");
        loveBtn.setTag("Love");

        Emotion[] emoArray = new Emotion[100];

        View.OnClickListener onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tagString = (String) v.getTag();
                //Log.d(tagString, "Message");
                Emotion emotion = new Emotion(tagString, "Message");
            }
        };

        surpriseBtn.setOnClickListener(onClickListener);
        fearBtn.setOnClickListener(onClickListener);
        sadBtn.setOnClickListener(onClickListener);
        joyBtn.setOnClickListener(onClickListener);
        angerBtn.setOnClickListener(onClickListener);
        loveBtn.setOnClickListener(onClickListener);


    }
}
