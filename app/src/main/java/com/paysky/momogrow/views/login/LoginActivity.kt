package com.paysky.momogrow.views.login

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.paysky.momogrow.data.api.ApiClientCube
import com.paysky.momogrow.data.api.ApiServiceCube
import com.paysky.momogrow.data.models.requests.MOMOPayLoginRequest
import com.paysky.momogrow.databinding.ActivityLoginBinding
import com.paysky.momogrow.helper.Status
import com.paysky.momogrow.utilis.*
import com.paysky.momogrow.viewmodels.ViewModelFactoryCube
import com.paysky.momogrow.views.home.HomeActivity
import timber.log.Timber

class LoginActivity : AppCompatActivity() {
    private var mobileNumber: String = ""
    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModels {
        ViewModelFactoryCube(
            ApiClientCube.apiClient().create(
                ApiServiceCube::class.java
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
        val request =
            MOMOPayLoginRequest()
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

                        PreferenceProcessor.setStr(
                            Constants.Companion.Preference.AUTH_TOKEN,
                            it.data.authToken
                        )

                        if (it.data.merchantId != null) {

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
                Status.ERRORHttp -> {
                    dialog.dismiss()
                }
            }
        })
    }

    fun forgotPassword(view: View) {
        startActivity(
            Intent(this, AuthenticateFrgotPasswordActivity::class.java).putExtra(
                "mobile_number",
                mobileNumber
            )
        )
    }
}