package com.paysky.momogrow.views.register

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.paysky.momogrow.R
import com.paysky.momogrow.data.api.ApiClient
import com.paysky.momogrow.data.api.ApiService
import com.paysky.momogrow.data.models.MoMoPayGetMerchantInfoRequest
import com.paysky.momogrow.data.models.MoMoPayGetMerchantInfoResponse
import com.paysky.momogrow.helper.Status
import com.paysky.momogrow.utilis.MyUtils
import com.paysky.momogrow.viewmodels.ViewModelFactory

class RegisterActivity : AppCompatActivity() {
    private val viewModel: RegisterViewModel by viewModels {
        ViewModelFactory(
            ApiClient.apiClient().create(
                ApiService::class.java
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        viewModel.mobileNumber.value = intent.getStringExtra("mobile_number")
        viewModel.refNumber.value = intent.getStringExtra("ref_number")
    }

}