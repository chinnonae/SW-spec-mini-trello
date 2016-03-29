package com.ske.minitrello.models;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Card implements Serializable {

    private int id;
    private String name;
    private String description;
    private long createdTime; // TODO: Add to database later
    private List<Comment> comments;

    public enum DBColumn{
        name("title"),description("description");

        private String colName;
        private DBColumn(String colName){
            this.colName = colName;
        }


        public String toString(){
            return colName;
        }
    }


    public Card(String name) {
        id = -1;
        this.name = name;
        description = "";
        comments = new ArrayList<Comment>();
        createdTime = System.currentTimeMillis();
    }

    public Card(String name, String description) {
        this(name);
        this.description = description;
    }

    public Card(String name, String description, List<Comment> comments) {
        this(name, description);
        this.comments = comments;
    }

    public Card(String name, String description, List<Comment> comments ,int id){
        this.name = name;
        this.description = description;
        this.id = id;
        this.comments = comments;
    }

    public Card(String name, String description,int id){
        this.name = name;
        this.description = description;
        this.id = id;
        this.comments = new ArrayList<Comment>();
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

    void setId(int id){
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId(){ return id; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        return id == card.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Card name: " + name + "\nDescription: " + description + "\n";
    }
}
