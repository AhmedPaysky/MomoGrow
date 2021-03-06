package com.paysky.momogrow.views

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.paysky.momogrow.data.api.ApiClientCube
import com.paysky.momogrow.data.api.ApiServiceCube
import com.paysky.momogrow.data.models.requests.MOMOPayCheckMerchantIsRegisterRequest
import com.paysky.momogrow.databinding.ActivityMobileNumberBinding
import com.paysky.momogrow.helper.Status
import com.paysky.momogrow.utilis.Constants
import com.paysky.momogrow.utilis.MyUtils
import com.paysky.momogrow.utilis.PreferenceProcessor
import com.paysky.momogrow.utilis.TextUtils
import com.paysky.momogrow.viewmodels.MobileNumberViewModel
import com.paysky.momogrow.viewmodels.ViewModelFactoryCube
import com.paysky.momogrow.views.login.LoginActivity


class MobileNumberActivity : AppCompatActivity(), View.OnTouchListener {
    private lateinit var binding: ActivityMobileNumberBinding
    lateinit var dialog: Dialog
    private val viewModel: MobileNumberViewModel by viewModels {
        ViewModelFactoryCube(
            ApiClientCube.apiClient().create(
                ApiServiceCube::class.java
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
        if (TextUtils.replaceAll("\\D", "256" + binding.etMobileNum.text.toString()).length < 9) {
            binding.linearMobileNum.setBackgroundResource(com.paysky.momogrow.R.drawable.ic_rectangle_red)
            binding.tvIncorrectMsg.visibility = View.VISIBLE
            return
        }
        mOMOPayCheckMerchantIsRegisterApi()

    }

    private fun mOMOPayCheckMerchantIsRegisterApi() {
        val request =
            MOMOPayCheckMerchantIsRegisterRequest()
        //todo replace with mobile number property
//        request.mobileNumber = "256785826095"
        request.mobileNumber = TextUtils.replaceAll("\\D", "256" + binding.etMobileNum.text.toString())
        PreferenceProcessor.setStr(
            Constants.Companion.Preference.MOBILE_NUM,
            "256" + binding.etMobileNum.text.toString()
        )
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
                                            "256" + binding.etMobileNum.text.toString()
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
                                            "256" + binding.etMobileNum.text.toString()
                                        )
                                    )
                            )
                        }
                    }
                }
                Status.ERROR -> {
                    dialog.dismiss()
                    Toast.makeText(this, "Fail", Toast.LENGTH_LONG).show()

                }
                Status.LOADING -> {
                    dialog.show()
                }
                Status.ERRORHttp -> {
                    dialog.dismiss()
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


