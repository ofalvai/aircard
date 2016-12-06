package com.ofalvai.aircard.presentation.savedcards;

import android.content.Context;

import com.ofalvai.aircard.R;
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
    public void detachView() {
        super.detachView();
        if (mDbWrapper != null) {
            mDbWrapper.close();
        }
    }

    @Override
    public void getSavedCards() {
        checkViewAttached();
        try {
            List<Card> savedCards = mDbWrapper.getSavedCards();
            getView().showCards(savedCards);
        } catch (Exception ex) {
            getView().showError(mContext.getString(R.string.error_get_saved_cards));
        }
    }

    @Override
    public void deleteSavedCard(Card card) {
        mDbWrapper.deleteSavedCard(card.getUuid());
    }

    @Override
    public void searchSavedCards(String query) {
        checkViewAttached();
        List<Card> cards = new ArrayList<>();

        if (query.trim().length() > 0) {
            try {
                cards = mDbWrapper.searchAnywhere(query);
            } catch (Exception ex) {
                getView().showError(mContext.getString(R.string.error_search));
            }
        } else {
            try {
                cards = mDbWrapper.getSavedCards();
            } catch (Exception ex) {
                getView().showError(mContext.getString(R.string.error_get_saved_cards));
            }
        }

        getView().showCards(cards);
    }
}
