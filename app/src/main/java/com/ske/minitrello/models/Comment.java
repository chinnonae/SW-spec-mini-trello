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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        if (createdTime != comment.createdTime) return false;
        return content.equals(comment.content);

    }

    @Override
    public int hashCode() {
        int result = content.hashCode();
        result = 31 * result + (int) (createdTime ^ (createdTime >>> 32));
        return result;
    }
}
