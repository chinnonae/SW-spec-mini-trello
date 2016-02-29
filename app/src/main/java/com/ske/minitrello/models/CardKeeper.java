package com.ske.minitrello.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chinnonae on 29-Feb-16.
 */
public class CardKeeper {

    /** a collection of card lists. */
    private ArrayList<CardList> cardLists;

    /** an instance of CardKeeper. */
    static private CardKeeper instance = null;

    /**
     * Constructor.
     */
    private CardKeeper(){
        cardLists = new ArrayList<CardList>();
    }

    /**
     * Return an instance of CardKeeper.
     * @return an instance of CardKeeper.
     */
    public static CardKeeper getInstance(){
        if(instance==null) instance = new CardKeeper();
        return instance;
    }

    /**
     * Return all the card lists exist.
     * @return all the card lists exist.
     */
    public List<CardList> getLists(){
        return cardLists;
    }

    /**
     * Add card list to the collection.
     * @param cardList a new card list.
     */
    public void addCardList(CardList cardList){
        cardLists.add(cardList);
    }

    // TODO: implement delete a card list
    // TODO: rearrange index?
    // TODO:
}
