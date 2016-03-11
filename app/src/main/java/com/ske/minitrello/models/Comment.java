package com.ske.minitrello.models;


import org.ocpsoft.prettytime.PrettyTime;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Comment implements Serializable {

    private String content;
    private final long createdTime;

    public Comment(String content) {
        this.content = content;
        createdTime = System.currentTimeMillis();
    }

    public Comment(String content, long createdTime){
        this.content = content;
        this.createdTime = createdTime;
    }

    public String getContent() {
        return content;
    }

    public long getCreatedTime(){ return createdTime; }

    public String getReadableTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        return sdf.format(new Date(createdTime));
    }

    public String getRelativeTime() {
        PrettyTime prettyTime = new PrettyTime();
        return prettyTime.format(new Date(createdTime));
    }
}
