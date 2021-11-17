package com.paysky.momogrow.views.orders

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.paysky.momogrow.R
import com.paysky.momogrow.databinding.FragmentOrdersBinding
import com.paysky.momogrow.databinding.FragmentPasswordBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.paysky.momogrow.data.api.ApiClientMomo
import com.paysky.momogrow.data.api.ApiServiceMomo
import com.paysky.momogrow.data.models.momo.ProductsResponse
import com.paysky.momogrow.data.models.momo.orders.OrdersItem
import com.paysky.momogrow.data.models.momo.orders.OrdersResponse
import com.paysky.momogrow.helper.Status
import com.paysky.momogrow.utilis.MyUtils
import com.paysky.momogrow.viewmodels.ViewModelFactoryMomo
import com.paysky.momogrow.views.products.ProductViewModel
import kotlinx.android.synthetic.main.fragment_orders.view.*


class OrdersFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentOrdersBinding? = null
    private val viewModel: OrdersViewModel by activityViewModels {
        ViewModelFactoryMomo(
            ApiClientMomo.apiClient().create(
                ApiServiceMomo::class.java
            )
        )
    }

    private lateinit var dialog: Dialog

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var adapter: OrdersAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        val view = binding.root
        adapter = OrdersAdapter(requireActivity())
        view.recyclerView.layoutManager = LinearLayoutManager(context)
        view.recyclerView.adapter = adapter
        dialog = MyUtils.getDlgProgress(requireActivity())
        binding.tvAll.setOnClickListener(this)
        binding.tvNotprocessed.setOnClickListener(this)
        binding.tvIntransit.setOnClickListener(this)
        binding.tvDelivered.setOnClickListener(this)
        binding.tvCancelled.setOnClickListener(this)
        binding.tvRefundrequest.setOnClickListener(this)
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                adapter?.filter(p0.toString())
            }

        })

        viewModel.getAllOrders().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    dialog.dismiss()
                    if ((it.data as OrdersResponse).data?.orders?.isEmpty()!!) {
                        binding.linearNoProducts.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.GONE
                    } else {
                        binding.linearNoProducts.visibility = View.GONE
                        binding.recyclerView.visibility = View.VISIBLE
                        adapter?.setOrdersList(it.data.data?.orders as ArrayList<OrdersItem>?)
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

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.tvAll -> {
                adapter?.filter("")
                binding.tvAll.setBackgroundResource(R.drawable.bc_blue_shape)
                binding.tvNotprocessed.setBackgroundColor(Color.TRANSPARENT)
                binding.tvIntransit.setBackgroundColor(Color.TRANSPARENT)
                binding.tvCancelled.setBackgroundColor(Color.TRANSPARENT)
                binding.tvDelivered.setBackgroundColor(Color.TRANSPARENT)
                binding.tvRefundrequest.setBackgroundColor(Color.TRANSPARENT)
                binding.tvAll.setTextColor(resources.getColor(R.color.white, null))
                binding.tvNotprocessed.setTextColor(resources.getColor(R.color.gray_light, null))
                binding.tvIntransit.setTextColor(resources.getColor(R.color.gray_light, null))
                binding.tvDelivered.setTextColor(resources.getColor(R.color.gray_light, null))
                binding.tvCancelled.setTextColor(resources.getColor(R.color.gray_light, null))
                binding.tvRefundrequest.setTextColor(resources.getColor(R.color.gray_light, null))
            }
            R.id.tvNotprocessed -> {
                adapter?.filter("Not processed")
                binding.tvNotprocessed.setBackgroundResource(R.drawable.bc_blue_shape)
                binding.tvAll.setBackgroundColor(Color.TRANSPARENT)
                binding.tvIntransit.setBackgroundColor(Color.TRANSPARENT)
                binding.tvCancelled.setBackgroundColor(Color.TRANSPARENT)
                binding.tvDelivered.setBackgroundColor(Color.TRANSPARENT)
                binding.tvRefundrequest.setBackgroundColor(Color.TRANSPARENT)
                binding.tvNotprocessed.setTextColor(resources.getColor(R.color.white, null))
                binding.tvAll.setTextColor(resources.getColor(R.color.gray_light, null))
                binding.tvIntransit.setTextColor(resources.getColor(R.color.gray_light, null))
                binding.tvDelivered.setTextColor(resources.getColor(R.color.gray_light, null))
                binding.tvCancelled.setTextColor(resources.getColor(R.color.gray_light, null))
                binding.tvRefundrequest.setTextColor(resources.getColor(R.color.gray_light, null))
            }
            R.id.tvIntransit -> {
                adapter?.filter("In transit")
                binding.tvIntransit.setBackgroundResource(R.drawable.bc_blue_shape)
                binding.tvNotprocessed.setBackgroundColor(Color.TRANSPARENT)
                binding.tvAll.setBackgroundColor(Color.TRANSPARENT)
                binding.tvCancelled.setBackgroundColor(Color.TRANSPARENT)
                binding.tvDelivered.setBackgroundColor(Color.TRANSPARENT)
                binding.tvRefundrequest.setBackgroundColor(Color.TRANSPARENT)
                binding.tvIntransit.setTextColor(resources.getColor(R.color.white, null))
                binding.tvNotprocessed.setTextColor(resources.getColor(R.color.gray_light, null))
                binding.tvAll.setTextColor(resources.getColor(R.color.gray_light, null))
                binding.tvDelivered.setTextColor(resources.getColor(R.color.gray_light, null))
                binding.tvCancelled.setTextColor(resources.getColor(R.color.gray_light, null))
                binding.tvRefundrequest.setTextColor(resources.getColor(R.color.gray_light, null))
            }
            R.id.tvDelivered -> {
                adapter?.filter("Delivered")
                binding.tvDelivered.setBackgroundResource(R.drawable.bc_blue_shape)
                binding.tvNotprocessed.setBackgroundColor(Color.TRANSPARENT)
                binding.tvIntransit.setBackgroundColor(Color.TRANSPARENT)
                binding.tvCancelled.setBackgroundColor(Color.TRANSPARENT)
                binding.tvAll.setBackgroundColor(Color.TRANSPARENT)
                binding.tvRefundrequest.setBackgroundColor(Color.TRANSPARENT)
                binding.tvDelivered.setTextColor(resources.getColor(R.color.white, null))
                binding.tvNotprocessed.setTextColor(resources.getColor(R.color.gray_light, null))
                binding.tvIntransit.setTextColor(resources.getColor(R.color.gray_light, null))
                binding.tvAll.setTextColor(resources.getColor(R.color.gray_light, null))
                binding.tvCancelled.setTextColor(resources.getColor(R.color.gray_light, null))
                binding.tvRefundrequest.setTextColor(resources.getColor(R.color.gray_light, null))

            }
            R.id.tvCancelled -> {
                adapter?.filter("Cancelled")
                binding.tvCancelled.setBackgroundResource(R.drawable.bc_blue_shape)
                binding.tvNotprocessed.setBackgroundColor(Color.TRANSPARENT)
                binding.tvIntransit.setBackgroundColor(Color.TRANSPARENT)
                binding.tvAll.setBackgroundColor(Color.TRANSPARENT)
                binding.tvDelivered.setBackgroundColor(Color.TRANSPARENT)
                binding.tvRefundrequest.setBackgroundColor(Color.TRANSPARENT)
                binding.tvCancelled.setTextColor(resources.getColor(R.color.white, null))
                binding.tvNotprocessed.setTextColor(resources.getColor(R.color.gray_light, null))
                binding.tvIntransit.setTextColor(resources.getColor(R.color.gray_light, null))
                binding.tvDelivered.setTextColor(resources.getColor(R.color.gray_light, null))
                binding.tvAll.setTextColor(resources.getColor(R.color.gray_light, null))
                binding.tvRefundrequest.setTextColor(resources.getColor(R.color.gray_light, null))

            }
            R.id.tvRefundrequest -> {
                adapter?.filter("Refund request")
                binding.tvRefundrequest.setBackgroundResource(R.drawable.bc_blue_shape)
                binding.tvNotprocessed.setBackgroundColor(Color.TRANSPARENT)
                binding.tvIntransit.setBackgroundColor(Color.TRANSPARENT)
                binding.tvCancelled.setBackgroundColor(Color.TRANSPARENT)
                binding.tvDelivered.setBackgroundColor(Color.TRANSPARENT)
                binding.tvAll.setBackgroundColor(Color.TRANSPARENT)
                binding.tvRefundrequest.setTextColor(resources.getColor(R.color.white, null))
                binding.tvNotprocessed.setTextColor(resources.getColor(R.color.gray_light, null))
                binding.tvIntransit.setTextColor(resources.getColor(R.color.gray_light, null))
                binding.tvDelivered.setTextColor(resources.getColor(R.color.gray_light, null))
                binding.tvCancelled.setTextColor(resources.getColor(R.color.gray_light, null))
                binding.tvAll.setTextColor(resources.getColor(R.color.gray_light, null))

            }
        }
    }
}