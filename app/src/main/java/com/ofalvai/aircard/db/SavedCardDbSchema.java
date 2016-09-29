package com.ofalvai.aircard.db;

public class SavedCardDbSchema {

    public static final class CardTable {
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
    }

}
