package com.ske.minitrello.models;


import java.util.List;

public class Card {

    private String name;
    private String description;
    private List<Comment> comments;

    public Card(String name) {
        this.name = name;
        description = "";
    }

    public Card(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Card(String name, String description, List<Comment> comments) {
        this(name, description);
        this.comments = comments;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
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

    @Override
    public String toString() {
        return "Card name: " + name + "\nDescription: " + description + "\n";
    }
}
