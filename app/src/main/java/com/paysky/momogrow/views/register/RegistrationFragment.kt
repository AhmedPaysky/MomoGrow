package com.paysky.momogrow.views.register

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.paysky.momogrow.R
import com.paysky.momogrow.databinding.FragmentRegisterationBinding
import kotlinx.android.synthetic.main.fragment_registeration.view.*


class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegisterationBinding? = null
    private val model: RegisterViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterationBinding.inflate(inflater, container, false)
        val view = binding.root
        view.btnNext.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_registerationFragment_to_locationFragment)
        })

        binding.etLastMobileNum.setText(model.mobileNumber.value)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}