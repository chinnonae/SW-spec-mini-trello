package com.ske.minitrello.models;


import java.util.ArrayList;
import java.util.List;

public class Card {

    private String name;
    private String description;
    private List<Comment> comments;
    private int id;

    public Card(String name) {
        this.name = name;
        description = "";
        comments = new ArrayList<Comment>();
        id = -1;
    }

    public Card(String name, String description) {
        this.name = name;
        this.description = description;
        comments = new ArrayList<Comment>();
        id = -1;
    }

    public Card(String name, String description, List<Comment> comments) {
        this(name, description);
        this.comments = comments;
        this.id = -1;

    }

    public Card(String name, String description, int id){
        this.name = name;
        this.description = description;
        this.id = id;
        comments = new ArrayList<Comment>();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void deleteComment(Comment comment) {
        comments.remove(comment);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId(){ return id; }

    @Override
    public String toString() {
        return "Card name: " + name + "\nDescription: " + description + "\n";
    }
}
