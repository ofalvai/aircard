package com.ofalvai.aircard.db;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.model.CardStyle;

public class MyCardsCursorWrapper extends CursorWrapper {

    public MyCardsCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Card getMyCard() {
        String uuid = getStringRecord(DbSchema.MyCardsTable.Cols.UUID);
        String name = getStringRecord(DbSchema.MyCardsTable.Cols.NAME);
        String phone = getStringRecord(DbSchema.MyCardsTable.Cols.PHONE);
        String mail = getStringRecord(DbSchema.MyCardsTable.Cols.MAIL);
        String address = getStringRecord(DbSchema.MyCardsTable.Cols.ADDRESS);
        String url = getStringRecord(DbSchema.MyCardsTable.Cols.URL);
        String note = getStringRecord(DbSchema.MyCardsTable.Cols.NOTE);
        CardStyle cardStyle = CardStyle.fromString(getStringRecord(DbSchema.MyCardsTable.Cols.CARD_STYLE));
        String color = getStringRecord(DbSchema.MyCardsTable.Cols.COLOR);
        String timestampCreated = getStringRecord(DbSchema.MyCardsTable.Cols.TIMESTAMP_CREATED);

        Card card = new Card(uuid, name, phone, mail, address, url, note, cardStyle, color);
        card.setTimestampSaved(timestampCreated);
        return card;
    }

    private String getStringRecord(String columnName) { //TODO: extract methods
        return getString(getColumnIndexOrThrow(columnName));
    }

    private long getLongRecord(String columnName) {
        return getLong(getColumnIndexOrThrow(columnName));
    }

}
