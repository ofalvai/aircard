package com.ofalvai.aircard.presentation.savedcards;

import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.presentation.base.MvpPresenter;
import com.ofalvai.aircard.presentation.base.MvpView;

import java.util.List;

interface SavedCardsContract {

    interface View extends MvpView {
        void showCards(List<Card> cards);
    }

    interface Presenter extends MvpPresenter<View> {
        void getSavedCards();
    }
}
