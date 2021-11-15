package com.paysky.momogrow.views.register

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.paysky.momogrow.R
import com.paysky.momogrow.data.api.ApiClient
import com.paysky.momogrow.data.api.ApiService
import com.paysky.momogrow.data.models.requests.MoMoPayRegisterAccountRequest
import com.paysky.momogrow.databinding.FragmentPasswordBinding
import com.paysky.momogrow.helper.Status
import com.paysky.momogrow.utilis.*
import com.paysky.momogrow.utilis.AesGcm256.hexIV
import com.paysky.momogrow.utilis.AesGcm256.hexKey
import com.paysky.momogrow.viewmodels.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_password.*
import kotlinx.android.synthetic.main.fragment_password.view.*

class PasswordFragment : Fragment(), View.OnTouchListener {
    private var _binding: FragmentPasswordBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: RegisterViewModel by activityViewModels {
        ViewModelFactory(
            ApiClient.apiClient().create(
                ApiService::class.java
            )
        )
    }
    lateinit var dialog: Dialog

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPasswordBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.etPassword.setOnTouchListener(this)
        binding.etConfirmPassword.setOnTouchListener(this)
        view.btnNext.setOnClickListener(View.OnClickListener {
            if (validate())
                momoRegisterApi()
        })

        dialog = MyUtils.getDlgProgress(requireActivity())

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
        return view
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

    private fun momoRegisterApi() {
        val request =
            MoMoPayRegisterAccountRequest()
        request.password = AesGcm256.encrypt(
            binding.etPassword.text.toString(),
            AesGcm256.HexToByte(hexKey),
            AesGcm256.HexToByte(hexIV)
        )

//        request.password =
//            binding.etPassword.text.toString()

        request.userName = viewModel.mobileNumber.value
//        request.userName = "256785826095"

        viewModel.moMoPayRegisterAccount(request).observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    dialog.dismiss()
                    //todo remove after finish test
                    PreferenceProcessor.setBool(
                        Constants.Companion.Preference.IS_REGISTERED,
                        true
                    )
                    if (it.data?.success!!) {
                        findNavController().navigate(R.id.action_passwordFragment_to_pendigApprovalFragment)
                        PreferenceProcessor.setStr(
                            Constants.Companion.Preference.AUTH_TOKEN,
                            it.data.authToken
                        )
                        PreferenceProcessor.setBool(
                            Constants.Companion.Preference.IS_REGISTERED,
                            true
                        )
                        Log.d("LoginActivity", it.data?.message!!)
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(requireActivity(), "Fail", Toast.LENGTH_LONG).show()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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