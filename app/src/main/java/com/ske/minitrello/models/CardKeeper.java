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
    private int cardId = 1000000;

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor prefEditor;
    private DBHandler dbHandler;

    private Map<String, CardList> cardListMap;
    private Map<Integer, Card> cardMap;
    private Map<Integer, CardList> cardParent;
    private Map<Comment, Card> commentToCardMap;

    private static CardKeeper instance;

    private CardKeeper(Context context){
        cardListMap = new HashMap<String, CardList>();
        cardMap = new HashMap<Integer, Card>();
        cardParent = new HashMap<Integer, CardList>();
        commentToCardMap = new HashMap<Comment, Card>();
        dbHandler = new DBHandler(context);

        sharedPref = context.getSharedPreferences("card_id", Context.MODE_PRIVATE);
        prefEditor = sharedPref.edit();
        if(sharedPref.getInt("curentID", -1) == -1){
            prefEditor.putInt("currentID", cardId);
            prefEditor.commit();
        }
        else{
            cardId = sharedPref.getInt("currentID", -1);
        }
        loadOnInit();
    }

    private void loadOnInit(){
        cardLists = dbHandler.loadCardLists();
        for(CardList cardList : cardLists){
            cardListMap.put(cardList.getName(), cardList);
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

    public void addCardList(CardList cardList){
        cardLists.add(cardList);
        cardListMap.put(cardList.getName(), cardList);

        dbHandler.insertCardList(cardList);
    }

    public void addCardToCardList(Card card, CardList cardList){
        cardList.addCard(card);
        cardParent.put(card.getId(), cardList);

        dbHandler.insertCard(cardList, card);
    }

    public void addCommentToCard(Comment comment, Card card){
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

        for(Comment comment : card.getComments()){
            deleteComment(comment);
        }

        cardMap.remove(card.getId());
        CardList cardList = cardParent.remove(card.getId());
        cardList.removeCard(card);

    }

    public void deleteCardList(CardList cardList){
        dbHandler.deleteCardList(cardList.getName());
        for(Card card : cardList.getCards()){
            deleteCard(card);
        }
        cardLists.remove(cardList);
        cardListMap.remove(cardList);

    }

    public void renameCardList(CardList cardList, String newName){
        cardList.setName(newName);

        dbHandler.updateCardList(cardList.getName(), CardList.DBColumn.name.toString(), newName);
    }

    public void renameCard(Card card, String newName){
        card.setName(newName);

        dbHandler.updateCard(card.getId(), Card.DBColumn.name.toString() , newName);
    }
}
