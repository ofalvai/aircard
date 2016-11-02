package com.ofalvai.aircard.presentation.mycards;

import android.content.Context;

import com.ofalvai.aircard.db.DbHelper;
import com.ofalvai.aircard.db.MyCardsDbWrapper;
import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.presentation.base.BasePresenter;

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
}
