package com.paysky.momogrow.views.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.paysky.momogrow.R
import com.paysky.momogrow.data.models.PayLinkDetailsModel
import com.paysky.momogrow.databinding.ActivityCalculatorBinding
import com.paysky.momogrow.databinding.ActivityPaylinkDetailsBinding
import java.io.Serializable

class PaylinkDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaylinkDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaylinkDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val response = intent.getSerializableExtra("response") as PayLinkDetailsModel
        bindViews(response)
    }

    private fun bindViews(response: PayLinkDetailsModel?) {
        binding.tvOrderId.text = response?.orderId
        binding.tvTerminalId.text = response?.terminalId
        binding.tvMerchantId.text = response?.merchantId
        binding.tvDateExpire.text = response?.dateExpire
        binding.tvDateSent.text = response?.dateCreated
        binding.tvMobileNum.text = response?.mobileNumer
        binding.tvAmount.text = response?.amount
    }

    fun finish(view: View) {
        finish()
    }
}