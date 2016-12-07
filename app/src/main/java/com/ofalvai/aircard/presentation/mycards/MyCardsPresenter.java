package com.ofalvai.aircard.presentation.mycards;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.nearby.messages.Message;
import com.ofalvai.aircard.NearbyConnectionManager;
import com.ofalvai.aircard.R;
import com.ofalvai.aircard.db.DbHelper;
import com.ofalvai.aircard.db.MyCardsDbWrapper;
import com.ofalvai.aircard.db.MyProfileWrapper;
import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.model.CardColor;
import com.ofalvai.aircard.model.CardStyle;
import com.ofalvai.aircard.model.MyProfileInfo;
import com.ofalvai.aircard.presentation.base.BasePresenter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class MyCardsPresenter extends BasePresenter<MyCardsContract.View>
        implements MyCardsContract.Presenter, NearbyConnectionManager.PublishListener {

    private static final String TAG = "MyCardsPresenter";

    private Context mContext;

    private MyCardsDbWrapper mDbWrapper;

    @Nullable
    private NearbyConnectionManager mNearbyConnectionManager;

    private Set<Card> mPublishedCards;

    public MyCardsPresenter(Context context) {
        mContext = context;
        mDbWrapper = new MyCardsDbWrapper(new DbHelper(mContext));
        mPublishedCards = new HashSet<>();
    }

    @Override
    public void initNearby(FragmentActivity fragmentActivity) {
        mNearbyConnectionManager = NearbyConnectionManager.getInstanceForActivity(fragmentActivity);
    }

    @Override
    public void detachView() {
        super.detachView();

        NearbyConnectionManager.releaseInstance();

        if (mDbWrapper != null) {
            mDbWrapper.close();
        }
    }

    @Override
    public void getMyCards() {
        checkViewAttached();
        getView().showCards(mDbWrapper.getMyCards());
    }

    @Override
    public void deleteMyCard(Card card) {
        checkViewAttached();
        try {
            mDbWrapper.deleteMyCard(card.getUuid());
        } catch (Exception ex) {
            getView().showError(mContext.getString(R.string.error_delete_my_card));
        }

        // UI update is handled in the adapter
    }

    @Override
    public void newCard(Card card) {
        checkViewAttached();
        try {
            mDbWrapper.addMyCard(card);
            getView().showCards(mDbWrapper.getMyCards());
        } catch (Exception ex) {
            getView().showError(mContext.getString(R.string.error_create_new_card));
        }
    }

    @Override
    public void publishCard(Card card) {
        Message message = Card.newNearbyMessage(card);

        if (mNearbyConnectionManager != null) {
            try {
                mNearbyConnectionManager.publish(message, this);
                mPublishedCards.add(card);
            } catch (Exception ex) {
                Log.e(TAG, ex.getMessage());
                checkViewAttached();
                getView().showPublishError(mContext.getString(R.string.error_publish));
            }
        } else {
            Log.e(TAG, "NearbyConnectionManager is not initialized");
        }
    }

    @Override
    public void unpublishCard(Card card) {
        Message message = Card.newNearbyMessage(card);

        if (mNearbyConnectionManager != null) {
            try {
                mNearbyConnectionManager.unpublish(message);
                mPublishedCards.remove(card);
            } catch (Exception ex) {
                Log.e(TAG, ex.getMessage());
                checkViewAttached();
                getView().showPublishError(mContext.getString(R.string.error_unpublish));
            }
        } else {
            Log.e(TAG, "NearbyConnectionManager is not initialized");
        }
    }

    @Override
    public boolean isCardPublished(Card card) {
        return mPublishedCards.contains(card);
    }

    @Override
    public void onPublishExpired(final Message message) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                UUID uuid = Card.fromNearbyMessage(message).getUuid();
                removeCardByUuid(uuid);

                if (isViewAttached()) {
                    // View might have been recreated after this runnable was posted to the UI thread
                    getView().setCardStateUnpublished(uuid);
                }
            }
        });
    }

    @Override
    public void onPublishSuccess(final Message message) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                UUID uuid = Card.fromNearbyMessage(message).getUuid();

                if (isViewAttached()) {
                    // View might have been recreated after this runnable was posted to the UI thread
                    getView().setCardStatePublished(uuid);
                }
            }
        });
    }

    @Override
    public void onPublishFailed(final Message message, final int statusCode, String statusMessage) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                UUID uuid = Card.fromNearbyMessage(message).getUuid();
                removeCardByUuid(uuid);

                if (isViewAttached()) {
                    // View might have been recreated after this runnable was posted to the UI thread
                    getView().setCardStateUnpublished(uuid);

                    String errorMessage;
                    if (statusCode == CommonStatusCodes.NETWORK_ERROR) {
                        errorMessage = mContext.getString(R.string.error_network);
                    } else if (statusCode == CommonStatusCodes.API_NOT_CONNECTED) {
                        errorMessage = mContext.getString(R.string.error_api_not_connected);
                    } else {
                        errorMessage = mContext.getString(R.string.error_publish);
                    }
                    getView().showPublishError(errorMessage);
                }
            }
        });
    }

    @Override
    public void pickCardColor(Card card) {
        checkViewAttached();
        getView().showColorPicker(card);
    }

    @Override
    public void updateCardColor(UUID uuid, CardColor color) {
        checkViewAttached();
        try {
            Card card = mDbWrapper.getMyCard(uuid);
            if (card != null) {
                card.setColor(color);
                mDbWrapper.updateMyCard(card);
            }
        } catch (Exception ex) {
            getView().showError(mContext.getString(R.string.error_my_card_color));
        }
    }

    @Override
    public void pickCardStyle(Card card) {
        checkViewAttached();
        getView().showStylePicker(card);
    }

    @Override
    public void updateCardStyle(UUID uuid, CardStyle style) {
        checkViewAttached();
        try {
            Card card = mDbWrapper.getMyCard(uuid);
            if (card != null) {
                card.setCardStyle(style);
                mDbWrapper.updateMyCard(card);
            }
        } catch (Exception ex) {
            getView().showError(mContext.getString(R.string.error_my_card_style));
        }
    }

    @Override
    public void beginEditingCard(Card card) {
        checkViewAttached();
        getView().showEditDialog(card);
    }

    @Override
    public void editCard(Card card) {
        checkViewAttached();
        try {
            mDbWrapper.updateMyCard(card);
        } catch (Exception ex) {
            getView().showError(mContext.getString(R.string.error_my_card_edit));
        }
    }

    @Override
    public void getMyProfileInfo() {
        try {
            MyProfileInfo info = new MyProfileWrapper(mContext).getMyProfileInfo();
            checkViewAttached();
            getView().showMyProfileInfo(info);
        } catch (Exception ex) {
            checkViewAttached();
            getView().showMyProfileInfoError();
        }
    }

    /**
     * Finds a matching card in the published cards set, and removes it.
     * Useful when a card object is not the same instance as the one stored in the set,
     * but they represent the same card (their uuid is the same)
     */
    private void removeCardByUuid(UUID uuid) {
        for (Card card : mPublishedCards) {
            if (card.getUuid().equals(uuid)) {
                mPublishedCards.remove(card);
            }
        }
    }
}
