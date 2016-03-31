package com.example.songshan.festivalmessage.bean;

/**
 * Created by songshan on 2016/3/28.
 */
public class Msg {
    public Msg(int id, int festivalId, String content, String festival) {
        this.id = id;
        this.festivalId = festivalId;
        this.content = content;
        this.festival=festival;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private int festivalId;
    private String content;
    private String festival;

    public int getFestivalId() {
        return festivalId;
    }

    public void setFestivalId(int festivalId) {
        this.festivalId = festivalId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
