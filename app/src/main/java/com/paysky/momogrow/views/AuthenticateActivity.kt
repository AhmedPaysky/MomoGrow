package com.paysky.momogrow.views

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.paysky.momogrow.bacgroundservices.MyFirebaseMessagingService
import com.paysky.momogrow.data.api.ApiClient
import com.paysky.momogrow.data.api.ApiService
import com.paysky.momogrow.data.models.MoMoPayRegisterRequest
import com.paysky.momogrow.databinding.ActivityAuthenticateBinding
import com.paysky.momogrow.helper.Status
import com.paysky.momogrow.utilis.Constants
import com.paysky.momogrow.utilis.Constants.Companion.Preference.Companion.RECEIVED_AUTH
import com.paysky.momogrow.utilis.CountUpTimer
import com.paysky.momogrow.utilis.PreferenceProcessor
import com.paysky.momogrow.viewmodels.MobileNumberViewModel
import com.paysky.momogrow.viewmodels.ViewModelFactory
import com.paysky.momogrow.views.register.RegisterActivity

class AuthenticateActivity : AppCompatActivity() {
    private var mobileNumber: String? = ""
    private var refNumer: String? = ""
    private lateinit var timer: CountDownTimer
    private lateinit var binding: ActivityAuthenticateBinding
    private lateinit var viewModel: MobileNumberViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticateBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        PreferenceProcessor.setBool(RECEIVED_AUTH, false)
        mobileNumber = intent.getStringExtra("mobile_number")
        binding.tvMobileNum.text = mobileNumber
        setupViewModel()
        timer = object : CountUpTimer(5000, 1000) {
            override fun onTick2(second: Int) {
                binding.progressCircular.progress = second;

            }

            override fun onFinish() {
                super.onFinish()
                timer.start()
            }
        }

        if (!PreferenceProcessor.getBool(RECEIVED_AUTH, false))
            momoRegisterApi()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiClient.apiClient().create(ApiService::class.java))
        ).get(MobileNumberViewModel::class.java)
    }

    private fun momoRegisterApi() {
        val request = MoMoPayRegisterRequest()
        request.setfBToken("")
        //todo replace with mobile number property
//        request.mobileNumber = "256785826095"
        request.mobileNumber = mobileNumber
        request.environment = Constants.ENVIRONMENT
        request.setfBToken(
            PreferenceProcessor.getStr(
                Constants.Companion.Preference.FIREBASE_TOKEN,
                ""
            )
        )

        viewModel.moMoPayRegister(request).observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    if (it.data?.isSuccess!!) {
                        refNumer = it.data.referenceNumber
                        Log.d("LoginActivity", it.data?.message!!)
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(this, "Fail", Toast.LENGTH_LONG).show()
                    Log.d("LoginActivity", it.message!!)

                }
                Status.LOADING -> {
                    timer.start()
                    Log.d("LoginActivity", "Loading")

                }
            }
        })
    }


    override fun onResume() {
        super.onResume()
        // Register for the particular broadcast based on ACTION string
        if (PreferenceProcessor.getBool(RECEIVED_AUTH, false)) {
            PreferenceProcessor.setBool(RECEIVED_AUTH, false)
//            binding.notification.visibility = View.VISIBLE
            timer.cancel()
            startActivity(
                Intent(this@AuthenticateActivity, RegisterActivity::class.java)
                    .putExtra("mobile_number", mobileNumber)
                    .putExtra("ref_number", refNumer)
            )
        }
        val filter = IntentFilter(MyFirebaseMessagingService.ACTION)
        registerReceiver(testReceiver, filter)
    }

    override fun onPause() {
        super.onPause()
        // Unregister the listener when the application is paused
        unregisterReceiver(testReceiver)
    }

    // Define the callback for what to do when data is received
    private val testReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            if (resultCode == RESULT_OK) {
                Log.d("TestMessage", "Notification Received")
//                binding.notification.visibility = View.VISIBLE
                timer.cancel()
                startActivity(
                    Intent(this@AuthenticateActivity, RegisterActivity::class.java)
                        .putExtra("mobile_number", mobileNumber)
                        .putExtra("ref_number", refNumer)
                )
            }
        }
    }


    fun nextPage(view: View) {
        startActivity(
            Intent(this@AuthenticateActivity, RegisterActivity::class.java)
                .putExtra("mobile_number", mobileNumber)
                .putExtra("ref_number", refNumer)
        )
    }

    fun sendAgain(view: View) {
        momoRegisterApi()
    }
}