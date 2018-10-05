package com.example.jarred.feelsbook;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Emotion implements Serializable, Comparable<Emotion>{

    private String date;
    private String comment;
    private String emotion;

    // Handled date with help from https://stackoverflow.com/a/27657965
    // The rest of the sorting code is in ListActivity.updateDate();
    public int compareTo(Emotion o) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            return sdf.parse(this.getDate()).compareTo(sdf.parse(o.getDate()));
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public Emotion(String emotion, String comment){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        this.date = sdf.format( new Date());
        this.comment = comment;
        this.emotion = emotion;
    }

    public void setDate(String date_string){
        this.date = date_string;

    }

    public String getDate() {
        return this.date;
    }

    public void setComment(String comment){
        this.comment = comment;
    }

    public String getComment(){
        return this.comment;
    }

    public String getEmotion(){
        return this.emotion;
    }

    // Turn all information of the Emotion into a string
    public String toString(){
        return ("Emotion: "+this.getEmotion() + "\n Date: " + this.getDate()
        + "\n Comment : " + this.getComment());
    }



}
