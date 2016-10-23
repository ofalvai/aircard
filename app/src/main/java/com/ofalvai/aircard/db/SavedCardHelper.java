package com.ofalvai.aircard.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ofalvai.aircard.db.DbSchema.SavedCardsTable;

public class SavedCardHelper  extends SQLiteOpenHelper {

    private static final int VERSION = 1;

    private static final String DB_NAME = "saved_cards.db";

    public SavedCardHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format("CREATE TABLE %1$s (%2$s, %3$s, %4$s, %5$s, %6$s, %7$s, %8$s, %9$s, %10$s DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, %11$s)",
                SavedCardsTable.TABLE_NAME, SavedCardsTable.Cols.NAME, SavedCardsTable.Cols.MAIL, SavedCardsTable.Cols.PHONE,
                SavedCardsTable.Cols.ADDRESS, SavedCardsTable.Cols.URL, SavedCardsTable.Cols.NOTE,
                SavedCardsTable.Cols.CARD_STYLE, SavedCardsTable.Cols.COLOR, SavedCardsTable.Cols.TIMESTAMP_SAVE, SavedCardsTable.Cols.UUID);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
