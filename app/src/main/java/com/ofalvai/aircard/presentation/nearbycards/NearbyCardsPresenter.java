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
                    "Address",
                    "Coordinates",
                    "http://example.com",
                    "Hello World note",
                    new ArrayList<>(Arrays.asList(testCustomFields)),
                    "Roboto",
                    "ffffff"
            ),

            new Card(
                    "John Doe",
                    "+36201234567",
                    "mail@example.com",
                    "Address",
                    "Coordinates",
                    "http://example.com",
                    "Hello World note",
                    new ArrayList<>(Arrays.asList(testCustomFields)),
                    "Roboto",
                    "ffffff"
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
