package com.ofalvai.aircard.presentation.nearbycards;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.ofalvai.aircard.NearbyConnectionManager;
import com.ofalvai.aircard.db.DbHelper;
import com.ofalvai.aircard.db.SavedCardsDbWrapper;
import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.model.CardColor;
import com.ofalvai.aircard.model.CardStyle;
import com.ofalvai.aircard.presentation.base.BasePresenter;

import java.util.ArrayList;
import java.util.Arrays;

public class NearbyCardsPresenter extends BasePresenter<NearbyCardsContract.View>
        implements NearbyCardsContract.Presenter, NearbyConnectionManager.SubscribeListener {

    private static final String TAG = "NearbyCardsPresenter";

    private static final Card[] mDemoCards = {
            new Card(
                    null,
                    "Demo card",
                    "776-2323",
                    "mail@example.com",
                    "Milky Way",
                    "example.com",
                    "These cards are for demo purposes only, and do not come from a nearby device.\nUse the options menu again to hide them.",
                    CardStyle.NORMAL,
                    CardColor.DEFAULT
            ),

            new Card(
                    null,
                    "Another demo card",
                    null,
                    "mail@example.com",
                    "",
                    "http://example.com",
                    "Praesent ac elementum nulla, id accumsan quam.",
                    CardStyle.SERIF,
                    CardColor.YELLOW
            )
    };

    private SavedCardsDbWrapper mDbWrapper;

    private Context mContext;

    @Nullable
    private NearbyConnectionManager mNearbyConnectionManager;

    /**
     * We need to keep a reference to a subscribed listener in order to unsubscribe it later.
     */
    @Nullable
    private MessageListener mMessageListener;

    public NearbyCardsPresenter(Context context) {
        mContext = context;
        mDbWrapper = new SavedCardsDbWrapper(new DbHelper(mContext));
    }

    @Override
    public void initNearby(FragmentActivity fragmentActivity) {
        mNearbyConnectionManager = NearbyConnectionManager.getInstanceForActivity(fragmentActivity);
    }

    @Override
    public void detachView() {
        super.detachView();

        NearbyConnectionManager.releaseInstance();
    }

    @Override
    public void save(Card card) {
        mDbWrapper.addSavedCard(card);
        checkViewAttached();
        getView().showMessageCardAdded();
    }

    @Override
    public void subscribe() {
        if (mNearbyConnectionManager != null) {
            mMessageListener = new MessageListener() {
                @Override
                public void onFound(Message message) {
                    Card card = Card.fromNearbyMessage(message);
                    checkViewAttached();
                    getView().showNewCard(card);
                }

                @Override
                public void onLost(Message message) {
                    super.onLost(message);
                    Card card = Card.fromNearbyMessage(message);
                    checkViewAttached();
                    getView().removeCard(card);
                }
            };

            mNearbyConnectionManager.subscribe(mMessageListener, this);
        }
    }

    @Override
    public void unsubscribe() {
        if (mNearbyConnectionManager != null && mMessageListener != null) {
            mNearbyConnectionManager.unsubscribe(mMessageListener);
            mMessageListener = null;
        }
    }

    @Override
    public boolean isSubscribed() {
        return mMessageListener != null;
    }

    @Override
    public void onSubscribeExpired(MessageListener messageListener) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                mMessageListener = null;
            }
        });
    }

    @Override
    public void onSubscribeSuccess(MessageListener messageListener) {

    }

    @Override
    public void onSubscribeFailed(MessageListener messageListener, int statusCode, String statusMessage) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                mMessageListener = null;
            }
        });
    }

    @Override
    public void getDemoCards() {
        checkViewAttached();
        getView().showCards(Arrays.asList(mDemoCards));
    }

    @Override
    public void removeDemoCards() {
        checkViewAttached();
        getView().showCards(new ArrayList<Card>());
    }
}
