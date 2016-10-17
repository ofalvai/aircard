package com.ofalvai.aircard.presentation.nearbycards;

import android.content.Context;

import com.ofalvai.aircard.db.SavedCardDbWrapper;
import com.ofalvai.aircard.db.SavedCardHelper;
import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.model.CardStyle;
import com.ofalvai.aircard.presentation.base.BasePresenter;

import java.util.Arrays;

public class NearbyCardsPresenter extends BasePresenter<NearbyCardsContract.View>
        implements NearbyCardsContract.Presenter {

    private static final Card[] testCards = {
            new Card(
                    null,
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
                    null,
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
                    null,
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
                    null,
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
                    null,
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
                    null,
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

    private SavedCardDbWrapper mDbWrapper;

    private Context mContext;

    public NearbyCardsPresenter(Context context) {
        mContext = context;
        mDbWrapper = new SavedCardDbWrapper(new SavedCardHelper(mContext));
    }

    @Override
    public void getTestCards() {
        checkViewAttached();
        getView().showCards(Arrays.asList(testCards));
    }

    @Override
    public void save(Card card) {
        mDbWrapper.addSavedCard(card);
        checkViewAttached();
        getView().showMessageCardAdded();
    }

    @Override
    public void startListen() {

    }

    @Override
    public void stopListen() {

    }
}
