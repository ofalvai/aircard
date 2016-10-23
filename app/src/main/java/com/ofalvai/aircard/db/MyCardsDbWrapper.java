package com.ofalvai.aircard.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ofalvai.aircard.model.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MyCardsDbWrapper {

    private SQLiteDatabase mDatabase;

    public MyCardsDbWrapper(SQLiteOpenHelper openHelper) {
        mDatabase = openHelper.getWritableDatabase();
    }

    public static ContentValues getContentValues(Card card) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.MyCardsTable.Cols.UUID, card.getUuid().toString());
        contentValues.put(DbSchema.MyCardsTable.Cols.NAME, card.getName());
        contentValues.put(DbSchema.MyCardsTable.Cols.MAIL, card.getMail());
        contentValues.put(DbSchema.MyCardsTable.Cols.PHONE, card.getPhone());
        contentValues.put(DbSchema.MyCardsTable.Cols.ADDRESS, card.getAddress());
        contentValues.put(DbSchema.MyCardsTable.Cols.URL, card.getUrl());
        contentValues.put(DbSchema.MyCardsTable.Cols.NOTE, card.getNote());
        contentValues.put(DbSchema.MyCardsTable.Cols.CARD_STYLE, String.valueOf(card.getCardStyle()));
        contentValues.put(DbSchema.MyCardsTable.Cols.COLOR, card.getColor());
        contentValues.put(DbSchema.MyCardsTable.Cols.TIMESTAMP_CREATED, card.getTimestampSaved()); //TODO: rename method and fields
        return contentValues;
    }

    public void addMyCard(Card card) {
        ContentValues values = getContentValues(card);
        mDatabase.insert(DbSchema.MyCardsTable.TABLE_NAME, null, values);
    }

    private MyCardsCursorWrapper queryMyCards(String whereClause, String[] whereArgs) {
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

    public List<Card> getMyCards() {
        List<Card> cards = new ArrayList<>();

        MyCardsCursorWrapper cursor = queryMyCards(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                cards.add(cursor.getMyCard());
                cursor.moveToNext();
            }
        } finally {
            //TODO: exception
            cursor.close();
        }

        return cards;
    }

    public void deleteMyCard(UUID uuid) {
        String[] whereArg = new String[] { uuid.toString() };
        mDatabase.delete(DbSchema.MyCardsTable.TABLE_NAME, "uuid = ?", whereArg);
    }

}
