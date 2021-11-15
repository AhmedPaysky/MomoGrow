package com.paysky.momogrow.views.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.paysky.momogrow.R
import com.paysky.momogrow.databinding.FragmentPendingApprovalProductBinding

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
        val productId = arguments?.getLong("productId", 0)
        val bundle = Bundle()
        bundle.putLong("productId", productId!!)

        binding.btnNext.setOnClickListener {
            findNavController().navigate(
                R.id.action_pendingApprovalProductFragment_to_productDetailsFragment, bundle
            )
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}