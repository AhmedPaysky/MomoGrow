package com.paysky.momogrow.views.products

import android.app.Dialog
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
import com.paysky.momogrow.data.models.momo.AddProductResponse
import com.paysky.momogrow.data.models.momo.Data
import com.paysky.momogrow.data.models.momo.SimpleResponse
import com.paysky.momogrow.databinding.FragmentProductDetailsBinding
import com.paysky.momogrow.helper.Status
import com.paysky.momogrow.utilis.MyUtils


class ProductDetailsFragment : Fragment() {
    lateinit var productdata : Data

    private var _binding: FragmentProductDetailsBinding? = null
    private val viewModel: ProductViewModel by activityViewModels()
    private lateinit var dialog: Dialog

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        dialog = MyUtils.getDlgProgress(requireActivity())
        val view = binding.root
        binding.btnEdit.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("productdata", productdata)
            findNavController().navigate(R.id.action_productDetailsFragment_to_addProductFragment,bundle)
        }
        productdata = (arguments?.getSerializable("productdata") as AddProductResponse).data!!

        binding.btnDelete.setOnClickListener {
            viewModel.deleteProduct(productdata.id.toString()).observe(viewLifecycleOwner, {
                when (it.status) {
                    Status.SUCCESS -> {
                        dialog.dismiss()
                        Toast.makeText(requireActivity(), "Product deleted ", Toast.LENGTH_LONG).show()
                        requireActivity().finish()
                    }
                    Status.ERROR -> {
                        dialog.dismiss()
                    }
                    Status.LOADING -> {
                        dialog.show()
                    }
                }
            })
        }
        binding.ivBack.setOnClickListener {
            requireActivity().finish()
        }

        binding.tvStatus.text = if (productdata.inStock!!) "In Stock" else "Out of stock"
        binding.tvNameFruit.text = productdata.name

        productdata.categories?.forEach {
            binding.tvCategory.text = it?.name
        }

        binding.tvName.text = productdata.name
        binding.tvDescripyion.text = productdata.description
        binding.tvSku.text = productdata.sku
        binding.tvPrice.text = productdata.price
        binding.tvStatus.text = productdata.status
        binding.tvWidth.text = productdata.width.toString()
        binding.tvHeight.text = productdata.height.toString()
        binding.tvWeight.text = productdata.weight.toString()
        binding.tvQuantity.text = ""
        binding.switchFeature.isChecked = productdata.featured == 1
        binding.switchNew.isChecked = productdata.new  == 1
        binding.switchPublish.isChecked = productdata.active == 1
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}