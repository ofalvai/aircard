package com.ofalvai.aircard.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ofalvai.aircard.db.DbSchema.SavedCardsTable;
import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.model.CardColor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SavedCardsDbWrapper {

    private SQLiteDatabase mDatabase;

    public SavedCardsDbWrapper(SQLiteOpenHelper openHelper) {
        mDatabase = openHelper.getWritableDatabase();
    }

    public static ContentValues getContentValues(Card card) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SavedCardsTable.Cols.UUID, card.getUuid().toString());
        contentValues.put(SavedCardsTable.Cols.NAME, card.getName());
        contentValues.put(SavedCardsTable.Cols.MAIL, card.getMail());
        contentValues.put(SavedCardsTable.Cols.PHONE, card.getPhone());
        contentValues.put(SavedCardsTable.Cols.ADDRESS, card.getAddress());
        contentValues.put(SavedCardsTable.Cols.URL, card.getUrl());
        contentValues.put(SavedCardsTable.Cols.NOTE, card.getNote());
        contentValues.put(SavedCardsTable.Cols.CARD_STYLE, String.valueOf(card.getCardStyle()));
        contentValues.put(SavedCardsTable.Cols.COLOR, CardColor.toHexString(card.getColor()));
        return contentValues;
    }

    public void addSavedCard(Card card) {
        ContentValues values = getContentValues(card);
        mDatabase.insert(SavedCardsTable.TABLE_NAME, null, values);
    }

    private SavedCardsCursorWrapper querySavedCards(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                SavedCardsTable.TABLE_NAME,
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

    public void deleteSavedCard(UUID uuid) {
        String[] whereArg = new String[] { uuid.toString() };
        mDatabase.delete(SavedCardsTable.TABLE_NAME, "uuid = ?", whereArg);
    }
}
