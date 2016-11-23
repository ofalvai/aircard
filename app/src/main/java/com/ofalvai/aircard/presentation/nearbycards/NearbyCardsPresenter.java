package com.ofalvai.aircard.presentation.nearbycards;

import android.content.Context;
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

public class NearbyCardsPresenter extends BasePresenter<NearbyCardsContract.View>
        implements NearbyCardsContract.Presenter {

    private static final String TAG = "NearbyCardsPresenter";

    private static final Card[] testCards = {
            new Card(
                    null,
                    "John Doe",
                    "+36201234567",
                    "mail@example.com",
                    "1234 Példa út 15",
                    "http://example.com",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum gravida porttitor tortor molestie commodo. Vestibulum eget bibendum magna, imperdiet aliquet lacus.",
                    CardStyle.NORMAL,
                    CardColor.DEFAULT
            ),

            new Card(
                    null,
                    "John Doe",
                    "+36201234567",
                    "mail@example.com",
                    "5678 Minta körút 13",
                    "http://example.com",
                    "Praesent ac elementum nulla, id accumsan quam.",
                    CardStyle.NORMAL,
                    CardColor.YELLOW
            ),

            new Card(
                    null,
                    "John Doe",
                    "+36201234567",
                    "mail@example.com",
                    "5678 Minta körút 13",
                    "http://example.com",
                    "",
                    CardStyle.NORMAL,
                    CardColor.DEFAULT
            ),

            new Card(
                    null,
                    "Ez hiányos",
                    "",
                    "",
                    "",
                    "",
                    "",
                    CardStyle.MONOSPACE,
                    CardColor.ORANGE
            ),

            new Card(
                    null,
                    "Ilyet is",
                    "",
                    "mail@example.hu",
                    "",
                    "",
                    "",
                    CardStyle.MONOSPACE,
                    CardColor.DEFAULT
            ),
            new Card(
                    null,
                    "Ilyet is lehet",
                    "",
                    "",
                    "",
                    "",
                    "",
                    CardStyle.SERIF,
                    CardColor.DEFAULT
            ),
    };

    private SavedCardsDbWrapper mDbWrapper;

    private Context mContext;

    @Nullable
    private NearbyConnectionManager mNearbyConnectionManager;

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
    public void startListen() {
        if (mNearbyConnectionManager != null) {
            final MessageListener listener = new MessageListener() {
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

            mNearbyConnectionManager.subscribe(listener);
        }
    }

    @Override
    public void stopListen() {

    }
}
