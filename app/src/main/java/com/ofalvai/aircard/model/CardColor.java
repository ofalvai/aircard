package com.ofalvai.aircard.model;

import android.support.annotation.Nullable;

public enum CardColor {

    DEFAULT, YELLOW, ORANGE, RED;

    /**
     * Returns a CardColor matching the input string value of a hexadecimal color (without #)
     * Used after reading color value from DB.
     *
     * Adding a new color involves extending the switch cases both in fromString() and toHexString()
     */
    public static CardColor fromString(@Nullable String stringValue) {
        if (stringValue == null) {
            return CardColor.DEFAULT;
        }

        switch (stringValue.toUpperCase()) {
            case "FFFFFF":
                return CardColor.DEFAULT;
            case "FFF9C4":
                return CardColor.YELLOW;
            case "FFE082":
                return CardColor.ORANGE;
            case "EF9A9A":
                return CardColor.RED;
            default:
                return CardColor.DEFAULT;
        }
    }

    public static boolean isValid(String colorValue) {
        return colorValue != null && !colorValue.isEmpty() && colorValue.length() == 6;
    }

    public static String toHexString(CardColor color) {
        switch (color) {
            case DEFAULT:
                return "FFFFFF";
            case YELLOW:
                return "FFF9C4";
            case ORANGE:
                return "FFE082";
            case RED:
                return "EF9A9A";
            default:
                return "FFFFFF";
        }
    }
}
