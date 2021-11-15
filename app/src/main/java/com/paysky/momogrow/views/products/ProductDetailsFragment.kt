package com.paysky.momogrow.views.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.paysky.momogrow.R
import com.paysky.momogrow.databinding.FragmentProductDetailsBinding


class ProductDetailsFragment : Fragment() {

    private var _binding: FragmentProductDetailsBinding? = null
    private val viewModel: ProductViewModel by activityViewModels()

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
        val productId = arguments?.getLong("productId", 0)

        viewModel.getProductById(productId?.toInt()!!)
            .observe(viewLifecycleOwner, Observer {
                binding.tvStatus.text = it.status
                binding.tvNameFruit.text = it.name
                binding.tvCategory.text = it.category
                binding.tvName.text = it.category
                binding.tvDescripyion.text = it.description
                binding.tvSku.text = it.sku
                binding.tvPrice.text = it.price
                binding.tvWidth.text = it.width
                binding.tvHeight.text = it.height
                binding.tvWeight.text = it.weight
                binding.tvQuantity.text = it.quantity
                binding.switchFeature.isChecked = it.featureUser
                binding.switchNew.isChecked = it.new
                binding.switchPublish.isChecked = it.publish
            })

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}