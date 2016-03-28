package com.ske.minitrello.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class CardList implements Serializable {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CardList cardList = (CardList) o;

        return name.equals(cardList.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
