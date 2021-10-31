package com.paysky.momogrow.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.paysky.momogrow.R

class AfterSplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_splash)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MobileNumberActivity::class.java))
            finish()
        }, 1500)
    }
}