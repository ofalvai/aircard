package com.ofalvai.aircard.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ofalvai.aircard.db.SavedCardDbSchema.CardTable;
import com.ofalvai.aircard.model.Card;

import java.util.ArrayList;
import java.util.List;

public class SavedCardDbWrapper {

    private SQLiteDatabase mDatabase;

    public SavedCardDbWrapper(SQLiteOpenHelper openHelper) {
        mDatabase = openHelper.getWritableDatabase();
    }

    public static ContentValues getContentValues(Card card) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CardTable.Cols.NAME, card.getName());
        contentValues.put(CardTable.Cols.MAIL, card.getMail());
        contentValues.put(CardTable.Cols.PHONE, card.getPhone());
        contentValues.put(CardTable.Cols.ADDRESS, card.getAddress());
        contentValues.put(CardTable.Cols.URL, card.getUrl());
        contentValues.put(CardTable.Cols.NOTE, card.getNote());
        contentValues.put(CardTable.Cols.CARD_STYLE, String.valueOf(card.getCardStyle()));
        contentValues.put(CardTable.Cols.COLOR, card.getColor());
        return contentValues;
    }

    public void addSavedCard(Card card) {
        ContentValues values = getContentValues(card);
        mDatabase.insert(CardTable.TABLE_NAME, null, values);
    }

    private SavedCardsCursorWrapper querySavedCards(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CardTable.TABLE_NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new SavedCardsCursorWrapper(cursor);
    }

    public List<Card> getSavedCards() {
        List<Card> cards = new ArrayList<>();

        SavedCardsCursorWrapper cursor = querySavedCards(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                cards.add(cursor.getSavedCard());
                cursor.moveToNext();
            }
        } finally {
            //TODO: exception
            cursor.close();
        }

        return cards;
    }
}
