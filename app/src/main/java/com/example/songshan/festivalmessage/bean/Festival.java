package com.example.songshan.festivalmessage.bean;

import android.provider.ContactsContract;

/**
 * Created by songshan on 2016/3/28.
 */
public class Festival {
    private int id;
    private String name;
    private String desc;
    public Festival(int id,String name){
        this.id=id;
        this.name=name;
    }

    public ContactsContract.Data getData() {
        return data;
    }

    public void setData(ContactsContract.Data data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private ContactsContract.Data data;
}
