package com.ofalvai.aircard.presentation.savedcards;

import android.content.Context;

import com.ofalvai.aircard.db.SavedCardDbWrapper;
import com.ofalvai.aircard.db.SavedCardHelper;
import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.model.CardStyle;
import com.ofalvai.aircard.presentation.base.BasePresenter;

public class SavedCardsPresenter extends BasePresenter<SavedCardsContract.View>
        implements SavedCardsContract.Presenter {

    private Context mContext;

    private SavedCardDbWrapper mDbWrapper;

    public SavedCardsPresenter(Context context) {
        mContext = context;
        mDbWrapper = new SavedCardDbWrapper(new SavedCardHelper(mContext));

        Card testCard = new Card(
                "John Doe",
                "+36201234567",
                "mail@example.com",
                "1234 Példa út 15",
                "http://example.com",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum gravida porttitor tortor molestie commodo. Vestibulum eget bibendum magna, imperdiet aliquet lacus.",
                CardStyle.NORMAL,
                ""
        );
        //mDbWrapper.addSavedCard(testCard);
    }

    @Override
    public void getSavedCards() {
        checkViewAttached();
        getView().showCards(mDbWrapper.getSavedCards());
    }
}
