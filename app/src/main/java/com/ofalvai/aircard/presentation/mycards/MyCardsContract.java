package com.ofalvai.aircard.presentation.mycards;

import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.model.CardColor;
import com.ofalvai.aircard.presentation.base.MvpPresenter;
import com.ofalvai.aircard.presentation.base.MvpView;

import java.util.List;
import java.util.UUID;

interface MyCardsContract {

    interface View extends MvpView {

        void showCards(List<Card> cards);

        void showColorPicker(Card card);
    }

    interface Presenter extends MvpPresenter<View> {

        void getMyCards();

        void deleteMyCard(Card card);

        void newCard(Card card);

        void publishCard(Card card);

        void unpublishCard(Card card);

        void pickCardColor(Card card);

        void updateCardColor(UUID uuid, CardColor color);
    }
}
