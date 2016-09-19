package com.ofalvai.aircard.presentation.nearbycards;

import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.presentation.base.MvpPresenter;
import com.ofalvai.aircard.presentation.base.MvpView;

import java.util.List;

interface NearbyCardsContract {

    interface View extends MvpView {

        void showCards(List<Card> cards);

        void showError(String message);

        void showWarning(String message);

    }

    interface Presenter extends MvpPresenter<View> {

        void collect(Card card);

        void startListen();

        void stopListen();

    }
}
