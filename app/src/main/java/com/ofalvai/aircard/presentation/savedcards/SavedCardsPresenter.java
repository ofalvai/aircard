package com.ofalvai.aircard.presentation.savedcards;

import android.content.Context;
import android.util.Log;

import com.ofalvai.aircard.db.DbHelper;
import com.ofalvai.aircard.db.SavedCardsDbWrapper;
import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.presentation.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

public class SavedCardsPresenter extends BasePresenter<SavedCardsContract.View>
        implements SavedCardsContract.Presenter {

    private final static String TAG = "SavedCardsPresenter";

    private Context mContext;

    private SavedCardsDbWrapper mDbWrapper;

    public SavedCardsPresenter(Context context) {
        mContext = context;
        mDbWrapper = new SavedCardsDbWrapper(new DbHelper(mContext));
    }

    @Override
    public void getSavedCards() {
        checkViewAttached();
        List<Card> savedCards = mDbWrapper.getSavedCards();
        getView().showCards(savedCards);
    }

    @Override
    public void deleteSavedCard(Card card) {
        if (card.getUuid() != null) {
            mDbWrapper.deleteSavedCard(card.getUuid());
        } else {
            Log.e(TAG, "Unable to delete card '" + card.getName() + "': missing UUID");
        }
    }

    @Override
    public void searchSavedCards(String query) {
        checkViewAttached();
        List<Card> cards = new ArrayList<>();

        if (query.trim().length() > 0) {
            cards = mDbWrapper.searchAnywhere(query);
        } else {
            cards = mDbWrapper.getSavedCards();
        }

        getView().showCards(cards);
    }
}
