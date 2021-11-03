package com.paysky.momogrow.views.login

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.paysky.momogrow.R
import com.paysky.momogrow.data.api.ApiClient
import com.paysky.momogrow.data.api.ApiService
import com.paysky.momogrow.data.models.MOMOPayLoginRequest
import com.paysky.momogrow.data.models.MoMoPayRegisterAccountRequest
import com.paysky.momogrow.databinding.ActivityAuthenticateBinding
import com.paysky.momogrow.databinding.ActivityLoginBinding
import com.paysky.momogrow.helper.Status
import com.paysky.momogrow.utilis.AesGcm256
import com.paysky.momogrow.utilis.Constants
import com.paysky.momogrow.utilis.MyUtils
import com.paysky.momogrow.utilis.PreferenceProcessor
import com.paysky.momogrow.viewmodels.ViewModelFactory
import com.paysky.momogrow.views.AuthenticateActivity
import com.paysky.momogrow.views.home.HomeActivity
import com.paysky.momogrow.views.register.RegisterActivity
import com.paysky.momogrow.views.register.RegisterViewModel
import com.paysky.momogrow.views.reset_password.ResetPasswordActivity
import timber.log.Timber

class LoginActivity : AppCompatActivity() {
    private var mobileNumber: String = ""
    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModels {
        ViewModelFactory(
            ApiClient.apiClient().create(
                ApiService::class.java
            )
        )
    }
    lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        dialog = MyUtils.getDlgProgress(this)
        mobileNumber = intent.getStringExtra("mobile_number")!!
    }

    fun nextPage(view: View) {
        momoLoginApi()
    }

    private fun momoLoginApi() {
        val request = MOMOPayLoginRequest()
        request.password = AesGcm256.encrypt(
            binding.etPassword.text.toString(),
            AesGcm256.HexToByte(AesGcm256.hexKey),
            AesGcm256.HexToByte(AesGcm256.hexIV)
        )
//        request.password =
//            binding.etPassword.text.toString()

        request.username = mobileNumber
        request.setfBToken(
            PreferenceProcessor.getStr(
                Constants.Companion.Preference.FIREBASE_TOKEN,
                ""
            )
        )
        viewModel.mOMOPayLogin(request).observe(this, {
            when (it.status) {
                Status.SUCCESS -> {
                    dialog.dismiss()
                    if (it.data?.success!!) {
                        it.data.message.let { message ->
                            Timber.tag("LoginActivity").d(message)
                        }

                        if (it.data.merchantId != null) {
                            PreferenceProcessor.setStr(
                                Constants.Companion.Preference.AUTH_TOKEN,
                                it.data.authToken
                            )
                            PreferenceProcessor.setStr(
                                Constants.Companion.Preference.MERCHANT_ID,
                                it.data.merchantId
                            )
                            PreferenceProcessor.setStr(
                                Constants.Companion.Preference.TERMINAL_ID,
                                it.data.terminalId
                            )
                            PreferenceProcessor.setBool(
                                Constants.Companion.Preference.IS_LOGIN,
                                true
                            )
                            startActivity(
                                Intent(this@LoginActivity, HomeActivity::class.java)
                                    .putExtra("type", "login")
                            )
                        } else {
                            startActivity(
                                Intent(this@LoginActivity, PendigApprovalActivity::class.java)
                            )
                        }
                    } else {
                        it.data.message.let { message ->
                            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                        }
                    }
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

    fun forgotPassword(view: View) {
        startActivity(Intent(this, ResetPasswordActivity::class.java))
    }
}