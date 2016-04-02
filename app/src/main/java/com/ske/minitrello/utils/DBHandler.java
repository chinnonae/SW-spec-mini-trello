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
        return loadCards(cardList.getId());
    }

    /**
     * Load cards belong to a card list.
     * @param cardListId is the name of a CardList object which cards belong to it are going to be loaded.
     * @return list of cards belong to a card list.
     */
    public List<Card> loadCards(int cardListId){
        String[] col = {"_id", "title", "card_index", "description"};
        Cursor cursor = db.query("CARD", col, "parent_list=?", new String[]{String.valueOf(cardListId)}, null, null, null);
        Log.d("DBHadnler", String.format("LoadCardsOf %s", cardListId));

        ArrayList<Card> cards = new ArrayList<Card>();
        do {
            cursor.moveToNext();
            if(cursor.isAfterLast()) break;

            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            int index = cursor.getInt(cursor.getColumnIndex("card_index"));
            String desc = cursor.getString(cursor.getColumnIndex("description"));
            Log.d("DBHadnler", String.format("LoadCard-> { card_id: %d, title: %s, parent_list: %s, index: %d, description: %s }", id, title, cardListId, index, desc));

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
        String[] col = {"_list_id", "name", "list_index"};
        Cursor cursor = db.query("CARD_LIST", col, null, null, null, null, null);
        Log.d("DBHandler", "LoadCardLists");

        ArrayList<CardList> cardLists = new ArrayList<CardList>();
        do {
            cursor.moveToNext();
            if(cursor.isAfterLast()) break;

            int listId = cursor.getInt(cursor.getColumnIndex("_list_id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int index = cursor.getInt(cursor.getColumnIndex("list_index"));
            Log.d("DBHadnler", String.format("LoadCardList-> { list_id: %d, cardList_name: %s, index: %d }", listId, name, index));

            CardList cardList = new CardList(name, listId, loadCards(listId));
            cardLists.add(cardList);
        } while (!cursor.isLast());

        return cardLists;

    }



    public void updateCard(int cardId, String col, String val){
        ContentValues contentValues = new ContentValues();
        contentValues.put(col, val);

        db.update("CARD",contentValues, "_id=?", new String[]{String.valueOf(cardId)});
    }

    public void updateCardList(int Id, String col, String val){
        ContentValues contentValues = new ContentValues();
        contentValues.put(col, val);
        Log.d("UpdateCardList", String.format("id:%d %s %s", Id ,col, val));

        db.update("CARD_LIST", contentValues, "_list_id=?", new String[]{String.valueOf(Id)});
    }

    public void deleteComment(long createTime){
        db.delete("COMMENT", "date_created=?", new String[]{String.valueOf(createTime)});
    }

    public void deleteCard(int cardId){
        db.delete("CARD", "_id=?", new String[]{String.valueOf(cardId)});
    }

    public void deleteCardList(int listId){
        db.delete("CARD_LIST", "_list_id=?", new String[]{String.valueOf(listId)});
    }

    public void deleteCommentsOf(int cardId){
        db.delete("COMMENT", "card_id=?", new String[]{String.valueOf(cardId)});
    }

    public void deleteCardsOf(int listId){
        db.delete("CARD", "parent_list=?", new String[]{String.valueOf(listId)});
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
        insertCard(cardList.getId(), card);
    }

    public void insertCard(int listId, Card card){
        insertCard(listId, card.getId(), card.getName(), card.getDescription());
    }

    public void insertCard(int listId, int cardId, String cardName, String description){
        ContentValues vals = new ContentValues();
        vals.put("_id", cardId);
        vals.put("title", cardName);
        vals.put("description", description);
        vals.put("parent_list", listId);
        vals.put("card_index", -1);

        db.insert("CARD", null, vals);
        Log.d("DBHandler", String.format("insertCard-> { _id: %d, title: %s, description: %s, parent_list: %s, index: %d }", cardId, cardName, description, cardId, -1));

    }

    public void insertCardList(CardList cardList){
        insertCardList(cardList.getId(), cardList.getName());
    }

    public void insertCardList(int Id, String cardListId){
        ContentValues vals = new ContentValues();
        vals.put("name", cardListId);
        vals.put("list_index", -1);
        vals.put("_list_id", Id);

        db.insert("CARD_LIST", null, vals);
        Log.d("DBHandler", String.format("insertCardList-> { name: %s, index: %d }" , cardListId, -1));

    }




}
