package com.example.jarred.feelsbook;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Counter implements Serializable{
    private Map<String, Integer> count;
    private List<String> emotionList;

    public Counter(List<String> emotionList){
        this.count = new HashMap<String, Integer>();
        this.emotionList = emotionList;
        for (String o : this.emotionList) this.count.put(o,0);
    }

    // increment the counter based off emotion given as attribute
    public void increment(String emotion){
        this.count.put(emotion, this.count.get(emotion)+1);
    }

    // return string value of the count
    public String val(String emotion){
        return this.count.get(emotion).toString();
    }


}
