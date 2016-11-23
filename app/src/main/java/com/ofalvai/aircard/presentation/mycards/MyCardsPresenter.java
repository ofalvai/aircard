package com.ofalvai.aircard.presentation.mycards;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
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
        implements MyCardsContract.Presenter, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "MyCardsPresenter";

    private Context mContext;

    private MyCardsDbWrapper mDbWrapper;

    private boolean mNearbyConnected = false;
    private GoogleApiClient mGoogleApiClient;

    private Set<Card> mPublishedCards;

    public MyCardsPresenter(Context context) {
        mContext = context;
        mDbWrapper = new MyCardsDbWrapper(new DbHelper(mContext));
        mPublishedCards = new HashSet<>();
    }

    @Override
    public void initNearby(FragmentActivity fragmentActivity) {
        if (!mNearbyConnected) {
            mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                    .addApi(Nearby.MESSAGES_API)
                    .enableAutoManage(fragmentActivity, this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
    }

    @Override
    public void getMyCards() {
        checkViewAttached();
        getView().showCards(mDbWrapper.getMyCards());
    }

    @Override
    public void deleteMyCard(Card card) {
        mDbWrapper.deleteMyCard(card.getUuid());

        // UI update is handled in the adapter
    }

    @Override
    public void newCard(Card card) {
        mDbWrapper.addMyCard(card);

        checkViewAttached();
        getView().showCards(mDbWrapper.getMyCards());
    }

    @Override
    public void publishCard(Card card) {
        Message message = Card.newNearbyMessage(card);
        Nearby.Messages.publish(mGoogleApiClient, message);

        mPublishedCards.add(card);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "onConnected: ");
        mNearbyConnected = true;
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "onConnectionSuspended: ");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "onConnectionFailed: ");
    }

    @Override
    public void unpublishCard(Card card) {
        mPublishedCards.remove(card);
    }

    @Override
    public boolean isCardPublished(Card card) {
        return mPublishedCards.contains(card);
    }

    @Override
    public void releaseNearbyResources() {
        //mNearbyConnectionManager.disconnect();
    }

    @Override
    public void pickCardColor(Card card) {
        checkViewAttached();
        getView().showColorPicker(card);
    }

    @Override
    public void updateCardColor(UUID uuid, CardColor color) {
        Card card = mDbWrapper.getMyCard(uuid);
        card.setColor(color);
        mDbWrapper.updateMyCard(card);
    }

    @Override
    public void pickCardStyle(Card card) {
        checkViewAttached();
        getView().showStylePicker(card);
    }

    @Override
    public void updateCardStyle(UUID uuid, CardStyle style) {
        Card card = mDbWrapper.getMyCard(uuid);
        card.setCardStyle(style);
        mDbWrapper.updateMyCard(card);
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
}
