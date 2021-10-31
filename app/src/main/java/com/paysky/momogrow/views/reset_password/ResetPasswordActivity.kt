package com.paysky.momogrow.views.reset_password

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.paysky.momogrow.R
import com.paysky.momogrow.databinding.ActivityAuthenticateBinding
import com.paysky.momogrow.databinding.ActivityResetPasswordBinding
import com.paysky.momogrow.databinding.FragmentPasswordBinding
import com.paysky.momogrow.utilis.Validation
import com.paysky.momogrow.views.register.PasswordFragment
import kotlinx.android.synthetic.main.fragment_password.*
import kotlinx.android.synthetic.main.fragment_password.view.*

class ResetPasswordActivity : AppCompatActivity(), View.OnTouchListener {
    private lateinit var binding: ActivityResetPasswordBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.etPassword.setOnTouchListener(this)
        binding.etConfirmPassword.setOnTouchListener(this)
        view.btnNext.setOnClickListener(View.OnClickListener {
            if (validate()) // todo uncomment
                startActivity(Intent(this,PasswordResetDoneActivity::class.java))

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