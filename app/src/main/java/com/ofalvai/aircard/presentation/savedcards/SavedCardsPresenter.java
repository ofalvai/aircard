package com.ofalvai.aircard.presentation.savedcards;

import android.content.Context;

import com.ofalvai.aircard.R;
import com.ofalvai.aircard.db.DbHelper;
import com.ofalvai.aircard.db.SavedCardsDbWrapper;
import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.presentation.base.BasePresenter;
import com.ofalvai.aircard.util.CardTimestampComparator;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;

public class SavedCardsPresenter extends BasePresenter<SavedCardsContract.View>
        implements SavedCardsContract.Presenter, SavedCardsDbWrapper.GetSavedCardsListener {

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
    public void onSavedCardsLoaded(List<Card> cards) {
        Collections.sort(cards, new CardTimestampComparator());
        getView().showCards(cards);
    }

    @Override
    public void onSavedCardsError(Exception ex) {
        getView().showError(mContext.getString(R.string.error_get_saved_cards));
    }

    @Override
    public void getSavedCards() {
        checkViewAttached();
        try {
            mDbWrapper.getSavedCards(new WeakReference<SavedCardsDbWrapper.GetSavedCardsListener>(this));
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

        if (query.trim().length() > 0) {
            try {
                List<Card> cards = mDbWrapper.searchAnywhere(query);
                getView().showCards(cards);
            } catch (Exception ex) {
                getView().showError(mContext.getString(R.string.error_search));
            }
        } else {
            try {
                mDbWrapper.getSavedCards(new WeakReference<SavedCardsDbWrapper.GetSavedCardsListener>(this));
            } catch (Exception ex) {
                getView().showError(mContext.getString(R.string.error_get_saved_cards));
            }
        }
    }
}
