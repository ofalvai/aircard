package com.ofalvai.aircard.util;

import com.ofalvai.aircard.model.Card;

import java.util.Comparator;

/**
 * Sorts cards in REVERSE ORDER by timestamp
 */
public class CardTimestampComparator implements Comparator<Card> {

    @Override
    public int compare(Card o1, Card o2) {
        int card1Timestamp = (int) o1.getTimestampSaved();
        int card2Timestamp = (int) o2.getTimestampSaved();
        return card2Timestamp - card1Timestamp;
    }
}
