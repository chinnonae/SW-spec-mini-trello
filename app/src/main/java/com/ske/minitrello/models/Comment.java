package com.ske.minitrello.models;


public class Comment {

    private String content;
    private long createdTime;

    public Comment(String content) {
        this.content = content;
        createdTime = System.currentTimeMillis();
    }
}
