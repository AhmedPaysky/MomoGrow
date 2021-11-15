package com.paysky.momogrow.views.home

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ScrollView
import android.widget.Toast
import com.paysky.momogrow.R
import com.paysky.momogrow.databinding.ActivityCalculatorBinding
import com.paysky.momogrow.views.bottomsheets.ShareSMSlBottomSheet
import com.paysky.momogrow.views.bottomsheets.ShareViaEmailBottomSheet
import com.paysky.momogrow.views.customviews.CustomAmountKeyBoard
import kotlinx.android.synthetic.main.activity_calculator.*
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.paysky.momogrow.data.api.ApiClient
import com.paysky.momogrow.data.api.ApiService
import com.paysky.momogrow.data.models.InitiateOrderResponse
import com.paysky.momogrow.data.models.requests.InitiateOrderRequest
import com.paysky.momogrow.helper.Status
import com.paysky.momogrow.utilis.Constants.Companion.Preference.Companion.MERCHANT_ID
import com.paysky.momogrow.utilis.Constants.Companion.Preference.Companion.TERMINAL_ID
import com.paysky.momogrow.utilis.MyUtils
import com.paysky.momogrow.utilis.OnBottomSheetButtonClicked
import com.paysky.momogrow.utilis.PreferenceProcessor
import com.paysky.momogrow.viewmodels.ViewModelFactory
import com.paysky.momogrow.views.bottomsheets.ConfirmationBottomSheet
import java.lang.invoke.MethodType


class CalculatorActivity : AppCompatActivity(), CustomAmountKeyBoard.ItemClickListener,
    OnBottomSheetButtonClicked {
    private lateinit var view: View
    private lateinit var binding: ActivityCalculatorBinding
    val strings = arrayOf("Never", "6 Days", "12 Days", "18 Days")
    lateinit var dialog: Dialog
    private val viewModel: HomeViewModel by viewModels {
        ViewModelFactory(
            ApiClient.apiClient().create(
                ApiService::class.java
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculatorBinding.inflate(layoutInflater)
        view = binding.root
        setContentView(view)
        dialog = MyUtils.getDlgProgress(this)
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

    private fun initOrder(notificationMethod: Int = 0, notificationValue: String = "") {
        val request = InitiateOrderRequest()
        request.dateTimeLocalTrxn = "1561665566"
        request.merchantId = PreferenceProcessor.getStr(MERCHANT_ID, "")
        request.terminalId = PreferenceProcessor.getStr(TERMINAL_ID, "")
        request.notificationMethod = notificationMethod
        request.notificationValue = notificationValue
        request.amount = binding.tvAmount.text.toString().toFloat()
        request.currency = 800

        viewModel.initiateOrderRequest(request).observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    dialog.dismiss()
                    val data = it.data as InitiateOrderResponse
                    if (data.success) {
                        when (notificationMethod) {
                            1 -> {
                                showConfirmationBottomSheet(view)
                            }
                        }
                    } else {

                    }
                }
                Status.ERROR -> {
                    dialog.dismiss()
                    Toast.makeText(this, it.message.toString(), Toast.LENGTH_LONG).show()

                }
                Status.LOADING -> {
                    dialog.show()
                }
                Status.ERRORHttp -> {
                    dialog.dismiss()
                    Toast.makeText(this, it.message.toString(), Toast.LENGTH_LONG).show()

                }
            }
        })
    }

    private fun showViaEmailBottomSheet(fragmentView: View) {
        val modalbottomSheetFragment = ShareViaEmailBottomSheet(fragmentView = fragmentView, this)
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

    //on bottomSheet button clicked
    override fun onClicked(value: String, type: String) {
        when (type) {
            "email" -> {
                initOrder(1)
            }
        }
    }

    private fun showConfirmationBottomSheet(fragmentView: View) {
        val modalbottomSheetFragment = ConfirmationBottomSheet(fragmentView = fragmentView)
        modalbottomSheetFragment.show(
            supportFragmentManager,
            modalbottomSheetFragment.tag
        )
    }
}