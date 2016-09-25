package com.ofalvai.aircard.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ofalvai.aircard.db.SavedCardDbSchema.CardTable;

public class SavedCardHelper  extends SQLiteOpenHelper {

    private static final int VERSION = 1;

    private static final String DB_NAME = "saved_cards.db";

    public SavedCardHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format("CREATE TABLE %1$s (%2$s, %3$s, %4$s, %5$s, %6$s, %7$s, %8$s, %9$s, %10$s TIMESTAMP NOT NULL DEFAULT current_timestamp)",
                CardTable.TABLE_NAME, CardTable.Cols.NAME, CardTable.Cols.MAIL, CardTable.Cols.PHONE,
                CardTable.Cols.ADDRESS, CardTable.Cols.URL, CardTable.Cols.NOTE,
                CardTable.Cols.TYPEFACE, CardTable.Cols.COLOR, CardTable.Cols.TIMESTAMP_SAVE);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
