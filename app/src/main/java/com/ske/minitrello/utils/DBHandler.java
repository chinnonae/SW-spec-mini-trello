package com.ske.minitrello.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ske.minitrello.models.Card;
import com.ske.minitrello.models.CardList;
import com.ske.minitrello.models.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chinthiti Wisetsombat on 28-Mar-16.
 */
public class DBHandler {

    /** Database instance. */
    private SQLiteDatabase db;
    /** Database manager. */
    private DBHelper helper;
    /** DB error handler. */
    private DefaultDatabaseErrorHandler errorHandler;

    /**
     * Constructor.
     */
    public DBHandler(Context context){
        errorHandler = new DefaultDatabaseErrorHandler();
        helper = new DBHelper(context, errorHandler);
        db = helper.getWritableDatabase();
    }

    /**
     * Load comments belong to a card.
     * @param card is the Card object which comments belong to it are going to be loaded.
     * @return list of comments belong to a card.
     */
    public List<Comment> loadComments(Card card){
        return loadComments(card.getId());
    }

    /**
     * Load comments belong to a card.
     * @param cardID is the id of the card which comments belong to it are going to be loaded.
     * @return list of comments belong to a card.
     */
    public List<Comment> loadComments(int cardID){
        String[] col = {"date_created", "detail"};
        Cursor cursor = db.query("COMMENT", col, "card_id=?", new String[]{String.valueOf(cardID)}, null, null, null);
        Log.d("DBHadnler", String.format("LoadCommentOf %d", cardID));

        ArrayList<Comment> comments = new ArrayList<Comment>();
        do {
            cursor.moveToNext();
            if(cursor.isAfterLast()) break;

            long dateCreated = cursor.getLong(cursor.getColumnIndex("date_created"));
            String content = cursor.getString(cursor.getColumnIndex("detail"));
            Log.d("DBHadnler", String.format("LoadComment-> { card_id: %d, date_created: %d, detail: %s }", cardID, dateCreated, content));

            Comment comment = new Comment(content, dateCreated);
            comments.add(comment);
        } while (!cursor.isLast());

        return comments;
    }

    /**
     * Load cards belong to a card list.
     * @param cardList is the CardList object which cards belong to it are going to be loaded.
     * @return list of cards belong to a card list.
     */
    public List<Card> loadCards(CardList cardList){
        return loadCards(cardList.getName());
    }

    /**
     * Load cards belong to a card list.
     * @param cardListName is the name of a CardList object which cards belong to it are going to be loaded.
     * @return list of cards belong to a card list.
     */
    public List<Card> loadCards(String cardListName){
        String[] col = {"_id", "title", "card_index", "description"};
        Cursor cursor = db.query("CARD", col, "parent_list=?", new String[]{cardListName}, null, null, null);
        Log.d("DBHadnler", String.format("LoadCardsOf %s", cardListName));

        ArrayList<Card> cards = new ArrayList<Card>();
        do {
            cursor.moveToNext();
            if(cursor.isAfterLast()) break;

            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            int index = cursor.getInt(cursor.getColumnIndex("card_index"));
            String desc = cursor.getString(cursor.getColumnIndex("description"));
            Log.d("DBHadnler", String.format("LoadCard-> { card_id: %d, title: %s, parent_list: %s, index: %d, description: %s }", id, title, cardListName, index, desc));

            Card card = new Card(title, desc, loadComments(id), id);
            cards.add(card);

        }while(true);

        return cards;
    }

    /**
     * Load created card lists.
     * @return list of card lists.
     */
    public List<CardList> loadCardLists(){
        String[] col = {"_name", "list_index"};
        Cursor cursor = db.query("CARD_LIST", col, null, null, null, null, null);
        Log.d("DBHandler", "LoadCardLists");

        ArrayList<CardList> cardLists = new ArrayList<CardList>();
        do {
            cursor.moveToNext();
            if(cursor.isAfterLast()) break;

            String name = cursor.getString(cursor.getColumnIndex("_name"));
            int index = cursor.getInt(cursor.getColumnIndex("list_index"));
            Log.d("DBHadnler", String.format("LoadCardList-> { cardList_name: %s, index: %d }", name, index));

            CardList cardList = new CardList(name, loadCards(name));
            cardLists.add(cardList);
        } while (!cursor.isLast());

        return cardLists;

    }

    public void updateCard(int cardId, String col, String val){
        ContentValues contentValues = new ContentValues();
        contentValues.put(col, val);

        db.update("CARD",contentValues, "_id=?", new String[]{String.valueOf(cardId)});
    }

    public void updateCardList(String cardListName, String col, String val){
        ContentValues contentValues = new ContentValues();
        contentValues.put(col, val);

        db.update("CARD_LIST", contentValues, "_name=?", new String[]{cardListName});
    }

    public void deleteComment(long createTime){
        db.delete("COMMENT", "date_created=?", new String[]{String.valueOf(createTime)});
    }

    public void deleteCard(int cardId){
        db.delete("CARD", "_id=?", new String[]{String.valueOf(cardId)});
    }

    public void deleteCardList(String cardListName){
        db.delete("CARD_LIST", "_name=?", new String[]{cardListName});
    }

    public void deleteCommentsOf(int cardId){
        db.delete("COMMENT", "card_id=?", new String[]{String.valueOf(cardId)});
    }

    public void deleteCardsOf(String cardListName){
        db.delete("CARD", "parent_list=?", new String[]{cardListName});
    }

    public void insertComment(Card card, Comment comment){
        insertComment(card.getId(), comment);
    }

    public void insertComment(int cardId, Comment comment){
        insertComment(cardId, comment.getCreatedTime(), comment.getContent());
    }

    public void insertComment(int cardId, long createTime, String content){
        ContentValues vals = new ContentValues();
        vals.put("card_id", cardId);
        vals.put("date_created", createTime);
        vals.put("detail", content);

        db.insert("COMMENT", null, vals);
        Log.d("DBHandler", String.format("insertComment-> { card_id: %d. date_created: %d, detail: %s }", cardId, createTime, content));

    }

    public void insertCard(CardList cardList, Card card){
        insertCard(cardList.getName(), card);
    }

    public void insertCard(String cardListName, Card card){
        insertCard(cardListName, card.getId(), card.getName(), card.getDescription());
    }

    public void insertCard(String cardListName, int cardId, String cardName, String description){
        ContentValues vals = new ContentValues();
        vals.put("_id", cardId);
        vals.put("title", cardName);
        vals.put("description", description);
        vals.put("parent_list", cardListName);
        vals.put("card_index", -1);

        db.insert("CARD", null, vals);
        Log.d("DBHandler", String.format("insertCard-> { _id: %d, title: %s, description: %s, parent_list: %s, index: %d }", cardId, cardName, description, cardListName, -1));

    }

    public void insertCardList(CardList cardList){
        insertCardList(cardList.getName());
    }

    public void insertCardList(String cardListName){
        ContentValues vals = new ContentValues();
        vals.put("_name", cardListName);
        vals.put("list_index", -1);

        db.insert("CARD_LIST", null, vals);
        Log.d("DBHandler", String.format("insertCardList-> { name: %s, index: %d }" , cardListName, -1));

    }




}
