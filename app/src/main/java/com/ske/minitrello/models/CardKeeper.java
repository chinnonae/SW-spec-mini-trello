package com.ske.minitrello.models;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Load and store data (cards, card lists, comments, etc.).
 * Created by chinnonae on 29-Feb-16.
 */
public class CardKeeper {

	/** a collection of card lists. */
	private List<CardList> cardLists;
	/** an instance of CardKeeper. */
	static private CardKeeper instance = null;
	/** Database manager. */
	private DBHelper helper;
	/** DB error handler */
	private DefaultDatabaseErrorHandler dbHandler;
	/** Database */
	private SQLiteDatabase db;
	/** New card id. */
	private static int cardId = 1000000;
	/** SharedPreference. */
	private SharedPreferences sharedPref;
	/** SharedPreference editor. */
	private SharedPreferences.Editor prefEditor;
	private Map<String, CardList> cardListMap;
	private Map<Integer, Card> cardMap;


	/**
	 * Constructor.
	 */
	private CardKeeper(Context context) {
		cardLists = new ArrayList<CardList>();
		cardListMap = new HashMap<String, CardList>();
		cardMap = new HashMap<Integer, Card>();
		dbHandler = new DefaultDatabaseErrorHandler();
		helper = new DBHelper(context, dbHandler);
		db = helper.getWritableDatabase();

		sharedPref = context.getSharedPreferences("card_id", Context.MODE_PRIVATE);
		prefEditor = sharedPref.edit();
		if(sharedPref.getInt("currentID", -1) == -1) {
			prefEditor.putInt("currentID", cardId);
			prefEditor.commit();
		}
		else
			cardId = sharedPref.getInt("currentID", -1);
		loadData();

	}

	/**
	 * Return an instance of CardKeeper.
	 * @return an instance of CardKeeper.
	 */
	public static CardKeeper getInstance(Context context) {
		if (instance == null) instance = new CardKeeper(context);
		return instance;
	}

	/**
	 * Return an instance of CardKeeper. (This can be invoke only after getInstance(Context) is invoked).
	 * Otherwise, this will return null value.
	 * @return an instance of CardKeeper, or null if getInstance(Context) hasn't been invoked.
	 */
	public static CardKeeper getInstance() {
		return instance;
	}

	/**
	 * Return all the card lists exist.
	 * @return all the card lists exist.
	 */
	public List<CardList> getLists(){
		return cardLists;
	}

	public int getSize() {
		return cardLists.size();
	}

	/**
	 * Add card list to the collection.
	 * @param cardList a new card list.
	 */
	public void addCardList(CardList cardList) {
		cardLists.add(cardList);
		insertCardList(cardList);
	}

	/**
	 * Add card to its parent card list.
	 * @param card a card to be added to card list.
	 * @param cardList a list that will hold the card.
	 */
	public void addCardToCardList(Card card, CardList cardList) {
		cardList.addCard(card);
		insertCard(card, cardList);
	}

	/**
	 * Add comment to a card.
	 * @param comment a comment to be added to a card.
	 * @param card a card that is commented.
	 */
	public void addCommentToCard(Comment comment, Card card) {
		card.addComment(comment);
		insertComment(comment, card);
	}

	/**
	 * Delete cardList from the lists.
	 * @param cardList to be deleted.
	 */
	public void deleteCardList(CardList cardList){
		cardListMap.remove(cardList.getName());
		cardLists.remove(cardList);
		deleteCardListFromDB(cardList);
	}

	/**
	 * Delete cardList from the lists.
	 * @param name is name of the cardList which will be deleted.
	 */
	public void deleteCardList(String name){
		CardList temp = cardListMap.remove(name);
		cardLists.remove(temp);
		deleteCardListFromDB(temp);
	}

	/**
	 * Delete a card with id.
	 * @param cardId is id of the card which will be deleted.
	 */
	public void deleteCard(int cardId){
		Card temp = cardMap.remove(cardId);
		String cardListName = getParentCardList(temp.getId());
		cardListMap.get(cardListName).removeCard(temp);
		db.delete("CARD", "_id=?", new String[]{"" + temp.getId()});
	}

	/**
	 * Delete a card.
	 * @param card to be deleted.
	 */
	public void deleteCard(Card card){
		cardMap.remove(card.getId());
		String cardListName = getParentCardList(card.getId());
		cardListMap.get(cardListName).removeCard(card);
		db.delete("CARD","_id=?", new String[]{""+card.getId()});
	}



	/**
	 * Delete a comment.
	 * @param comment to be deleted.
	 */
	public void deleteComment(Comment comment){
		Cursor cursor = db.query("COMMENT", new String[]{"card_id"}, "date_created=?", new String[]{comment.getCreatedTime()+""}, null, null ,null);
		cursor.moveToNext();
		int card_id = cursor.getInt(0);
		Card card = cardMap.get(card_id);
		card.deleteComment(comment);
		db.delete("COMMENT", "date_created=? description=?", new String[]{comment.getCreatedTime()+"", comment.getContent()});

	}

	/**
	 * Rename a cardlist.
	 * @param newName is a new name of the cardlist.
	 * @param cardList to be renamed.
	 */
	public void renameCardList(String newName, CardList cardList){
		String oldName = cardList.getName();
		ContentValues val = new ContentValues();
		val.put("_name", newName);
		db.update("CARD_LIST", val, "_name=?", new String[]{oldName});
		cardList.setName(newName);
	}


    /**
     * Rename the title of a card.
     * @param newTitle is a new title of a card.
     * @param card is a card which title is renamed.
     */
	public void renameCardTitle(String newTitle, Card card){
		ContentValues val = new ContentValues();
		val.put("title", newTitle);
		db.update("CARD", val, "_id=?", new String[]{""+card.getId()});
        card.setName(newTitle);
	}

	private String getParentCardList(int cardId){
		Cursor cursor = db.query("CARD", new String[]{"parent_list"}, "_id=?" , new String[]{""+cardId}, null, null, null);
		String name = "test";
		cursor.moveToNext();
		if(cursor.isAfterLast()){
			return null;
		}else{
			name = cursor.getString(0);
		}
		return name;
	}

	private void insertCardList(CardList cardList) {
		String name = cardList.getName();
		int index = -1;
		ContentValues val = new ContentValues(2);
		val.put("_name", name);
		val.put("list_index", index);
		db.insert("CARD_LIST", null, val);
		Log.d("CardKeeper", "InsertCardList: " + "{ name: " + name + ", index: " + index + " }");
		cardListMap.put(name, cardList);
	}

	private void insertCard(Card card, CardList cardList) {
		String title = card.getName();
		String parent_list = cardList.getName();
		if(card.getId() < 0) card.setId(cardId);
		int index = -1;
		String description = card.getDescription();
		ContentValues val = new ContentValues(5);
		val.put("_id", cardId);
		val.put("title", title);
		val.put("parent_list", parent_list);
		val.put("card_index", index);
		val.put("description", description);
		db.insert("CARD", null, val);
		Log.d("CardKeeper", String.format("InsertCard: { title: %s, id: %d, parent_list: %s, card_index: %d, description: %s }", title, cardId, parent_list, index, description));
		prefEditor.putInt("currentID", ++cardId);
		prefEditor.commit();
		cardMap.put(card.getId(), card);
	}


	private void insertComment(Comment comment, Card card) {
		long createdTime = comment.getCreatedTime();
		String detail = comment.getContent();
		int id = card.getId();
		ContentValues val = new ContentValues(3);
		val.put("card_id", id);
		val.put("date_created", createdTime);
		val.put("detail", detail);
		db.insert("COMMENT", null, val);
		Log.d("CardKeeper", String.format("InsertComment: { card_id: %d, date_created: %s, detail: %s", id, comment.getCreatedTime(), comment.getContent()));

	}


	private void deleteCardListFromDB(CardList cardList){
		db.delete("CARD_LIST","_name=?", new String[]{cardList.getName()});
	}


	private void loadData() {
		loadCardLists();
		loadCards();
	}

	private void loadCardLists() {
		String[] col = {"_name", "list_index"};
		Cursor cursor = db.query("CARD_LIST", col, null,  null, null, null, null  );
		Log.d("CardKeeper", "LoadCardList-> " + "Cursor Column Count: " + cursor.getColumnCount());
		for (String s : cursor.getColumnNames()) {
			Log.d("CardKeeper", "LoadCardList-> Column names: " + s);
		}
		do {
			cursor.moveToNext();
			if (cursor.isAfterLast()) break;
			String name;
			int index;
			name = cursor.getString(cursor.getColumnIndex("_name"));
			index = cursor.getInt(cursor.getColumnIndex("list_index"));
			Log.d("CardKeeper", "LoadCardLists-> " + String.format("{ Name: %s, Index: %d", name, index));
			CardList newone = new CardList(name);
			cardLists.add(newone);
			cardListMap.put(name, newone);
		}while(!cursor.isLast());
	}

	private void loadCards() {
		String[] col = {"_id", "title", "parent_list", "card_index", "description"};
		Cursor cursor = db.query("CARD", col, null, null, null, null, null);
		Log.d("CardKeeper", "LoadCards-> " + "Cursor Column Count: " + cursor.getColumnCount());
		for (String s : cursor.getColumnNames()) {
			Log.d("CardKeeper", "LoadCards-> " + "Column names: " + s);
		}

		do {
			cursor.moveToNext();
			if(cursor.isAfterLast()) break;
			int id, index;
			String title, parent_list, description;
			id = cursor.getInt(cursor.getColumnIndex("_id"));
			title = cursor.getString(cursor.getColumnIndex("title"));
			index = cursor.getInt(cursor.getColumnIndex("card_index"));
			parent_list = cursor.getString(cursor.getColumnIndex("parent_list"));
			description = cursor.getString(cursor.getColumnIndex("description"));
			Log.d("CardKeeper", "LoadCard-> " + String.format("{ ID: %d, Title: %s, Parent List: %s, Index: %d, Description: %s", id, title, parent_list, index, description));
			CardList parent = cardListMap.get(parent_list);
			Card card = new Card(title, description,id);
			parent.addCard(card);
			cardMap.put(id, card);
		}while(!cursor.isLast());
	}

	private void loadComments() {
		String[] col = {"card_id", "date_created", "detail"};
		Cursor cursor = db.query("COMMENT", col, null, null, null, null, null);
		Log.d("CardKeeper", String.format("LoadComment-> Cursor Column Count: %d", cursor.getColumnCount()));
		for (String s : cursor.getColumnNames()) {
			Log.d("CardKeeper", String.format(" LoadCards-> Column names: %s", s));
		}

		do {
			cursor.moveToNext();
			if(cursor.isAfterLast()) break;
			int card_id = cursor.getInt(cursor.getColumnIndex("card_id"));
			long createdTime = cursor.getLong((cursor.getColumnIndex("date_created")));
			String detail = cursor.getString(cursor.getColumnIndex("detail"));
			Log.d("CardKeeper", String.format("LoadComment-> { card_id: %d, date_created: %d, detail: %s", card_id, createdTime, detail));
			Card card = cardMap.get(card_id);
			card.addComment(new Comment(detail, createdTime));
		} while (!cursor.isLast());
	}

}
