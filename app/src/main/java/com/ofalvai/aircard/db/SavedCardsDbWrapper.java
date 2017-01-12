package com.ofalvai.aircard.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;

import com.ofalvai.aircard.db.DbSchema.SavedCardsTable;
import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.model.CardColor;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SavedCardsDbWrapper {

    public interface GetSavedCardsListener {

        void onSavedCardsLoaded(List<Card> cards);

        void onSavedCardsError(Exception ex);

    }

    private SQLiteDatabase mDatabase;

    public SavedCardsDbWrapper(SQLiteOpenHelper openHelper) {
        mDatabase = openHelper.getWritableDatabase();
    }

    public void close() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

    public void addSavedCard(Card card) {
        ContentValues values = getContentValues(card);
        mDatabase.insert(SavedCardsTable.TABLE_NAME, null, values);
    }

    public void getSavedCards(final WeakReference<GetSavedCardsListener> listenerRef) {
        new AsyncTask<Void, Void, List<Card>>() {
            @Override
            protected List<Card> doInBackground(Void... params) {
                List<Card> cards = new ArrayList<>();

                SavedCardsCursorWrapper cursor = null;
                try {
                    cursor = querySavedCards(null, null);
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        cards.add(cursor.getSavedCard());
                        cursor.moveToNext();
                    }
                } catch(Exception ex) {
                    if (listenerRef.get() != null) {
                        listenerRef.get().onSavedCardsError(ex);
                    }
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }

                return cards;
            }

            @Override
            protected void onPostExecute(List<Card> cards) {
                if (listenerRef.get() != null) {
                    listenerRef.get().onSavedCardsLoaded(cards);
                }

                listenerRef.clear(); // Avoiding leaking Activities
            }
        }.execute();
    }

    public void deleteSavedCard(UUID uuid) {
        String[] whereArg = new String[] { uuid.toString() };
        mDatabase.delete(SavedCardsTable.TABLE_NAME, "uuid = ?", whereArg);
    }

    public List<Card> searchAnywhere(String query) {
        List<Card> cards = new ArrayList<>();

        // We use a raw SQL query, because the normal query method doesn't work with expressions
        // like this: LIKE %?%  (placeholders and % wildcards)

        String sql = "SELECT * FROM " + SavedCardsTable.TABLE_NAME + " WHERE " +
                SavedCardsTable.Cols.NAME + " LIKE ? OR " +
                SavedCardsTable.Cols.MAIL + " LIKE ? OR " +
                SavedCardsTable.Cols.PHONE + " LIKE ? OR " +
                SavedCardsTable.Cols.ADDRESS + " LIKE ? OR " +
                SavedCardsTable.Cols.URL + " LIKE ? OR " +
                SavedCardsTable.Cols.NOTE + " LIKE ? OR " +
                SavedCardsTable.Cols.TIMESTAMP_SAVE + " LIKE ?";

        String[] whereArgs = new String[] {
                "%" + query + "%", // NAME
                "%" + query + "%", // MAIL
                "%" + query + "%", // PHONE
                "%" + query + "%", // ADDRESS
                "%" + query + "%", // URL
                "%" + query + "%", // NOTE
                "%" + query + "%"  // TIMESTAMP_SAVE
        };

        SavedCardsCursorWrapper cursorWrapper = new SavedCardsCursorWrapper(
                mDatabase.rawQuery(sql, whereArgs)
        );

        try {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()) {
                cards.add(cursorWrapper.getSavedCard());
                cursorWrapper.moveToNext();
            }
        } finally {
            cursorWrapper.close();
        }

        return cards;
    }

    /**
     * Note: make sure to call close() on the returned CursorWrapper!
     */
    private SavedCardsCursorWrapper querySavedCards(String whereClause, String[] whereArgs) {
        @SuppressLint("Recycle")
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

    private static ContentValues getContentValues(Card card) {
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
}
