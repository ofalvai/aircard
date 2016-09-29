package com.ofalvai.aircard.presentation.nearbycards;

import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.model.CardStyle;
import com.ofalvai.aircard.presentation.base.BasePresenter;

import java.util.Arrays;

public class NearbyCardsPresenter extends BasePresenter<NearbyCardsContract.View>
        implements NearbyCardsContract.Presenter {

    private static final Card[] testCards = {
            new Card(
                    "John Doe",
                    "+36201234567",
                    "mail@example.com",
                    "1234 Példa út 15",
                    "http://example.com",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum gravida porttitor tortor molestie commodo. Vestibulum eget bibendum magna, imperdiet aliquet lacus.",
                    CardStyle.NORMAL,
                    ""
            ),

            new Card(
                    "John Doe",
                    "+36201234567",
                    "mail@example.com",
                    "5678 Minta körút 13",
                    "http://example.com",
                    "Praesent ac elementum nulla, id accumsan quam.",
                    CardStyle.NORMAL,
                    "FFF9C4"
            ),

            new Card(
                    "John Doe",
                    "+36201234567",
                    "mail@example.com",
                    "5678 Minta körút 13",
                    "http://example.com",
                    "",
                    CardStyle.NORMAL,
                    ""
            ),

            new Card(
                    "Ez hiányos",
                    "",
                    "",
                    "",
                    "",
                    "",
                    CardStyle.MONOSPACE,
                    ""
            ),

            new Card(
                    "Ilyet is",
                    "",
                    "mail@example.hu",
                    "",
                    "",
                    "",
                    CardStyle.MONOSPACE,
                    ""
            ),
            new Card(
                    "Ilyet is lehet",
                    "",
                    "",
                    "",
                    "",
                    "",
                    CardStyle.SERIF,
                    ""
    ),

    };

    @Override
    public void getTestCards() {
        checkViewAttached();
        getView().showCards(Arrays.asList(testCards));
    }

    @Override
    public void collect(Card card) {

    }

    @Override
    public void startListen() {

    }

    @Override
    public void stopListen() {

    }
}
