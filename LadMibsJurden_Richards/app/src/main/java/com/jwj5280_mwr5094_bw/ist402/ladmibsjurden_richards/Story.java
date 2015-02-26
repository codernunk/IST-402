package com.jwj5280_mwr5094_bw.ist402.ladmibsjurden_richards;

/**
 * Created by Jesse on 2/25/2015.
 */
public class Story {
    private String title;
    private String text;

    public Story(){

    }

    public Story(String title, String text){
        this.title = title;
        this.text = text;
    }

    public void setTitle(String val){
        title = val;
    }

    public void setText(String val){
        text = val;
    }

    public String getTitle(){
        return title;
    }

    public String getText(){
        return text;
    }
}
