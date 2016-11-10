package com.ofalvai.aircard.presentation.nearbycards;

import android.content.Context;

import com.ofalvai.aircard.db.SavedCardsDbWrapper;
import com.ofalvai.aircard.db.DbHelper;
import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.model.CardColor;
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
                    CardColor.DEFAULT
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
                    CardColor.YELLOW
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
                    CardColor.DEFAULT
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
                    CardColor.ORANGE
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
                    CardColor.DEFAULT
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
                    CardColor.DEFAULT
            ),
    };

    private SavedCardsDbWrapper mDbWrapper;

    private Context mContext;

    public NearbyCardsPresenter(Context context) {
        mContext = context;
        mDbWrapper = new SavedCardsDbWrapper(new DbHelper(mContext));
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
