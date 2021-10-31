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
import com.paysky.momogrow.views.register.RegisterActivity
import com.paysky.momogrow.views.register.RegisterViewModel
import com.paysky.momogrow.views.reset_password.ResetPasswordActivity

class LoginActivity : AppCompatActivity() {
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
    }

    fun nextPage(view: View) {
        momoLoginApi()
    }

    private fun momoLoginApi() {
        val request = MOMOPayLoginRequest()
//        request.password = AesGcm256.encrypt(
//            binding.etPassword.text.toString(),
//            AesGcm256.HexToByte(AesGcm256.hexKey),
//            AesGcm256.HexToByte(AesGcm256.hexIV)
//        )
        request.password =
            binding.etPassword.text.toString()

        request.username = "256785826095"
        viewModel.mOMOPayLogin(request).observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    dialog.dismiss()
                    if (it.data?.success!!) {
                        PreferenceProcessor.setStr(
                            Constants.Companion.Preference.AUTH_TOKEN,
                            it.data.authToken
                        )
                        PreferenceProcessor.setBool(
                            Constants.Companion.Preference.IS_LOGIN,
                            true
                        )
                        startActivity(
                            Intent(this@LoginActivity, AuthenticateActivity::class.java)
                                .putExtra("type", "login")
                        )
                        Log.d("LoginActivity", it.data?.message!!)
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