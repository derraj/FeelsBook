package com.example.jarred.feelsbook;

import java.io.Serializable;
import java.util.Date;

public class Emotion implements Serializable{
    private Date date;
    private String comment;
    private String emotion;

    public Emotion(String emotion, String comment){
        this.date = new Date(System.currentTimeMillis());
        this.comment = comment;
        this.emotion = emotion;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
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

    public String toString(){
        return ("Emotion: "+this.getEmotion() + " Date: " + this.getDate()
        + " Comment : " + this.getComment());
    }

}
