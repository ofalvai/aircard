package com.ofalvai.aircard.db;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.ofalvai.aircard.db.DbSchema.SavedCardsTable;
import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.model.CardColor;
import com.ofalvai.aircard.model.CardStyle;

public class SavedCardsCursorWrapper extends CursorWrapper {

    public SavedCardsCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Card getSavedCard() {
        String uuid = getStringRecord(SavedCardsTable.Cols.UUID);
        String name = getStringRecord(SavedCardsTable.Cols.NAME);
        String phone = getStringRecord(SavedCardsTable.Cols.PHONE);
        String mail = getStringRecord(SavedCardsTable.Cols.MAIL);
        String address = getStringRecord(SavedCardsTable.Cols.ADDRESS);
        String url = getStringRecord(SavedCardsTable.Cols.URL);
        String note = getStringRecord(SavedCardsTable.Cols.NOTE);
        CardStyle cardStyle = CardStyle.fromString(getStringRecord(SavedCardsTable.Cols.CARD_STYLE));
        CardColor color = CardColor.fromString(getStringRecord(SavedCardsTable.Cols.COLOR));
        String timestampSaved = getStringRecord(SavedCardsTable.Cols.TIMESTAMP_SAVE);

        Card card = new Card(uuid, name, phone, mail, address, url, note, cardStyle, color);
        card.setTimestampSaved(timestampSaved);
        return card;
    }

    private String getStringRecord(String columnName) {
        return getString(getColumnIndexOrThrow(columnName));
    }
}
