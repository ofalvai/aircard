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
        String name = getString(getColumnIndex(CardTable.Cols.NAME));
        String phone = getString(getColumnIndex(CardTable.Cols.PHONE));
        String mail = getString(getColumnIndex(CardTable.Cols.MAIL));
        String address = getString(getColumnIndex(CardTable.Cols.ADDRESS));
        String url = getString(getColumnIndex(CardTable.Cols.URL));
        String note = getString(getColumnIndex(CardTable.Cols.NOTE));
        CardStyle cardStyle = CardStyle.fromString(getString(getColumnIndex(CardTable.Cols.CARD_STYLE)));
        String color = getString(getColumnIndex(CardTable.Cols.COLOR));
        long timestampSaved = getLong(getColumnIndex(CardTable.Cols.TIMESTAMP_SAVE));

        Card card = new Card(name, phone, mail, address, url, note, cardStyle, color);
        card.setTimestampSaved(timestampSaved);
        return card;
    }
}
