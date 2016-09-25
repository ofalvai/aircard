package com.ofalvai.aircard.db;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.ofalvai.aircard.db.SavedCardDbSchema.CardTable;
import com.ofalvai.aircard.model.Card;

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
        int typeface = Integer.parseInt(getString(getColumnIndex(CardTable.Cols.TYPEFACE))); //TODO
        String color = getString(getColumnIndex(CardTable.Cols.COLOR));

        return new Card(name, phone, mail, address, null, url, note, null, typeface, color);

        //TODO: kelleni fog a timestamp_saved is
    }
}
