package com.paysky.momogrow.views.more

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.paysky.momogrow.R

class AccountDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_details)
    }

    fun finish(view: View) {
        finish()
    }
}