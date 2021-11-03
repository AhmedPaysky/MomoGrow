package com.paysky.momogrow.views

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.paysky.momogrow.data.api.ApiClient
import com.paysky.momogrow.data.api.ApiService
import com.paysky.momogrow.data.models.MOMOPayCheckMerchantIsRegisterRequest
import com.paysky.momogrow.data.models.MoMoPayRegisterRequest
import com.paysky.momogrow.databinding.ActivityMobileNumberBinding
import com.paysky.momogrow.helper.Status
import com.paysky.momogrow.utilis.Constants
import com.paysky.momogrow.utilis.MyUtils
import com.paysky.momogrow.utilis.PreferenceProcessor
import com.paysky.momogrow.utilis.TextUtils
import com.paysky.momogrow.viewmodels.MobileNumberViewModel
import com.paysky.momogrow.viewmodels.ViewModelFactory
import com.paysky.momogrow.views.login.LoginActivity
import com.paysky.momogrow.views.register.RegisterViewModel


class MobileNumberActivity : AppCompatActivity(), View.OnTouchListener {
    private lateinit var binding: ActivityMobileNumberBinding
    lateinit var dialog: Dialog
    private val viewModel: MobileNumberViewModel by viewModels {
        ViewModelFactory(
            ApiClient.apiClient().create(
                ApiService::class.java
            )
        )
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMobileNumberBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.etMobileNum.setOnTouchListener(this)
        dialog = MyUtils.getDlgProgress(this)
    }

    fun nextPage(view: View) {
        if (binding.etMobileNum.text?.length!! < 12) {
            binding.linearMobileNum.setBackgroundResource(com.paysky.momogrow.R.drawable.ic_rectangle_red)
            binding.tvIncorrectMsg.visibility = View.VISIBLE
            return
        }
        mOMOPayCheckMerchantIsRegisterApi()

    }

    private fun mOMOPayCheckMerchantIsRegisterApi() {
        val request = MOMOPayCheckMerchantIsRegisterRequest()
        //todo replace with mobile number property
//        request.mobileNumber = "256785826095"
        request.mobileNumber = TextUtils.replaceAll("\\D", binding.etMobileNum.text.toString())

        viewModel.mOMOPayCheckMerchantIsRegister(request).observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    dialog.dismiss()
                    if (it.data?.isSuccess!!) {
                        if (!it.data.isRegister) {
                            startActivity(
                                Intent(this, AuthenticateActivity::class.java)
                                    .putExtra(
                                        "mobile_number",
                                        TextUtils.replaceAll(
                                            "\\D",
                                            binding.etMobileNum.text.toString()
                                        )
                                    )
                            )
                        } else {
                            startActivity(
                                Intent(this, LoginActivity::class.java)
                                    .putExtra(
                                        "mobile_number",
                                        TextUtils.replaceAll(
                                            "\\D",
                                            binding.etMobileNum.text.toString()
                                        )
                                    )
                            )
                        }
                    }
                }
                Status.ERROR -> {
                    dialog.dismiss()
                    Toast.makeText(this, "Fail", Toast.LENGTH_LONG).show()
                    Log.d("MobileActivity", it.message!!)

                }
                Status.LOADING -> {
                    dialog.show()
                    Log.d("Mobilectivity", "Loading")

                }
            }
        })
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        binding.linearMobileNum.setBackgroundResource(com.paysky.momogrow.R.drawable.ic_rectangle_grey)
        binding.tvIncorrectMsg.visibility = View.GONE
        return false
    }

    fun login(view: View) {
        startActivity(Intent(this, LoginActivity::class.java))
    }

}


