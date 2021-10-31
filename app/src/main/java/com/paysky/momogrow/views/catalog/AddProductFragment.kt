package com.paysky.momogrow.views.catalog

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.paysky.momogrow.R
import com.paysky.momogrow.databinding.FragmentAddProductBinding
import com.paysky.momogrow.databinding.FragmentPendingApprovalProductBinding
import com.paysky.momogrow.views.home.HomeActivity


class AddProductFragment : Fragment() {
    private var _binding: FragmentAddProductBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val strings = arrayOf("Fruits", "Vegetables", "Bakery", "Dairy & Eggs")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddProductBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.ivBack.setOnClickListener {
            requireActivity().finish()
        }
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(requireActivity(), android.R.layout.simple_spinner_item, strings)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.spinner.adapter = adapter

        binding.btnNext.setOnClickListener {
            val prodObj = CatalogAdapter.ProductObj()
            prodObj.name = binding.etProductName.text.toString()
            prodObj.status = "In stock"
            prodObj.qStatus = "under review"

            CatalogAdapter.products.add(0, prodObj)
            findNavController().navigate(R.id.action_addProductFragment_to_pendingApprovalProductFragment)
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