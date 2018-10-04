package com.example.jarred.feelsbook;

import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Counter extends AppCompatActivity {
    private Map<String, Integer> count;
    private List<String> emotionList;

    public Counter(List<String> emotionList){
        this.count = new HashMap<String, Integer>();
        this.emotionList = emotionList;
        for (String o : this.emotionList) this.count.put(o,0);
    }

    public void increment(String emotion){
        this.count.put(emotion, this.count.get(emotion)+1);
    }
    public void decrement(String emotion){
        this.count.put(emotion, this.count.get(emotion)-1);
    }

    public String val(String emotion){
        return this.count.get(emotion).toString();
    }


}
