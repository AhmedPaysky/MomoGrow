package com.paysky.momogrow.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.paysky.momogrow.R
import com.paysky.momogrow.utilis.Constants
import com.paysky.momogrow.utilis.PreferenceProcessor
import com.paysky.momogrow.views.home.HomeActivity
import com.paysky.momogrow.views.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({
//            if (PreferenceProcessor.getBool(Constants.Companion.Preference.IS_REGISTERED, false)) {
//                startActivity(Intent(this, LoginActivity::class.java))
//                finish()
//            } else if (PreferenceProcessor.getBool(
//                    Constants.Companion.Preference.IS_LOGIN,
//                    false
//                )
//            ) {
//                startActivity(Intent(this, HomeActivity::class.java))
//                finish()
//            } else {
                startActivity(Intent(this, AfterSplashActivity::class.java))
                finish()
//            }
        }, 1500)
    }
}