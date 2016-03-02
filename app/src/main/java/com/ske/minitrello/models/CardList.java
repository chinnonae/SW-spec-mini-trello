package com.ske.minitrello.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class CardList {

    private String name;
    private List<Card> cards;

    /**
     * Create an empty card list.
     * @param name is name of card list
     */
    public CardList(String name) {
        this.name = name;
        cards = new ArrayList<Card>();
    }

    /**
     * Create a card list with specify cards.
     * @param name is name of card list
     * @param cards is list of cards in the card list
     */
    public CardList(String name, List<Card> cards) {
        this.name = name;
        this.cards = cards;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public String getName() {
        return name;
    }

    public List<Card> getCards() {
        return cards;
    }
}
