package com.dodo81.memo1.model;

import java.io.Serializable;

public class Memo implements Serializable {

    public int id;
    public String title;
    public String content;

    public Memo(){

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Memo(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Memo(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}