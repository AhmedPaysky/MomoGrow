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
import com.paysky.momogrow.R
import com.paysky.momogrow.data.api.ApiClientMomo
import com.paysky.momogrow.data.api.ApiServiceMomo
import com.paysky.momogrow.data.models.momo.CatgoriesItem
import com.paysky.momogrow.data.models.momo.DataItem
import com.paysky.momogrow.data.models.momo.MainOfMainCategories
import com.paysky.momogrow.data.models.momo.ProductsResponse
import com.paysky.momogrow.databinding.FragmentCatalogBinding
import com.paysky.momogrow.helper.Status
import com.paysky.momogrow.utilis.MyUtils
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
    private val binding get() = _binding!!
    private var adapter: ProductsAdapter? = null
    private var adaptercategories: CategoriesAdapter? = null
    private lateinit var dialog: Dialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCatalogBinding.inflate(inflater, container, false)
        val view = binding.root
        adapter = ProductsAdapter(requireActivity())
        adaptercategories = CategoriesAdapter(requireActivity())
        view.recyclerView.layoutManager = LinearLayoutManager(context)
        view.recyclerView.adapter = adapter
        dialog = MyUtils.getDlgProgress(requireActivity())
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                adapter!!.filter(p0.toString())
            }

        })


        GetAllCatgories()
        return view
    }

    fun setProductData(id: Int = 0) {
        viewModel.allProducts(id).observe(viewLifecycleOwner, Observer {
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
                    binding.tvOrdersCount.text = it.data.data?.products?.size.toString() + " " + getString(R.string.products)
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
                        .putExtra("obj", productObj)
                )
            }
        })
    }
    override fun onResume() {
        super.onResume()
        if (adaptercategories != null && adaptercategories?.getCategories()?.size!! > 0) {
            setProductData()
        }
    }

    fun GetAllCatgories() {
        binding.listCategories.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        binding.listCategories.adapter = adaptercategories
        viewModel.allCategories().observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    dialog.dismiss()
                    val datacategories = ArrayList<CatgoriesItem?>()
                    val cat = CatgoriesItem()
                    cat.name = "All"
                    cat.id = 0
                    datacategories.add(cat)
                    datacategories.addAll((it.data as MainOfMainCategories).data?.catgories!!)
                    adaptercategories?.setCategories(datacategories)
                    setProductData()
                }
                Status.ERROR -> {
                    dialog.dismiss()
                }
                Status.LOADING -> {
                    dialog.show()
                }
                else ->
                    dialog.dismiss()

            }
        })

        adaptercategories!!.setListener(object : CategoriesAdapter.onItemClick {
            override fun onClicked(Obj: CatgoriesItem) {
                setProductData(Obj.id!!)
            }
        })

    }
}