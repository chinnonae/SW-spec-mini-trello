package com.ske.minitrello.models;


public class Card {

    private String name;
    private String description;

    public Card(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Card name: " + name + "\nDescription: " + description + "\n";
    }
}
