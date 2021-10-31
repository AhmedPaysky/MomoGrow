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
    lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        viewModel.mobileNumber.value = intent.getStringExtra("mobile_number")
        dialog = MyUtils.getDlgProgress(this)
        momoRegisterApi()
    }

    private fun momoRegisterApi() {
        val request = MoMoPayGetMerchantInfoRequest()
//        request.referenceNumber = intent.getStringExtra("ref_number")
        request.referenceNumber = "d4510f55-1b78-447a-9fc9-8464c4be1109"
        request.mobileNumber = intent.getStringExtra("mobile_number")

        viewModel.moMoPayGetMerchantInfo(request).observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
//                    startActivity(
//                        Intent(this, AuthenticateActivity::class.java)
//                            .putExtra("mobile_number", binding.etMobileNum.text.toString())
//                    )
                    dialog.dismiss()
                    if (it.data?.success!!) {
                        bindView(it.data)

                    }
                    Log.d("LoginActivity", it.data?.message!!)
                }
                Status.ERROR -> {
                    Toast.makeText(this, "Fail", Toast.LENGTH_LONG).show()
                    Log.d("LoginActivity", it.message!!)
                    dialog.dismiss()
                }
                Status.LOADING -> {
                    dialog.show()
                    Log.d("LoginActivity", "Loading")

                }
            }
        })
    }

    private fun bindView(merchantData: MoMoPayGetMerchantInfoResponse) {

    }

}