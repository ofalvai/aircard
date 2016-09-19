/*
 * Copyright 2016 Olivér Falvai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.ofalvai.aircard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.android.gms.nearby.messages.SubscribeCallback;
import com.google.android.gms.nearby.messages.SubscribeOptions;
import com.ofalvai.aircard.model.Card;
import com.ofalvai.aircard.model.CustomField;
import com.ofalvai.aircard.presentation.CardAdapter;
import com.ofalvai.aircard.presentation.nearbycards.NearbyCardsActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "MainActivity";

    private GoogleApiClient mGoogleApiClient;

    private Message mActiveMessage;

    private MessageListener mMessageListener;

    private CardAdapter mNearbyCardsArrayAdapter;

    @BindView(R.id.debug_log)
    TextView mDebugTextView;

    @BindView(R.id.publish)
    Button mPublishButton;

    @BindView(R.id.nearby_cards_list)
    ListView mNearbyCardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        //mGoogleApiClient = new GoogleApiClient.Builder(this)
        //        .addApi(Nearby.MESSAGES_API)
        //        .addConnectionCallbacks(this)
        //        .enableAutoManage(this, this)
        //        .build();

        mMessageListener = new MessageListener() {
            @Override
            public void onFound(Message message) {
                Card card = Card.fromNearbyMessage(message);

                Log.i(TAG, "Found message: " + card.getName());
                appendLog("Found message: " + card.getName());

                //mNearbyCardsArrayAdapter.add(card);
            }

            @Override
            public void onLost(Message message) {
                Card card = Card.fromNearbyMessage(message);

                Log.i(TAG, "Lost sight of message: " + card.getName());
                appendLog("Lost sight of message: " + card.getName());

                //mNearbyCardsArrayAdapter.remove(card);
                //mNearbyCardsArrayAdapter.notifyDataSetChanged();
            }
        };

        //final List<Card> nearbyCardList = new ArrayList<>();
        //mNearbyCardsArrayAdapter = new CardAdapter(this, R.layout.list_item_card, nearbyCardList);
        //mNearbyCardList.setAdapter(mNearbyCardsArrayAdapter);
        //
        //mDebugTextView.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public void onStop() {
        //unpublish();
        //unsubscribe();
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "onConnected: ");
        appendLog("onConnected()");

        subscribe();

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "onConnectionSuspended: ");
        appendLog("onConnectionSuspended()");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "onConnectionFailed: ");
        appendLog("onConnectionFailed()");
    }

    @OnClick(R.id.clear_debug_log)
    void clearDebugLog() {
        mDebugTextView.setText("");
    }

    @OnClick(R.id.publish)
    public void publish() {
        mActiveMessage = newRandomCardMessage();
        Nearby.Messages.publish(mGoogleApiClient, mActiveMessage);

        Card card = Card.fromNearbyMessage(mActiveMessage);

        Log.i(TAG, "Publishing message: " + card.getCustomFields().get(0).getValue());
        appendLog("Publishing message: " + card.getCustomFields().get(0).getValue());
    }

    private Message newRandomCardMessage() {
        List<CustomField> customFields = new ArrayList<>();
        customFields.add(new CustomField("Random ID: ", String.valueOf(new Random().nextInt(1000))));

        return Card.newNearbyMessage(
                "Hello World",
                "+36201234567",
                "mail@example.com",
                "Address",
                "Coordinates",
                "http://example.com",
                "Hello World note",
                customFields,
                "Roboto",
                "ffffff"
        );
    }

    private void subscribe() {
        Log.i(TAG, "Subscribing.");
        appendLog("Subscribing...");

        SubscribeOptions options = new SubscribeOptions.Builder()
                .setCallback(new SubscribeCallback() {
                    @Override
                    public void onExpired() {
                        super.onExpired();
                        Log.i(TAG, "No longer subscribing");
                        appendLog("No linger subscribing (expired)");
                    }
                }).build();

        Nearby.Messages.subscribe(mGoogleApiClient, mMessageListener, options)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if (status.isSuccess()) {
                            Log.i(TAG, "Subscribed successfully.");
                            appendLog("Subscribed successfully.");
                        } else {
                            int code = status.getStatusCode();
                            Log.i(TAG, "Subscribe unsuccessful.");
                            appendLog("Subscribe unsuccessful (code: " + code + ")");
                        }
                    }
                });
    }

    private void unpublish() {
        appendLog("Unpublishing...");
        if (mActiveMessage != null) {
            Nearby.Messages.unpublish(mGoogleApiClient, mActiveMessage);
            mActiveMessage = null;
        }
    }

    private void unsubscribe() {
        appendLog("Unsubscribing...");
        Nearby.Messages.unsubscribe(mGoogleApiClient, mMessageListener);
    }

    private void appendLog(String message) {
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:MM:ss");
        String timestamp = simpleDateFormat.format(now);

        mDebugTextView.setText(mDebugTextView.getText() + "\n" + timestamp + "   " + message);
    }

    @OnClick(R.id.launch_nearby_cards)
    public void launchNearbyCardsActivity() {
        Intent intent = new Intent(MainActivity.this, NearbyCardsActivity.class);
        startActivity(intent);
    }


}
