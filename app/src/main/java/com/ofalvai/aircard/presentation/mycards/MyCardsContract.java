package com.ofalvai.aircard.presentation.mycards;

import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.presentation.base.MvpPresenter;
import com.ofalvai.aircard.presentation.base.MvpView;

import java.util.List;

interface MyCardsContract {

    interface View extends MvpView {

        void showCards(List<Card> cards);
    }

    interface Presenter extends MvpPresenter<View> {

        void getMyCards();

        void deleteMyCard(Card card);

        void newCard(Card card);

        void publishCard(Card card);

        void unpublishCard(Card card);
    }
}
