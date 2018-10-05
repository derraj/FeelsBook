package com.example.jarred.feelsbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class FeelsBookActivity extends AppCompatActivity {
    public ArrayList<Emotion> emoList;
    public List<String> emotionList = Arrays.asList("Surprise", "Fear", "Sad","Joy","Anger","Love");
    public Counter count;
    public TextView surpriseNum,sadNum,joyNum,angerNum,loveNum,fearNum;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feels_book);

        emoList = loadData();

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

        // give buttons string tags, to pass to Emotion class
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

                EditText message = (EditText)findViewById(R.id.message);
                Emotion emotion = new Emotion(tagString, message.getText().toString());
                emoList.add(emotion);
                saveData();
                update();
            }
        };


        // Open ListActivity
        View.OnClickListener historyListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openHistory = new Intent(getApplicationContext(), ListActivity.class);

                // Pass list of emotion objects by using serializable
                Bundle emo_bundle = new Bundle();
                emo_bundle.putSerializable("emoList", emoList);
                openHistory.putExtras(emo_bundle);

                startActivity(openHistory);
            }
        };

        historyBtn.setOnClickListener(historyListener);
        surpriseBtn.setOnClickListener(onClickListener);
        fearBtn.setOnClickListener(onClickListener);
        sadBtn.setOnClickListener(onClickListener);
        joyBtn.setOnClickListener(onClickListener);
        angerBtn.setOnClickListener(onClickListener);
        loveBtn.setOnClickListener(onClickListener);
    }

    // Save count object and emoList array list
    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // Save the counter
        Gson gson = new Gson();
        String json = gson.toJson(count);
        editor.putString("counter object", json);
        editor.apply();

        // Save the Emotion list
        String json2 = gson.toJson(emoList);
        editor.putString("emotion object list", json2);
        editor.apply();
    }

    // Load count variable and emoList (emoList returned as array list)
    // Create new list of emotions if no past data found
    public ArrayList<Emotion> loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();

        // load counter object
        String json = sharedPreferences.getString("counter object", null);
        count = gson.fromJson(json, Counter.class);

        // load list of emotion objects
        String json2 = sharedPreferences.getString("emotion object list", null);
        ArrayList<Emotion> emoList;

        if (json2 == null){
            emoList = new ArrayList<Emotion>();
        }
        else{
            Type type = new TypeToken<ArrayList<Emotion>>() {}.getType();
            emoList = gson.fromJson(json2, type);
        }
        return emoList;

    }

    // update Counter when emotion list is updated
    private void update(){
        count = new Counter(emotionList);

        for (Emotion e : emoList){
            count.increment(e.getEmotion());
        }

        // update text views that display the emotion count
        surpriseNum.setText(count.val("Surprise"));
        sadNum.setText(count.val("Sad"));
        joyNum.setText(count.val("Joy"));
        angerNum.setText(count.val("Anger"));
        loveNum.setText(count.val("Love"));
        fearNum.setText(count.val("Fear"));
    }

}
