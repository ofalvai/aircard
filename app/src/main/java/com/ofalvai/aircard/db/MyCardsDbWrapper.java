package com.ofalvai.aircard.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.model.CardColor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MyCardsDbWrapper {

    private SQLiteDatabase mDatabase;

    public MyCardsDbWrapper(SQLiteOpenHelper openHelper) {
        mDatabase = openHelper.getWritableDatabase();
    }

    public void close() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

    public void addMyCard(Card card) {
        ContentValues values = getContentValues(card);
        mDatabase.insert(DbSchema.MyCardsTable.TABLE_NAME, null, values);
    }

    public List<Card> getMyCards() {
        List<Card> cards = new ArrayList<>();

        MyCardsCursorWrapper cursor = null;
        try {
            cursor = queryMyCards(null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                cards.add(cursor.getMyCard());
                cursor.moveToNext();
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return cards;
    }

    @Nullable
    public Card getMyCard(UUID uuid) {
        Card result = null;
        String[] whereArg = new String[] { uuid.toString() };
        MyCardsCursorWrapper cursor = null;
        try {
            cursor = queryMyCards(DbSchema.MyCardsTable.Cols.UUID + " = ?", whereArg);
            cursor.moveToFirst();
            result = cursor.getMyCard();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return result;
    }

    public void deleteMyCard(UUID uuid) {
        String[] whereArg = new String[] { uuid.toString() };
        mDatabase.delete(DbSchema.MyCardsTable.TABLE_NAME, DbSchema.MyCardsTable.Cols.UUID + " = ?", whereArg);
    }

    public void updateMyCard(Card card) {
        ContentValues values = getContentValues(card);
        String[] whereArg = new String[] { card.getUuid().toString() };
        mDatabase.update(DbSchema.MyCardsTable.TABLE_NAME, values, DbSchema.MyCardsTable.Cols.UUID + " = ?", whereArg);
    }

    /**
     * Note: make sure to call close() on the returned CursorWrapper!
     */
    private MyCardsCursorWrapper queryMyCards(String whereClause, String[] whereArgs) {
        @SuppressLint("Recycle")
        Cursor cursor = mDatabase.query(
                DbSchema.MyCardsTable.TABLE_NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new MyCardsCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Card card) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.MyCardsTable.Cols.UUID, card.getUuid().toString());
        contentValues.put(DbSchema.MyCardsTable.Cols.NAME, card.getName());
        contentValues.put(DbSchema.MyCardsTable.Cols.MAIL, card.getMail());
        contentValues.put(DbSchema.MyCardsTable.Cols.PHONE, card.getPhone());
        contentValues.put(DbSchema.MyCardsTable.Cols.ADDRESS, card.getAddress());
        contentValues.put(DbSchema.MyCardsTable.Cols.URL, card.getUrl());
        contentValues.put(DbSchema.MyCardsTable.Cols.NOTE, card.getNote());
        contentValues.put(DbSchema.MyCardsTable.Cols.CARD_STYLE, String.valueOf(card.getCardStyle()));
        contentValues.put(DbSchema.MyCardsTable.Cols.COLOR, CardColor.toHexString(card.getColor()));
        return contentValues;
    }

}
