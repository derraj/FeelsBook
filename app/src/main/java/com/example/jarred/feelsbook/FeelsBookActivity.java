package com.example.jarred.feelsbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FeelsBookActivity extends AppCompatActivity {
    public ArrayList<Emotion> emoList = new ArrayList<>();
    public List<String> emotionList = Arrays.asList("Surprise", "Fear", "Sad","Joy","Anger","Love");
    public Counter count;
    public TextView surpriseNum,sadNum,joyNum,angerNum,loveNum,fearNum;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feels_book);
        //count = new Counter(emotionList);
        loadData();


        Button surpriseBtn = (Button) findViewById(R.id.surpriseBtn);
        Button fearBtn = (Button) findViewById(R.id.fearBtn);
        Button sadBtn = (Button) findViewById(R.id.sadBtn);
        Button joyBtn = (Button) findViewById(R.id.joyBtn);
        Button angerBtn = (Button) findViewById(R.id.angerBtn);
        Button loveBtn = (Button) findViewById(R.id.loveBtn);
        Button historyBtn = (Button) findViewById(R.id.historyBtn);

        surpriseNum = (TextView) findViewById(R.id.surpriseNum);
        sadNum = (TextView) findViewById(R.id.sadNum);
        joyNum = (TextView) findViewById(R.id.joyNum);
        angerNum = (TextView) findViewById(R.id.angerNum);
        loveNum = (TextView) findViewById(R.id.loveNum);
        fearNum = (TextView) findViewById(R.id.fearNum);

        update();

        surpriseBtn.setTag("Surprise");
        fearBtn.setTag("Fear");
        sadBtn.setTag("Sad");
        joyBtn.setTag("Joy");
        angerBtn.setTag("Anger");
        loveBtn.setTag("Love");


        View.OnClickListener onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tagString = (String) v.getTag(); // tagString is the emotion in str format
                Emotion emotion = new Emotion(tagString, "Message");
                emoList.add(emotion);
                count.increment(tagString);
                saveData();
                update();
            }
        };

        surpriseBtn.setOnClickListener(onClickListener);
        fearBtn.setOnClickListener(onClickListener);
        sadBtn.setOnClickListener(onClickListener);
        joyBtn.setOnClickListener(onClickListener);
        angerBtn.setOnClickListener(onClickListener);
        loveBtn.setOnClickListener(onClickListener);

        View.OnClickListener historyListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                for (Emotion s : emoList){
                    Log.d("My array list content: ", s.getEmotion());

                }
                */
                Intent openHistory = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(openHistory);
            }
        };

        historyBtn.setOnClickListener(historyListener);



    }

    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(count);
        editor.putString("counter object", json);
        editor.apply();
    }

    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("counter object", null);
        count = gson.fromJson(json, Counter.class);

    }

    private void update(){
        surpriseNum.setText(count.val("Surprise"));
        sadNum.setText(count.val("Sad"));
        joyNum.setText(count.val("Joy"));
        angerNum.setText(count.val("Anger"));
        loveNum.setText(count.val("Love"));
        fearNum.setText(count.val("Fear"));
    }

}
