package com.paysky.momogrow.views.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.paysky.momogrow.R

class PendigApprovalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pendig_approval)
    }

    fun back(view: View) {
        finish()
    }
}