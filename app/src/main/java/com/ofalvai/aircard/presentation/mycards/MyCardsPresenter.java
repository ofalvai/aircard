package com.ofalvai.aircard.presentation.mycards;

import android.content.Context;

import com.ofalvai.aircard.db.DbHelper;
import com.ofalvai.aircard.db.MyCardsDbWrapper;
import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.model.CardColor;
import com.ofalvai.aircard.model.CardStyle;
import com.ofalvai.aircard.presentation.base.BasePresenter;

import java.util.UUID;

public class MyCardsPresenter extends BasePresenter<MyCardsContract.View>
        implements MyCardsContract.Presenter {

    private Context mContext;

    private MyCardsDbWrapper mDbWrapper;

    public MyCardsPresenter(Context context) {
        mContext = context;
        mDbWrapper = new MyCardsDbWrapper(new DbHelper(mContext));
    }

    @Override
    public void getMyCards() {
        checkViewAttached();
        getView().showCards(mDbWrapper.getMyCards());
    }

    @Override
    public void deleteMyCard(Card card) {
        mDbWrapper.deleteMyCard(card.getUuid());

        // UI update is handled in the adapter
    }

    @Override
    public void newCard(Card card) {
        mDbWrapper.addMyCard(card);

        checkViewAttached();
        getView().showCards(mDbWrapper.getMyCards());
    }

    @Override
    public void publishCard(Card card) {

    }

    @Override
    public void unpublishCard(Card card) {

    }

    @Override
    public void pickCardColor(Card card) {
        checkViewAttached();
        getView().showColorPicker(card);
    }

    @Override
    public void updateCardColor(UUID uuid, CardColor color) {
        Card card = mDbWrapper.getMyCard(uuid);
        card.setColor(color);
        mDbWrapper.updateMyCard(card);
    }

    @Override
    public void pickCardStyle(Card card) {
        checkViewAttached();
        getView().showStylePicker(card);
    }

    @Override
    public void updateCardStyle(UUID uuid, CardStyle style) {
        Card card = mDbWrapper.getMyCard(uuid);
        card.setCardStyle(style);
        mDbWrapper.updateMyCard(card);
    }
}
