package com.ofalvai.aircard.presentation.nearbycards;

import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.model.CustomField;
import com.ofalvai.aircard.presentation.base.BasePresenter;

import java.util.ArrayList;
import java.util.Arrays;

public class NearbyCardsPresenter extends BasePresenter<NearbyCardsContract.View>
        implements NearbyCardsContract.Presenter {

    private static final CustomField[] testCustomFields = {
            new CustomField("Custom label", "Custom value"),
            new CustomField("Twitter", "@example")
    };

    private static final Card[] testCards = {
            new Card(
                    "John Doe",
                    "+36201234567",
                    "mail@example.com",
                    "1234 Példa út 15",
                    "Coordinates",
                    "http://example.com",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum gravida porttitor tortor molestie commodo. Vestibulum eget bibendum magna, imperdiet aliquet lacus.",
                    new ArrayList<>(Arrays.asList(testCustomFields)),
                    1,
                    ""
            ),

            new Card(
                    "John Doe",
                    "+36201234567",
                    "mail@example.com",
                    "5678 Minta körút 13",
                    "Coordinates",
                    "http://example.com",
                    "Praesent ac elementum nulla, id accumsan quam.",
                    new ArrayList<>(Arrays.asList(testCustomFields)),
                    3,
                    "FFF9C4"
            ),

            new Card(
                    "John Doe",
                    "+36201234567",
                    "mail@example.com",
                    "5678 Minta körút 13",
                    "Coordinates",
                    "http://example.com",
                    "",
                    new ArrayList<>(Arrays.asList(testCustomFields)),
                    1,
                    ""
            ),

            new Card(
                    "Ez elég hiányos",
                    "",
                    "",
                    "",
                    "Coordinates",
                    "",
                    "",
                    new ArrayList<>(Arrays.asList(testCustomFields)),
                    1,
                    ""
            )
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
