package com.paysky.momogrow;

import android.app.Application;

import androidx.lifecycle.ProcessLifecycleOwner;

import com.paysky.momogrow.utilis.PreferenceProcessor;

import java.util.Timer;

public class MyApplication extends Application {
    public void onCreate() {
        super.onCreate();
        PreferenceProcessor.Companion.getInstance((Application) getApplicationContext());
    }
}
