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
    public Counter count = new Counter(emotionList);
    public ArrayList<Counter> counter_list;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feels_book);
        //counter_list = new ArrayList<>();
        loadData();

        Button surpriseBtn = (Button) findViewById(R.id.surpriseBtn);
        Button fearBtn = (Button) findViewById(R.id.fearBtn);
        Button sadBtn = (Button) findViewById(R.id.sadBtn);
        Button joyBtn = (Button) findViewById(R.id.joyBtn);
        Button angerBtn = (Button) findViewById(R.id.angerBtn);
        Button loveBtn = (Button) findViewById(R.id.loveBtn);
        Button historyBtn = (Button) findViewById(R.id.historyBtn);

        final TextView surpriseNum = (TextView) findViewById(R.id.surpriseNum);
        final TextView sadNum = (TextView) findViewById(R.id.sadNum);
        final TextView joyNum = (TextView) findViewById(R.id.joyNum);
        final TextView angerNum = (TextView) findViewById(R.id.angerNum);
        final TextView loveNum = (TextView) findViewById(R.id.loveNum);
        final TextView fearNum = (TextView) findViewById(R.id.fearNum);

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

                if (tagString.equals("Surprise")){
                    surpriseNum.setText(count.val(tagString));
                }
                else if (tagString.equals("Sad")){
                    sadNum.setText(count.val(tagString));
                }
                else if (tagString.equals("Joy")){
                    joyNum.setText(count.val(tagString));
                }
                else if (tagString.equals("Anger")){
                    angerNum.setText(count.val(tagString));
                }
                else if (tagString.equals("Love")){
                    loveNum.setText(count.val(tagString));
                }
                else if (tagString.equals("Fear")){
                    fearNum.setText(count.val(tagString));
                }

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
        String json = gson.toJson(counter_list);
        editor.putString("counter object", json);
        editor.apply();
    }

    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("counter object", null);
        Type type = new TypeToken<ArrayList<Counter>>(){}.getType();
        counter_list = gson.fromJson(json, type);

        if (count == null){
            counter_list = new ArrayList<>();
        }
    }

}
