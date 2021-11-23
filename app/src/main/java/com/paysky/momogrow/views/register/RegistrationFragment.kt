package com.paysky.momogrow.views.register

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.paysky.momogrow.R
import com.paysky.momogrow.data.models.requests.MoMoPayGetMerchantInfoRequest
import com.paysky.momogrow.data.models.MoMoPayGetMerchantInfoResponse
import com.paysky.momogrow.databinding.FragmentRegisterationBinding
import com.paysky.momogrow.helper.Status
import com.paysky.momogrow.utilis.MyUtils
import kotlinx.android.synthetic.main.fragment_registeration.view.*


class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegisterationBinding? = null
    private val viewModel: RegisterViewModel by activityViewModels()

    lateinit var dialog: Dialog

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterationBinding.inflate(inflater, container, false)
        val view = binding.root

        dialog = MyUtils.getDlgProgress(requireActivity())

        view.btnNext.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_registerationFragment_to_locationFragment)
        })
        val strings = arrayOf("Fruits", "Vegetables", "Bakery", "Dairy & Eggs")
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(requireActivity(), android.R.layout.simple_spinner_item, strings)
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice)

        binding.spinner.adapter = adapter

        binding.etLastMobileNum.setText(viewModel.mobileNumber.value)

        momoRegisterApi()
        return view
    }

    private fun momoRegisterApi() {
        val request =
            MoMoPayGetMerchantInfoRequest()
        request.referenceNumber = viewModel.refNumber.value
//        request.referenceNumber = "d4510f55-1b78-447a-9fc9-8464c4be1109"
        request.mobileNumber = viewModel.mobileNumber.value
//        request.mobileNumber = "256785826095"

        viewModel.moMoPayGetMerchantInfo(request).observe(requireActivity(), {
            when (it.status) {
                Status.SUCCESS -> {
//                    startActivity(
//                        Intent(this, AuthenticateActivity::class.java)
//                            .putExtra("mobile_number", binding.etMobileNum.text.toString())
//                    )
                    dialog.dismiss()
                    if (it.data?.success!!) {
                        bindView(it.data)

                    }
                }
                Status.ERROR -> {
                    Toast.makeText(requireActivity(), "Fail", Toast.LENGTH_LONG).show()
                    dialog.dismiss()
                }
                Status.LOADING -> {
                    dialog.show()
                }
            }
        })
    }

    private fun bindView(data: MoMoPayGetMerchantInfoResponse) {
        val fName =
            data.merchantData.name.substring(0, data.merchantData.name.indexOf(" "))
        binding.etFirstName.setText(fName)
        binding.etLastName.setText(data.merchantData.familyName)
        binding.etLastMobileNum.setText(viewModel.mobileNumber.value)
        binding.etEmail.setText(data.merchantData.email)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}