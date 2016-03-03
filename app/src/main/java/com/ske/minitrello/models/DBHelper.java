package com.ske.minitrello.models;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by chinnonae on 01-Mar-16.
 */
public class DBHelper extends SQLiteOpenHelper{


    public DBHelper(Context context, DatabaseErrorHandler dbErrorHandler) {
        super(context, "mini-trello-db", null, 1, dbErrorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS CARD_LIST( " +
                        "_name text primary key not null," +
                        "list_index int not null" +
                        ");"
        );

        db.execSQL("CREATE TABLE IF NOT EXISTS CARD( " +
                        "_id int primary key not null," +
                        "title text not null," +
                        "parent_list text not null," +
                        "card_index int not null," +
                        "description text," +
                        "FOREIGN KEY(parent_list) REFERENCES CARD_LIST(_name)" +
                        ");"
        );
        db.execSQL("CREATE TABLE IF NOT EXISTS COMMENT( " +
                        "card_id int not null," +
                        "date_created int not null," +
                        "detail text not null," +
                        "PRIMARY KEY(card_id, date_created)" +
                        ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}