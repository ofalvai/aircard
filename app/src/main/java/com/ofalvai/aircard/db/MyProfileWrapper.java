package com.ofalvai.aircard.db;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.ofalvai.aircard.model.MyProfileInfo;

/**
 * Fetches user profile information from the Contacts content provider
 */
public class MyProfileWrapper {

    private Context mContext;

    private interface DataKinds {
        String MIMETYPE = ContactsContract.Data.MIMETYPE;

        String MAIL = ContactsContract.CommonDataKinds.Email.ADDRESS;

        String PHONE = ContactsContract.CommonDataKinds.Phone.NUMBER;

        String NAME = ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME;

        String ADDRESS = ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS;

        String[] PROJECTION = {MIMETYPE, MAIL, PHONE, NAME, ADDRESS};
    }

    public MyProfileWrapper(Context context) {
        mContext = context;
    }

    public MyProfileInfo getMyProfileInfo() {
        MyProfileInfo myProfileInfo = new MyProfileInfo();

        // Profile info is stored in ContactsContract.Profile, but the actual data entries are in a
        // Data table
        final Uri myProfileDataUri = Uri.withAppendedPath(
                ContactsContract.Profile.CONTENT_URI,
                ContactsContract.Contacts.Data.CONTENT_DIRECTORY
        );

        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver().query(
                    myProfileDataUri,
                    DataKinds.PROJECTION,
                    null,
                    null,
                    null
            );

            if (cursor == null) {
                throw new RuntimeException("Failed to query Contacts Content Provider");
            }

            myProfileInfo = parseResults(cursor);

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return myProfileInfo;
    }

    private String getString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndexOrThrow(columnName));
    }

    /**
     * Fetches the required entries from the cursor of the content provider.
     * Entries in a Contacts provider Data table are stored in DATA[1-15] columns, so we use the
     * MIMETYPE column to recognize each row.
     */
    private MyProfileInfo parseResults(Cursor cursor) {
        MyProfileInfo myProfileInfo = new MyProfileInfo();

        cursor.moveToFirst();

        String mimeType;
        while (!cursor.isAfterLast()) {
            mimeType = getString(cursor, DataKinds.MIMETYPE);

            switch (mimeType) {
                case ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE:
                    myProfileInfo.mail = getString(cursor, DataKinds.MAIL);
                    break;

                case ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE:
                    myProfileInfo.phone = getString(cursor, DataKinds.PHONE);
                    break;

                case ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE:
                    myProfileInfo.name = getString(cursor, DataKinds.NAME);
                    break;

                case ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE:
                    myProfileInfo.address = getString(cursor, DataKinds.ADDRESS);
                    break;
            }

            cursor.moveToNext();
        }

        return myProfileInfo;
    }
}
