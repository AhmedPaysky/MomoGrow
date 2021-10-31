package com.paysky.momogrow.views.catalog

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.paysky.momogrow.R
import com.paysky.momogrow.databinding.FragmentCatalogBinding
import kotlinx.android.synthetic.main.fragment_orders.view.*


class CatalogFragment : Fragment() {

    private var _binding: FragmentCatalogBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var adapter: CatalogAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCatalogBinding.inflate(inflater, container, false)
        val view = binding.root
        adapter = CatalogAdapter(requireActivity())
        view.recyclerView.layoutManager = LinearLayoutManager(context)
        view.recyclerView.adapter = adapter
        adapter!!.setListener(object : CatalogAdapter.onItemClick {
            override fun onClicked(productObj: CatalogAdapter.ProductObj) {
                startActivity(
                    Intent(requireActivity(), ProductDetailsActivity::class.java)
                        .putExtra("name", productObj.name)
                        .putExtra("status", productObj.qStatus)
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
        return view
    }

    override fun onResume() {
        super.onResume()
        adapter = CatalogAdapter(requireActivity())
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
        adapter!!.setListener(object : CatalogAdapter.onItemClick {
            override fun onClicked(productObj: CatalogAdapter.ProductObj) {
                startActivity(
                    Intent(requireActivity(), ProductDetailsActivity::class.java)
                        .putExtra("name", productObj.name)
                        .putExtra("status", productObj.qStatus)
                )
            }
        })
    }
}