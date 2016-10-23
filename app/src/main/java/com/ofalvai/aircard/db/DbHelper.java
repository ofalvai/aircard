package com.ofalvai.aircard.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;

    private static final String DB_NAME = "cards.db";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Saved cards
        String savedCardsQuery = DbSchema.SavedCardsTable.CREATE_QUERY;
        db.execSQL(savedCardsQuery);

        // My cards
        String myCardsQuery = DbSchema.MyCardsTable.CREATE_QUERY;
        db.execSQL(myCardsQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
