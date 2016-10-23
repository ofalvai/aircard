package com.ofalvai.aircard.db;

public class DbSchema {

    public static final class SavedCardsTable {

        public static final String TABLE_NAME = "saved_cards";

        public static final class Cols {

            public static final String UUID = "uuid";

            public static final String NAME = "name";

            public static final String MAIL = "mail";

            public static final String PHONE = "phone";

            public static final String ADDRESS = "address";

            public static final String URL = "url";

            public static final String NOTE = "note";

            public static final String CARD_STYLE= "card_style";

            public static final String COLOR = "color";

            public static final String TIMESTAMP_SAVE = "timestamp_save";
        }

        public static final String CREATE_QUERY = String.format("CREATE TABLE %1$s (%2$s, %3$s, %4$s, %5$s, %6$s, %7$s, %8$s, %9$s, %10$s DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, %11$s)",
                TABLE_NAME, Cols.NAME, Cols.MAIL, Cols.PHONE, Cols.ADDRESS, Cols.URL, Cols.NOTE,
                Cols.CARD_STYLE, Cols.COLOR, Cols.TIMESTAMP_SAVE, Cols.UUID);
    }

    public static final class MyCardsTable {

        public static final String TABLE_NAME = "my_cards";

        public static final class Cols {

            public static final String UUID = "uuid";

            public static final String NAME = "name";

            public static final String MAIL = "mail";

            public static final String PHONE = "phone";

            public static final String ADDRESS = "address";

            public static final String URL = "url";

            public static final String NOTE = "note";

            public static final String CARD_STYLE= "card_style";

            public static final String COLOR = "color";

            public static final String TIMESTAMP_CREATED = "timestamp_created";
        }

        public static final String CREATE_QUERY = String.format("CREATE TABLE %1$s (%2$s, %3$s, %4$s, %5$s, %6$s, %7$s, %8$s, %9$s, %10$s DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, %11$s)",
                TABLE_NAME, Cols.NAME, Cols.MAIL, Cols.PHONE, Cols.ADDRESS, Cols.URL, Cols.NOTE,
                Cols.CARD_STYLE, Cols.COLOR, Cols.TIMESTAMP_CREATED, Cols.UUID);
    }

}
