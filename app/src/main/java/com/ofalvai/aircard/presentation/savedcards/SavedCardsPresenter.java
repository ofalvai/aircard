package com.ofalvai.aircard.presentation.savedcards;

import android.content.Context;
import android.util.Log;

import com.ofalvai.aircard.db.SavedCardsDbWrapper;
import com.ofalvai.aircard.db.DbHelper;
import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.model.CardColor;
import com.ofalvai.aircard.model.CardStyle;
import com.ofalvai.aircard.presentation.base.BasePresenter;

public class SavedCardsPresenter extends BasePresenter<SavedCardsContract.View>
        implements SavedCardsContract.Presenter {

    private final static String TAG = "SavedCardsPresenter";

    private Context mContext;

    private SavedCardsDbWrapper mDbWrapper;

    public SavedCardsPresenter(Context context) {
        mContext = context;
        mDbWrapper = new SavedCardsDbWrapper(new DbHelper(mContext));

        Card testCard = new Card(
                null,
                "John Doe",
                "+36201234567",
                "mail@example.com",
                "1234 Példa út 15",
                "http://example.com",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum gravida porttitor tortor molestie commodo. Vestibulum eget bibendum magna, imperdiet aliquet lacus.",
                CardStyle.NORMAL,
                CardColor.DEFAULT
        );
        //mDbWrapper.addSavedCard(testCard);
    }

    @Override
    public void getSavedCards() {
        checkViewAttached();
        getView().showCards(mDbWrapper.getSavedCards());
    }

    @Override
    public void deleteSavedCard(Card card) {
        if (card.getUuid() != null) {
            mDbWrapper.deleteSavedCard(card.getUuid());
        } else {
            Log.e(TAG, "Unable to delete card '" + card.getName() + "': missing UUID");
        }
    }
}
