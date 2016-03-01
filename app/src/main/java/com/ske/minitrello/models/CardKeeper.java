package com.ske.minitrello.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

/**
 * Load and store data (cards, card lists, comments, etc.).
 * Created by chinnonae on 29-Feb-16.
 */
public class CardKeeper {

    /** a collection of card lists. */
    private ArrayList<CardList> cardLists;
    /** an instance of CardKeeper. */
    static private CardKeeper instance = null;
    /** Database manager. */
    private DBHelper helper;
    /** DB error handler */
    private DefaultDatabaseErrorHandler dbHandler;
    /** Database */
    private SQLiteDatabase db;

    /**
     * Constructor.
     */
    private CardKeeper(Context context) {
        dbHandler = new DefaultDatabaseErrorHandler();
        helper = new DBHelper(context, dbHandler);
        db = helper.getWritableDatabase();

    }

    /**
     * Return an instance of CardKeeper.
     * @return an instance of CardKeeper.
     */
    public static CardKeeper getInstance(Context context){
        if(instance==null) instance = new CardKeeper(context);
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

    /**
     * Add card to its parent card list.
     * @param card a card to be added to card list.
     * @param cardList a list that will hold the card.
     */
    public void addCardToCardList(Card card, CardList cardList){
        //TODO: implement this
    }


    private void insertCardList(CardList cardList){
        String name = cardList.getName();
        int index = -1;
        ContentValues val = new ContentValues(2);
        val.put("_name", name);
        val.put("index", index);
        db.insert("CARD_LIST", null, val);
        Log.d("CardKeeper", "InsertCardList: "+"{ name: " + name + ", index: " + index + " }");
    }

    private void insertNewCard(Card card, CardList cardList){

    }

    //TODO: implement this method.
    private void addCommentToCard(){

    }

    private void testData(){
        ContentValues val = new ContentValues(2);
        val.put("_name", "test name");
        val.put("list_index", 0);
        db.insert("CARD_LIST", null, val);
        Log.d("add test", "add data completed");
    }

    private void loadData() {
        loadCardLists();
        loadCards();
    }

    private void loadCardLists(){
        String[] col = {"_name", "list_index"};
        Cursor cursor = db.query("CARD_LIST", col, null,  null, null, null, null  );
        Log.d("CardKeeper", "LoadCardList-> " + "Cursor Column Count: " + cursor.getColumnCount());
        for(String s : cursor.getColumnNames()){
            Log.d("CardKeeper", "LoadCardList-> Column names: " + s);
        }
        do {
            cursor.moveToNext();
            String name;
            int index;
            name = cursor.getString(cursor.getColumnIndex("_name"));
            index = cursor.getInt(cursor.getColumnIndex("list_index"));
            Log.d("CardKeeper", "LoadCardLists-> " + String.format("{ Name: %s, Index: %d", name, index));
            //create real cardList
        }while(!cursor.isLast());
    }

    private void loadCards(){
        String[] col = {"_id", "title", "parent_list", "index", "description"};
        Cursor cursor = db.query("CARD", col, null, null, null, null, null);
        Log.d("CardKeeper", "LoadCards-> " + "Cursor Column Count: " + cursor.getColumnCount());
        for(String s : cursor.getColumnNames()){
            Log.d("CardKeeper", "LoadCards-> " + "Column names: " + s);
        }
        do {
            cursor.moveToNext();
            int id, index;
            String title, parent_list, description;
            id = cursor.getInt(cursor.getColumnIndex("_id"));
            title = cursor.getString(cursor.getColumnIndex("title"));
            index = cursor.getInt(cursor.getColumnIndex("card_index"));
            parent_list = cursor.getString(cursor.getColumnIndex("parent_list"));
            description = cursor.getString(cursor.getColumnIndex("description"));
            Log.d("CardKeeper", "LoadCard-> " + String.format("{ ID: %d, Title: %s, Parent List: %s, Index: %d, Description: %s", id, title, parent_list, index, description));
            //add card to card list
        }while(!cursor.isLast());
    }


    // TODO: implement delete a card list
    // TODO: rearrange index?
    // TODO:
}
