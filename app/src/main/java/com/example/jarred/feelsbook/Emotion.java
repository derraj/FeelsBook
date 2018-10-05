package com.example.jarred.feelsbook;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Emotion implements Serializable, Comparable<Emotion>{

    private String date;
    private String comment;
    private String emotion;
    // sdf is our desired date format
    // https://stackoverflow.com/questions/36114426/java-lang-illegalargumentexception-class-java-text-decimalformat-declares-multi
    private transient SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    // Handled date with help from https://stackoverflow.com/a/27657965
    // The rest of the sorting code is in ListActivity.updateDate();
    public int compareTo(Emotion o) {
        try {
            return sdf.parse(this.getDate()).compareTo(sdf.parse(o.getDate()));
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public Emotion(String emotion, String comment){
        this.date = sdf.format( new Date());    // get current date and change format
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
