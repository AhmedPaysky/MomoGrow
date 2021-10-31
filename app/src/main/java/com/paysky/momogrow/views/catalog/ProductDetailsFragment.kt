package com.paysky.momogrow.views.catalog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.paysky.momogrow.R
import com.paysky.momogrow.databinding.FragmentAddProductBinding
import com.paysky.momogrow.databinding.FragmentProductDetailsBinding


class ProductDetailsFragment : Fragment() {

    private var _binding: FragmentProductDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.btnEdit.setOnClickListener {
            findNavController().navigate(R.id.action_productDetailsFragment_to_addProductFragment)
        }

        binding.btnDelete.setOnClickListener {
            requireActivity().finish()
            Toast.makeText(requireActivity(), "Product deleted ", Toast.LENGTH_LONG).show()
        }
        binding.ivBack.setOnClickListener {
            requireActivity().finish()
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}