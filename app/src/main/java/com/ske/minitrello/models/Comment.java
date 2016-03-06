package com.ske.minitrello.models;


import java.text.SimpleDateFormat;
import java.util.Date;

public class Comment {

    private String content;
    private long createdTime;

    public Comment(String content) {
        this.content = content;
        createdTime = System.currentTimeMillis();
    }

    public String getContent() {
        return content;
    }

    public String getCreatedTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        return sdf.format(new Date(createdTime));
    }
}
