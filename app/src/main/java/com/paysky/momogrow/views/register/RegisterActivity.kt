package com.paysky.momogrow.views.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.paysky.momogrow.R
import com.paysky.momogrow.data.api.ApiClientCube
import com.paysky.momogrow.data.api.ApiServiceCube
import com.paysky.momogrow.viewmodels.ViewModelFactoryCube

class RegisterActivity : AppCompatActivity() {
    private val viewModel: RegisterViewModel by viewModels {
        ViewModelFactoryCube(
            ApiClientCube.apiClient().create(
                ApiServiceCube::class.java
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