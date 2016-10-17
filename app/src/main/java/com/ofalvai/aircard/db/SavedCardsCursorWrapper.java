package com.ofalvai.aircard.db;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.ofalvai.aircard.db.SavedCardDbSchema.CardTable;
import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.model.CardStyle;

public class SavedCardsCursorWrapper extends CursorWrapper {

    public SavedCardsCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Card getSavedCard() {
        String uuid = getStringRecord(CardTable.Cols.UUID);
        String name = getStringRecord(CardTable.Cols.NAME);
        String phone = getStringRecord(CardTable.Cols.PHONE);
        String mail = getStringRecord(CardTable.Cols.MAIL);
        String address = getStringRecord(CardTable.Cols.ADDRESS);
        String url = getStringRecord(CardTable.Cols.URL);
        String note = getStringRecord(CardTable.Cols.NOTE);
        CardStyle cardStyle = CardStyle.fromString(getStringRecord(CardTable.Cols.CARD_STYLE));
        String color = getStringRecord(CardTable.Cols.COLOR);
        long timestampSaved = getLongRecord(CardTable.Cols.TIMESTAMP_SAVE);

        Card card = new Card(uuid, name, phone, mail, address, url, note, cardStyle, color);
        card.setTimestampSaved(timestampSaved);
        return card;
    }

    private String getStringRecord(String columnName) {
        return getString(getColumnIndexOrThrow(columnName));
    }

    private long getLongRecord(String columnName) {
        return getLong(getColumnIndexOrThrow(columnName));
    }
}
