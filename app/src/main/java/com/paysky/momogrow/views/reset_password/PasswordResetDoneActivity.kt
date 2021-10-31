package com.paysky.momogrow.views.reset_password

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.paysky.momogrow.R
import com.paysky.momogrow.views.login.LoginActivity

class PasswordResetDoneActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_reset_done)

    }

    fun back(view: View) {
        startActivity(Intent(this, LoginActivity::class.java))
    }
}