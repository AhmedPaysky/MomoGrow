package com.paysky.momogrow.views

import android.annotation.SuppressLint
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
import com.paysky.momogrow.data.api.ApiClient
import com.paysky.momogrow.data.api.ApiService
import com.paysky.momogrow.data.models.MoMoPayRegisterRequest
import com.paysky.momogrow.databinding.ActivityMobileNumberBinding
import com.paysky.momogrow.helper.Status
import com.paysky.momogrow.viewmodels.MobileNumberViewModel
import com.paysky.momogrow.viewmodels.ViewModelFactory
import com.paysky.momogrow.views.login.LoginActivity


class MobileNumberActivity : AppCompatActivity(), View.OnTouchListener {
    private lateinit var binding: ActivityMobileNumberBinding

    private lateinit var viewModel: MobileNumberViewModel

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMobileNumberBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.etMobileNum.setOnTouchListener(this)
    }

    fun nextPage(view: View) {
        if (binding.etMobileNum.text?.length!! < 12) {
            binding.linearMobileNum.setBackgroundResource(com.paysky.momogrow.R.drawable.ic_rectangle_red)
            binding.tvIncorrectMsg.visibility = View.VISIBLE
            return
        }
        startActivity(
            Intent(this, AuthenticateActivity::class.java)
                .putExtra("mobile_number", binding.etMobileNum.text.toString())
        )

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