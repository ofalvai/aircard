package com.ofalvai.aircard;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.android.gms.nearby.messages.PublishCallback;
import com.google.android.gms.nearby.messages.PublishOptions;
import com.google.android.gms.nearby.messages.SubscribeCallback;
import com.google.android.gms.nearby.messages.SubscribeOptions;

import static android.R.id.message;

/**
 * Manages a single connection to the GoogleApiClient, and provides methods for publishing,
 * unpublishing and subscribing to Nearby Messages.
 * GoogleApiClient needs a reference to a FragmentActivity in order to manage its own lifecycle
 * based on the activity's lifecycle. Therefore this class needs a reference to that
 * FragmentActivity, while being a Singleton class. This leads to memory leak if not handled
 * properly, so it's very important to call releaseInstance() when the dependent fragment/activity
 * is destroyed.
 * <p>
 * (Yes, GoogleApiClient can be used without enableAutoManage(), but dealing manually with its
 * lifecycle, callbacks, and starting resolutions for failed connection attempts is even more painful
 * than being careful with using this class.)
 */
public class NearbyConnectionManager implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    /**
     * Callbacks indicating a Message's publish state.
     * Note: these are not called on the UI thread.
     */
    public interface PublishListener {

        /**
         * Indicates that the message is no longer published, it's time to update the UI
         */
        void onPublishExpired(Message message);

        void onPublishSuccess(Message message);

        void onPublishFailed(Message messase, int statusCode, String statusMessage);
    }

    /**
     * Callbacks indicating a subscription's state. A subscription is identified by the
     * MessageListener passed to subscribe().
     * Note: these are not called from the UI thread.
     */
    public interface SubscribeListener {

        /**
         * Indicates that the client is no longer subscribing, it's time to update the UI
         */
        void onSubscribeExpired(MessageListener messageListener);

        void onSubscribeSuccess(MessageListener messageListener);

        void onSubscribeFailed(MessageListener messageListener, int statusCode, String statusMessage);

    }

    private static final String TAG = "NearbyConnectionManager";

    private static NearbyConnectionManager mInstance;

    private GoogleApiClient mGoogleApiClient;


    /**
     * Use this method to obtain an instance, and don't forget to call releaseInstance() afterwards
     *
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
    }

    public void publish(final Message message, final PublishListener listener) {
        Log.i(TAG, "Publishing...");

        if (!mGoogleApiClient.isConnected()) {
            listener.onPublishFailed(message, CommonStatusCodes.API_NOT_CONNECTED,
                    "GoogleApiClient is not connected");
            return;
        }

        final PublishOptions options = new PublishOptions.Builder()
                .setCallback(new PublishCallback() {
                    @Override
                    public void onExpired() {
                        Log.i(TAG, "Publish expired");
                        listener.onPublishExpired(message);
                    }
                })
                .build();

        PendingResult<Status> result = Nearby.Messages.publish(mGoogleApiClient, message, options);

        result.setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                Log.i(TAG, "Publish result: " + status.getStatusMessage());
                if (status.isSuccess()) {
                    listener.onPublishSuccess(message);
                } else {
                    listener.onPublishFailed(message, status.getStatusCode(), status.getStatusMessage());
                    Log.e(TAG, "Publish failure status code: " + status.getStatusCode());
                }
            }
        });
    }

    public void unpublish(Message message) {
        Log.i(TAG, "Unpublishing");

        if (!mGoogleApiClient.isConnected()) {
            Log.e(TAG, "Unpublish failed: GoogleApiClient not connected");
            return;
        }

        PendingResult<Status> result = Nearby.Messages.unpublish(mGoogleApiClient, message);

        result.setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                Log.i(TAG, "Unpublish result: " + status.getStatusMessage());
            }
        });
    }

    public void subscribe(final MessageListener messageListener, final SubscribeListener subscribeListener) {
        Log.i(TAG, "Subscribing...");

        if (!mGoogleApiClient.isConnected()) {
            subscribeListener.onSubscribeFailed(messageListener, CommonStatusCodes.API_NOT_CONNECTED,
                    "GoogleApiClient is not connected");
            return;
        }

        final SubscribeOptions options = new SubscribeOptions.Builder()
                .setCallback(new SubscribeCallback() {
                    @Override
                    public void onExpired() {
                        Log.i(TAG, "Subscribe expired");
                        subscribeListener.onSubscribeExpired(messageListener);
                    }
                }).build();

        PendingResult<Status> result = Nearby.Messages.subscribe(mGoogleApiClient, messageListener, options);

        result.setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                Log.i(TAG, "Subscribe result: " + status.getStatusMessage());
                if (status.isSuccess()) {
                    subscribeListener.onSubscribeSuccess(messageListener);
                } else {
                    Log.e(TAG, "Publish failure status code: " + status.getStatusCode());
                    subscribeListener.onSubscribeFailed(messageListener, status.getStatusCode(),
                            status.getStatusMessage());
                }
            }
        });
    }

    /**
     * Unsubscribe the given MessageListener from the Nearby API
     *
     * @param listener A MessageListener that was previously subscribed
     */
    public void unsubscribe(MessageListener listener) {
        Log.i(TAG, "Unsubscribing...");

        if (!mGoogleApiClient.isConnected()) {
            Log.e(TAG, "Unsubscribe failed: GoogleApiClient not connected");
            return;
        }

        PendingResult<Status> result = Nearby.Messages.unsubscribe(mGoogleApiClient, listener);

        result.setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {
                    Log.i(TAG, "Unsubscribed successfully.");
                } else {
                    int code = status.getStatusCode();
                    Log.i(TAG, "Unsubsribe unsuccessful (code: " + code + ")");
                }
            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "Connected successfully");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection suspended");
        // Connection failures are handled during share/publish calls
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed");
        // Connection failures are handled during share/publish calls
    }
}
