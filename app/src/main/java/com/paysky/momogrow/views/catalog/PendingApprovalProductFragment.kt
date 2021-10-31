package com.paysky.momogrow.views.catalog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.paysky.momogrow.R
import com.paysky.momogrow.databinding.FragmentPendigApprovalBinding
import com.paysky.momogrow.databinding.FragmentPendingApprovalProductBinding
import com.paysky.momogrow.views.home.HomeActivity

class PendingApprovalProductFragment : Fragment() {
    private var _binding: FragmentPendingApprovalProductBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPendingApprovalProductBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_pendingApprovalProductFragment_to_productDetailsFragment)
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}