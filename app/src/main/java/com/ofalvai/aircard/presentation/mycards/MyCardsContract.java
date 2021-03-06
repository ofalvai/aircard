package com.ofalvai.aircard.presentation.mycards;

import android.support.v4.app.FragmentActivity;

import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.model.CardColor;
import com.ofalvai.aircard.model.CardStyle;
import com.ofalvai.aircard.model.MyProfileInfo;
import com.ofalvai.aircard.presentation.base.MvpPresenter;
import com.ofalvai.aircard.presentation.base.MvpView;

import java.util.List;
import java.util.UUID;

interface MyCardsContract {

    interface View extends MvpView {

        void showCards(List<Card> cards);

        void showColorPicker(Card card);

        void showStylePicker(Card card);

        void showEditDialog(Card card);

        void showMyProfileInfo(MyProfileInfo info);

        void showMyProfileInfoError();

        void setCardStatePublished(UUID uuid);

        void setCardStateUnpublished(UUID uuid);

        void showPublishError(String message);

        void showError(String message);
    }

    interface Presenter extends MvpPresenter<View> {

        void getMyCards();

        void deleteMyCard(Card card);

        void newCard(Card card);

        void publishCard(Card card);

        void unpublishCard(Card card);

        boolean isCardPublished(Card card);

        void initNearby(FragmentActivity fragmentActivity);

        void pickCardColor(Card card);

        void updateCardColor(UUID uuid, CardColor color);

        void pickCardStyle(Card card);

        void updateCardStyle(UUID uuid, CardStyle style);

        /**
         * Delegates the display of edit dialog from the adapter to the view
         */
        void beginEditingCard(Card card);

        void editCard(Card card);

        void getMyProfileInfo();
    }
}
