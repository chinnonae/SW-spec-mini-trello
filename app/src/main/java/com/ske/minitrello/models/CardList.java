package com.ske.minitrello.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class CardList implements Serializable {

    private String name;
    private List<Card> cards;

    public CardList(String name) {
        this.name = name;
        cards = new ArrayList<Card>();

    }

    public CardList(String name, List<Card> cards) {
        this.name = name;
        this.cards = cards;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }

    public String getName() {
        return name;
    }

    public List<Card> getCards() {
        return cards;
    }
}
