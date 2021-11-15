package com.paysky.momogrow;

import android.app.Application;

import androidx.lifecycle.ProcessLifecycleOwner;

import com.paysky.momogrow.data.local.AppDatabase;
import com.paysky.momogrow.utilis.PreferenceProcessor;

import java.util.Timer;

public class MyApplication extends Application {
    public static AppDatabase db;

    public void onCreate() {
        super.onCreate();
        PreferenceProcessor.Companion.getInstance((Application) getApplicationContext());
        db = AppDatabase.Companion.getInstance(getApplicationContext());
    }

}
