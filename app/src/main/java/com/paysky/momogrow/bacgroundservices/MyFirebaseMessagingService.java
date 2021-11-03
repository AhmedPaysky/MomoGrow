package com.paysky.momogrow.bacgroundservices;

import static com.paysky.momogrow.utilis.Constants.Companion.Preference.FIREBASE_TOKEN;
import static com.paysky.momogrow.utilis.Constants.Companion.Preference.RECEIVED_AUTH;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.paysky.momogrow.utilis.Constants;
import com.paysky.momogrow.utilis.PreferenceProcessor;


import timber.log.Timber;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMessagingServ";
    public static final String ACTION = "MY_INTENT_FILTER";

    @Override
    public void onNewToken(@NonNull String token) {
        Log.d(TAG, "Refreshed token " + token);

        PreferenceProcessor.Companion.getInstance((Application) getApplicationContext());
        PreferenceProcessor.Companion.setStr(FIREBASE_TOKEN, token);

    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "Notification Received");
        PreferenceProcessor.Companion.setBool(RECEIVED_AUTH, true);

        Intent intent = new Intent(ACTION);
        sendBroadcast(intent);
    }
}
