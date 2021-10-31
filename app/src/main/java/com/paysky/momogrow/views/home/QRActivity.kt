package com.paysky.momogrow.views.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.paysky.momogrow.R
import com.paysky.momogrow.databinding.ActivityAuthenticateBinding
import com.paysky.momogrow.databinding.ActivityQractivityBinding
import com.paysky.momogrow.views.bottomsheets.ConfirmationBottomSheet
import com.paysky.momogrow.views.bottomsheets.ShareSMSlBottomSheet

class QRActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQractivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQractivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.btnDone.setOnClickListener {
            finish()
        }
    }

    private fun showConfirmationBottomSheet(fragmentView: View) {
        val modalbottomSheetFragment =
            ConfirmationBottomSheet(fragmentView = fragmentView, fromWhere = "qr")
        modalbottomSheetFragment.show(
            supportFragmentManager,
            modalbottomSheetFragment.tag
        )
    }
}