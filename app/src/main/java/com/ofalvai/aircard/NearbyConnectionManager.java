package com.ofalvai.aircard;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.android.gms.nearby.messages.SubscribeCallback;
import com.google.android.gms.nearby.messages.SubscribeOptions;

/**
 * Manages a single connection to the GoogleApiClient, and provides methods for publishing,
 * unpublishing and subscribing to Nearby Messages.
 * GoogleApiClient needs a reference to a FragmentActivity in order to manage its own lifecycle
 * based on the activity's lifecycle. Therefore this class needs a reference to that
 * FragmentActivity, while being a Singleton class. This leads to memory leak if not handled
 * properly, so it's very important to call releaseInstance() when the dependent fragment/activity
 * is destroyed.
 *
 * (Yes, GoogleApiClient can be used without enableAutoManage(), but dealing manually with its
 * lifecycle, callbacks, and starting resolutions for failed connection attempts is even more painful
 * than being careful with using this class.)
 */
public class NearbyConnectionManager implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "NearbyConnectionManager";

    private static NearbyConnectionManager mInstance;

    private GoogleApiClient mGoogleApiClient;

    private SubscribeOptions mSubscribeOptions;

    /**
     * Use this method to obtain an instance, and don't forget to call releaseInstance() afterwards
     * @param fragmentActivity activity that is in the forground when using Nearby Messages
     * @return the single instance of the class
     */
    public static NearbyConnectionManager getInstanceForActivity(FragmentActivity fragmentActivity) {
        if (mInstance == null) {
            mInstance = new NearbyConnectionManager(fragmentActivity);
        }
        return mInstance;
    }

    /**
     * Don't forget to call this to avoid leaking an activity/context. This can be called even when
     * the instance is already released.
     */
    public static void releaseInstance() {
        if (mInstance != null) {
            mInstance = null;
        }
    }

    private NearbyConnectionManager(FragmentActivity fragmentActivity) {
        mGoogleApiClient = new GoogleApiClient.Builder(fragmentActivity)
                .addApi(Nearby.MESSAGES_API)
                .enableAutoManage(fragmentActivity, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mSubscribeOptions = createSubscribeOptions();
    }

    public void publish(Message message) {
        Nearby.Messages.publish(mGoogleApiClient, message);
    }

    public void unpublish(Message message) {
        Nearby.Messages.unpublish(mGoogleApiClient, message);
    }

    public void subscribe(MessageListener listener) {
        Log.i(TAG, "Subscribing.");

        Nearby.Messages.subscribe(mGoogleApiClient, listener, mSubscribeOptions)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if (status.isSuccess()) {
                            Log.i(TAG, "Subscribed successfully.");
                        } else {
                            int code = status.getStatusCode();
                            Log.i(TAG, "Subscribe unsuccessful (code: " + code + ")");
                        }
                    }
                });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "onConnected: ");

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "onConnectionSuspended: ");

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "onConnectionFailed: ");


    }

    private SubscribeOptions createSubscribeOptions() {
        return new SubscribeOptions.Builder()
                .setCallback(new SubscribeCallback() {
                    @Override
                    public void onExpired() {
                        super.onExpired();
                        Log.i(TAG, "No longer subscribing (expired)");
                    }
                }).build();
    }
}
