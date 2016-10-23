package com.ofalvai.aircard.presentation.mycards;

import android.content.Context;

import com.ofalvai.aircard.db.DbHelper;
import com.ofalvai.aircard.db.MyCardsDbWrapper;
import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.model.CardStyle;
import com.ofalvai.aircard.presentation.base.BasePresenter;

public class MyCardsPresenter extends BasePresenter<MyCardsContract.View>
        implements MyCardsContract.Presenter {

    private Context mContext;

    private MyCardsDbWrapper mDbWrapper;

    public MyCardsPresenter(Context context) {
        mContext = context;
        mDbWrapper = new MyCardsDbWrapper(new DbHelper(mContext));

        Card testCard = new Card(
                null,
                "John Doe",
                "+36201234567",
                "mail@example.com",
                "1234 Példa út 15",
                "http://example.com",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum gravida porttitor tortor molestie commodo. Vestibulum eget bibendum magna, imperdiet aliquet lacus.",
                CardStyle.NORMAL,
                ""
        );
        //testCard.setTimestampSaved(new DateTime().getMillis());
        //mDbWrapper.addMyCard(testCard);
    }


    @Override
    public void getMyCards() {

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
