package com.ske.minitrello.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.ske.minitrello.utils.DBHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Chinthiti Wisetsombat on 28-Mar-16.
 */
public class CardKeeper {

    /** a collection of card lists. */
    private List<CardList> cardLists;
    /** Current new card id. */
    private static int cardId = 1000000;
    /** Current new card list id. */
    private static int listId = 1000000;

    private SharedPreferences cardSharedPref;
    private SharedPreferences.Editor cardPrefEditor;
    private SharedPreferences listSharedPref;
    private SharedPreferences.Editor listPrefEditor;


    private DBHandler dbHandler;

    private Map<Integer, CardList> cardListMap;
    private Map<Integer, Card> cardMap;
    private Map<Integer, CardList> cardParent;
    private Map<Comment, Card> commentToCardMap;

    private static CardKeeper instance;

    private CardKeeper(Context context){
        cardListMap = new HashMap<Integer, CardList>();
        cardMap = new HashMap<Integer, Card>();
        cardParent = new HashMap<Integer, CardList>();
        commentToCardMap = new HashMap<Comment, Card>();
        dbHandler = new DBHandler(context);

        cardSharedPref = context.getSharedPreferences("card_id", Context.MODE_PRIVATE);
        cardPrefEditor = cardSharedPref.edit();
        if(cardSharedPref.getInt("currentID", -1) == -1){
            cardPrefEditor.putInt("currentID", cardId);
            cardPrefEditor.commit();
        }
        else{
            cardId = cardSharedPref.getInt("currentID", -1);
        }

        listSharedPref = context.getSharedPreferences("list_id", Context.MODE_PRIVATE);
        listPrefEditor = listSharedPref.edit();
        if(listSharedPref.getInt("currentID", -1) == -1){
            listPrefEditor.putInt("currentID", cardId);
            cardPrefEditor.commit();
        }
        else{
            listId = cardSharedPref.getInt("currentID", -1);
        }

        loadOnInit();
    }

    private void loadOnInit(){
        cardLists = dbHandler.loadCardLists();
        for(CardList cardList : cardLists){
            cardListMap.put(cardList.getId(), cardList);
            for(Card card : cardList.getCards()){
                cardParent.put(card.getId(), cardList);
                cardMap.put(card.getId(), card);
                for(Comment comment : card.getComments()){
                    commentToCardMap.put(comment, card);
                }
            }
        }
    }

    public static CardKeeper getInstance(Context context){
        if(instance == null) instance = new CardKeeper(context);
        return instance;
    }

    public static CardKeeper getInstance(){
        return instance;
    }

    public List<CardList> getLists() {
        return cardLists;
    }

    public int getSize(){
        return cardLists.size();
    }

    public void addCardList(CardList cardList){
        cardLists.add(cardList);
        if(cardList.getId() < 0){
            cardList.setId(listId);
        }
        cardListMap.put(cardList.getId(), cardList);


        dbHandler.insertCardList(cardList);
    }

    public void addCardToCardList(Card card, CardList cardList){
        cardList.addCard(card);
        if(card.getId() < 1000000){
            card.setId(this.cardId);

        }

        dbHandler.insertCard(cardList, card);

        cardParent.put(card.getId(), cardList);
        cardMap.put(card.getId(), card);

        cardPrefEditor.putInt("currentID", ++cardId);
        cardPrefEditor.commit();
    }

    public void addCommentToCard(Comment comment, Card card) {
        card.addComment(comment);
        commentToCardMap.put(comment, card);

        dbHandler.insertComment(card, comment);
    }

    public void deleteComment(Comment comment){
        dbHandler.deleteComment(comment.getCreatedTime());

        Card card = commentToCardMap.remove(comment);
        card.getComments().remove(comment);
    }

    public void deleteCard(Card card){
        dbHandler.deleteCard(card.getId());

        while(!card.getComments().isEmpty()){
            deleteComment(card.getComments().get(0));
        }

        cardMap.remove(card.getId());
        CardList cardList = cardParent.get(card.getId());
        cardList.removeCard(card);
        cardParent.remove(card.getId());

    }

    public void deleteCardList(CardList cardList){
        dbHandler.deleteCardList(cardList.getId());

        while(!cardList.getCards().isEmpty()){
            deleteCard(cardList.getCards().get(0));
        }

        cardLists.remove(cardList);
        cardListMap.remove(cardList);

    }

    public void renameCardList(CardList cardList, String newName){
        cardList.setName(newName);

        dbHandler.updateCardList(cardList.getId(), CardList.DBColumn.name.toString(), newName);
    }

    public void renameCard(Card card, String newName){
        card.setName(newName);

        dbHandler.updateCard(card.getId(), Card.DBColumn.name.toString() , newName);
    }

    public void editCardDescription(Card card, String description){
        card.setDescription(description);;

	    dbHandler.updateCard(card.getId(), Card.DBColumn.description.toString(), description);
    }
}
