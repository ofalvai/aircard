package com.ofalvai.aircard.model;

import android.support.annotation.Nullable;

public enum CardStyle {

    NORMAL, MONOSPACE, SERIF;

    /**
     * Returns a CardStyle matching the input string value. Used after reading typeface value from DB
     */
    public static CardStyle fromString(@Nullable String stringValue) {
        if (stringValue == null) {
            return CardStyle.NORMAL;
        }

        switch (stringValue.toLowerCase()) {
            case "normal":
                return CardStyle.NORMAL;
            case "monospace":
                return CardStyle.MONOSPACE;
            case "serif":
                return CardStyle.SERIF;
            default:
                return CardStyle.NORMAL;
        }
    }
}
