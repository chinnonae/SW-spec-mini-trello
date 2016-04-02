package com.ske.minitrello.utils;

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
        super(context, "mini-trello-db", null, 11, dbErrorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS CARD_LIST( " +
                        "_list_id int primary key not null," +
                        "name text not null," +
                        "list_index int not null" +
                        ");"
        );

        db.execSQL("CREATE TABLE IF NOT EXISTS CARD( " +
                        "_id int primary key not null," +
                        "title text not null," +
                        "parent_list int not null," +
                        "card_index int not null," +
                        "description text," +
                        "FOREIGN KEY(parent_list) REFERENCES CARD_LIST(_list_id)" +
                        ");"
        );
        db.execSQL("CREATE TABLE IF NOT EXISTS COMMENT( " +
                        "card_id int not null," +
                        "date_created int not null," +
                        "detail text not null," +
                        "PRIMARY KEY(card_id, date_created)" +
                        "FOREIGN KEY(card_id) REFERENCES CARD(_id)" +
                        ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS COMMENT");
        db.execSQL("DROP TABLE IF exists card_list");
        db.execSQL("DROP table if exists card");
        onCreate(db);
    }
}
