package com.paysky.momogrow.views.products

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.paysky.momogrow.data.api.ApiClientMomo
import com.paysky.momogrow.data.api.ApiServiceMomo
import com.paysky.momogrow.data.models.momo.DataItem
import com.paysky.momogrow.data.models.momo.ProductsResponse
import com.paysky.momogrow.databinding.FragmentCatalogBinding
import com.paysky.momogrow.helper.Status
import com.paysky.momogrow.utilis.MyUtils
import com.paysky.momogrow.viewmodels.ViewModelFactoryCube
import com.paysky.momogrow.viewmodels.ViewModelFactoryMomo
import kotlinx.android.synthetic.main.fragment_orders.view.*


class ProductsFragment : Fragment() {

    private var _binding: FragmentCatalogBinding? = null
    private val viewModel: ProductViewModel by activityViewModels {
        ViewModelFactoryMomo(
            ApiClientMomo.apiClient().create(
                ApiServiceMomo::class.java
            )
        )
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var adapter: ProductsAdapter? = null
    private lateinit var dialog: Dialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCatalogBinding.inflate(inflater, container, false)
        val view = binding.root
        adapter = ProductsAdapter(requireActivity())
        view.recyclerView.layoutManager = LinearLayoutManager(context)
        view.recyclerView.adapter = adapter
        dialog = MyUtils.getDlgProgress(requireActivity())
        adapter!!.setListener(object : ProductsAdapter.onItemClick {
            override fun onClicked(productObj: DataItem) {
                startActivity(
                    Intent(requireActivity(), ProductDetailsActivity::class.java)
                        .putExtra("name", productObj.name)
                        .putExtra("status", productObj.status)
                        .putExtra("productId", productObj.id)
                        .putExtra("objet", productObj)
                )
            }
        })

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                adapter!!.filter(p0.toString())
            }

        })

        viewModel.allProducts().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    dialog.dismiss()
                    if ((it.data as ProductsResponse).data?.products?.isEmpty()!!) {
                        binding.linearNoProducts.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.GONE
                    } else {
                        binding.linearNoProducts.visibility = View.GONE
                        binding.recyclerView.visibility = View.VISIBLE
                        adapter?.setProducts(it.data.data?.products)
                    }
                }
                Status.ERROR -> {
                    dialog.dismiss()
                }
                Status.LOADING -> {
                    dialog.show()

                }
                else -> dialog.dismiss()

            }
        })
        return view
    }

    override fun onResume() {
        super.onResume()
        adapter = ProductsAdapter(requireActivity())
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
        viewModel.allProducts().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    dialog.dismiss()
                    if ((it.data as ProductsResponse).data?.products?.isEmpty()!!) {
                        binding.linearNoProducts.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.GONE
                    } else {
                        binding.linearNoProducts.visibility = View.GONE
                        binding.recyclerView.visibility = View.VISIBLE
                        adapter?.setProducts(it.data.data?.products)
                    }
                }
                Status.ERROR -> {
                    dialog.dismiss()
                }
                Status.LOADING -> {
                    dialog.show()

                }
                else -> dialog.dismiss()

            }
        })
        adapter!!.setListener(object : ProductsAdapter.onItemClick {
            override fun onClicked(productObj: DataItem) {
                startActivity(
                    Intent(requireActivity(), ProductDetailsActivity::class.java)
                        .putExtra("name", productObj.name)
                        .putExtra("status", productObj.status)
                        .putExtra("productId", productObj.id)
                        .putExtra("obj", productObj)
                )
            }
        })
    }
}