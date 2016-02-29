package com.ske.minitrello.models;

import java.util.ArrayList;
import java.util.List;


public class CardList {

    private String name;
    private List<Card> cardList;

    /**
     * Create an empty card list.
     * @param name is name of card list
     */
    public CardList(String name) {
        this.name = name;
        cardList = new ArrayList<Card>();
    }

    /**
     * Create a card list with specify cards.
     * @param name is name of card list
     * @param cardList is list of cards in the card list
     */
    public CardList(String name, List<Card> cardList) {
        this.name = name;
        this.cardList = cardList;
    }

    public void addCard(Card card) {
        cardList.add(card);
    }

    public String getName() {
        return name;
    }

    public List<Card> getCardList() {
        return cardList;
    }
}
