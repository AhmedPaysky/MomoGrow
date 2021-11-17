package com.paysky.momogrow.views.reset_password

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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.paysky.momogrow.R
import com.paysky.momogrow.data.api.ApiClientCube
import com.paysky.momogrow.data.api.ApiServiceCube
import com.paysky.momogrow.data.models.requests.MoMoPayResetPasswordRequest
import com.paysky.momogrow.databinding.ActivityResetPasswordBinding
import com.paysky.momogrow.helper.Status
import com.paysky.momogrow.utilis.*
import com.paysky.momogrow.viewmodels.MobileNumberViewModel
import com.paysky.momogrow.viewmodels.ViewModelFactoryCube
import kotlinx.android.synthetic.main.fragment_password.*
import kotlinx.android.synthetic.main.fragment_password.view.*

class ResetPasswordActivity : AppCompatActivity(), View.OnTouchListener {
    private var refNumer: String = ""
    private var mobileNumber: String = ""
    private lateinit var binding: ActivityResetPasswordBinding

    private lateinit var viewModel: MobileNumberViewModel
    lateinit var dialog: Dialog

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.etPassword.setOnTouchListener(this)
        binding.etConfirmPassword.setOnTouchListener(this)
        mobileNumber = intent.getStringExtra("mobile_number")!!
        refNumer = intent.getStringExtra("ref_number")!!
        dialog = MyUtils.getDlgProgress(this)
        setupViewModel()
        view.btnNext.setOnClickListener(View.OnClickListener {
            if (validate()) // todo uncomment
                momopasswordApi()
        })

        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (Validation.validatePasswordLength(p0.toString())) {
                    tvRule1.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_validated,
                        0,
                        0,
                        0
                    )
                } else {
                    tvRule1.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_error,
                        0,
                        0,
                        0
                    )
                }
                if (Validation.validatePasswordLowerChar(p0.toString())) {
                    tvRule2.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_validated,
                        0,
                        0,
                        0
                    )
                } else {
                    tvRule2.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_error,
                        0,
                        0,
                        0
                    )
                }
                if (Validation.validatePasswordUpperChar(p0.toString())) {
                    tvRule3.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_validated,
                        0,
                        0,
                        0
                    )
                } else {
                    tvRule3.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_error,
                        0,
                        0,
                        0
                    )
                }
                if (Validation.validatePasswordSpecialChar(p0.toString())) {
                    tvRule4.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_validated,
                        0,
                        0,
                        0
                    )
                } else {
                    tvRule4.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_error,
                        0,
                        0,
                        0
                    )
                }
            }
        })
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactoryCube(ApiClientCube.apiClient().create(ApiServiceCube::class.java))
        ).get(MobileNumberViewModel::class.java)
    }

    private fun momopasswordApi() {
        val request =
            MoMoPayResetPasswordRequest()
        //todo replace with mobile number property
//        request.mobileNumber = "256785826095"
        request.mobileNumber = mobileNumber
        request.referenceNumber = refNumer
        request.password = AesGcm256.encrypt(
            binding.etPassword.text.toString(),
            AesGcm256.HexToByte(AesGcm256.hexKey),
            AesGcm256.HexToByte(AesGcm256.hexIV)
        )

        viewModel.moMoPayResetPassword(request).observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    dialog.dismiss()
                    if (it.data?.success!!) {
                        startActivity(Intent(this, PasswordResetDoneActivity::class.java))
                        Log.d("LoginActivity", it.data?.message!!)
                    }
                }
                Status.ERROR -> {
                    dialog.dismiss()
                    Toast.makeText(this, "Fail", Toast.LENGTH_LONG).show()
                    Log.d("LoginActivity", it.message!!)

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

    private fun validate(): Boolean {
        if (!Validation.validiate(binding.etPassword.text.toString())) {
            binding.linearPassword.setBackgroundResource(R.drawable.ic_rectangle_red)
            return false
        }
        if (binding.etConfirmPassword.text.toString() != binding.etPassword.text.toString()) {
            binding.linearConfirmPassword.setBackgroundResource(R.drawable.ic_rectangle_red)
            return false
        }
        return true
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        when (p0?.id) {
            R.id.etPassword -> binding.linearPassword.setBackgroundResource(R.drawable.ic_rectangle_grey)
            R.id.etConfirmPassword -> binding.linearConfirmPassword.setBackgroundResource(R.drawable.ic_rectangle_grey)
        }
        return false
    }
}