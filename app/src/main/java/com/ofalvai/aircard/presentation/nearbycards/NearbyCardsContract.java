package com.ofalvai.aircard.presentation.nearbycards;

import android.support.v4.app.FragmentActivity;

import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.presentation.base.MvpPresenter;
import com.ofalvai.aircard.presentation.base.MvpView;

import java.util.List;

interface NearbyCardsContract {

    interface View extends MvpView {

        void showCards(List<Card> cards);

        void showNewCard(Card card);

        void removeCard(Card card);

        void showMessageCardAdded();

        void setStateSubscribing();

        void setStateNotSubscribing();

        void showSubscribeError(String message);

    }

    interface Presenter extends MvpPresenter<View> {

        void initNearby(FragmentActivity fragmentActivity);

        void save(Card card);

        /**
         * Starts listening for nearby cards
         */
        void subscribe();

        /**
         * Stops listening for nearby cards
         */
        void unsubscribe();

        boolean isSubscribed();

        void getDemoCards();

        void removeDemoCards();

    }
}
