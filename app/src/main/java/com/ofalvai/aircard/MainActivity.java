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

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "MainActivity";

    private GoogleApiClient mGoogleApiClient;

    private Message mActiveMessage;

    private MessageListener mMessageListener;

    private TextView mDebugTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDebugTextView = (TextView) findViewById(R.id.debug_log);

        findViewById(R.id.clear_debug_log).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDebugTextView.setText("");
            }
        });

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Nearby.MESSAGES_API)
                .addConnectionCallbacks(this)
                .enableAutoManage(this, this)
                .build();

        mMessageListener = new MessageListener() {
            @Override
            public void onFound(Message message) {
                String messageAsString = new String(message.getContent());
                Log.i(TAG, "Found message: " + messageAsString);
                appendLog("Found message: " + messageAsString);
            }

            @Override
            public void onLost(Message message) {
                String messageAsString = new String(message.getContent());
                Log.i(TAG, "Lost sight of message: " + messageAsString);
                appendLog("Lost sight of message: " + messageAsString);
            }
        };
    }

    @Override
    public void onStop() {
        unpublish();
        unsubscribe();
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "onConnected: ");
        appendLog("onConnected()");

        int random = new Random().nextInt(1000);
        publish("Hello World #" + random);

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

    private void publish(String message) {
        Log.i(TAG, "Publishing message: " + message);
        appendLog("Publishing message: " + message);

        mActiveMessage = new Message(message.getBytes());
        Nearby.Messages.publish(mGoogleApiClient, mActiveMessage);
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
}
