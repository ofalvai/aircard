package com.ofalvai.aircard.presentation.mycards;

import android.content.Context;

import com.ofalvai.aircard.db.DbHelper;
import com.ofalvai.aircard.db.MyCardsDbWrapper;
import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.model.CardStyle;
import com.ofalvai.aircard.presentation.base.BasePresenter;

import java.util.Arrays;

public class MyCardsPresenter extends BasePresenter<MyCardsContract.View>
        implements MyCardsContract.Presenter {

    private Context mContext;

    private MyCardsDbWrapper mDbWrapper;

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
            )
    };

    public MyCardsPresenter(Context context) {
        mContext = context;
        mDbWrapper = new MyCardsDbWrapper(new DbHelper(mContext));
    }

    @Override
    public void getMyCards() {
        checkViewAttached();
        getView().showCards(Arrays.asList(testCards));
    }

    @Override
    public void deleteMyCard(Card card) {

    }

    @Override
    public void newCard() {

    }

    @Override
    public void publishCard(Card card) {

    }

    @Override
    public void unpublishCard(Card card) {

    }
}
