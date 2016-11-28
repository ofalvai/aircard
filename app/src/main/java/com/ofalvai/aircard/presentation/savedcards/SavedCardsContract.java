package com.ofalvai.aircard.presentation.savedcards;

import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.presentation.base.MvpPresenter;
import com.ofalvai.aircard.presentation.base.MvpView;

import java.util.List;

interface SavedCardsContract {

    interface View extends MvpView {

        void showCards(List<Card> cards);

        /**
         * Used for externally asking the view to get the latest data.
         * Currently MainPagerAdapter calls this to update the view every time it gets selected.
         */
        void refreshSavedCards();
    }

    interface Presenter extends MvpPresenter<View> {

        void getSavedCards();

        void deleteSavedCard(Card card);

        void searchSavedCards(String query);
    }
}
