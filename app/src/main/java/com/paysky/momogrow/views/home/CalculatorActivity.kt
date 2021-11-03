package com.paysky.momogrow.views.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.paysky.momogrow.R
import com.paysky.momogrow.databinding.ActivityCalculatorBinding
import com.paysky.momogrow.views.bottomsheets.ConfirmationBottomSheet
import com.paysky.momogrow.views.bottomsheets.ShareSMSlBottomSheet
import com.paysky.momogrow.views.bottomsheets.ShareViaEmailBottomSheet
import com.paysky.momogrow.views.customviews.CustomAmountKeyBoard
import kotlinx.android.synthetic.main.activity_calculator.*
import android.R.attr.country


class CalculatorActivity : AppCompatActivity(), CustomAmountKeyBoard.ItemClickListener {
    private lateinit var binding: ActivityCalculatorBinding
    val strings = arrayOf("Never", "6 Days", "12 Days", "18 Days")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculatorBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.cusetomkey.setOnClickListener {
            cusetomkey.SetitemClickListener(this)
        }
        binding.cusetomkey.performClick()

        binding.ivEmail.setOnClickListener {
            showViaEmailBottomSheet(view)
        }
        binding.ivSms.setOnClickListener {
            showSMSBottomSheet(view)
        }
        binding.ivShare.setOnClickListener {
            shareViaSocial()
        }
        binding.ivQr.setOnClickListener {
            startActivity(Intent(this, QRActivity::class.java))
        }

        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, strings)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.spinner.adapter = adapter
    }

    private fun showViaEmailBottomSheet(fragmentView: View) {
        val modalbottomSheetFragment = ShareViaEmailBottomSheet(fragmentView = fragmentView)
        modalbottomSheetFragment.show(
            supportFragmentManager,
            modalbottomSheetFragment.tag
        )
    }

    private fun showSMSBottomSheet(fragmentView: View) {
        val modalbottomSheetFragment = ShareSMSlBottomSheet(fragmentView = fragmentView)
        modalbottomSheetFragment.show(
            supportFragmentManager,
            modalbottomSheetFragment.tag
        )
    }

    private fun shareViaSocial() {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(
            Intent.EXTRA_SUBJECT,
            resources.getString(R.string.app_name)
        )
        sharingIntent.putExtra(Intent.EXTRA_TEXT, "message content ")

        val intent = Intent.createChooser(
            sharingIntent, resources
                .getString(R.string.share)
        )
        startActivity(intent)
    }

    fun finish(view: View) {
        finish()
    }

    override fun setPriceInEditText(s: String?) {
        tvAmount.text = s
        if (s?.length == 0) tvAmount.text = "0"
    }
}