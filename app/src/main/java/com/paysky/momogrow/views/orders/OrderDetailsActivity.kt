package com.paysky.momogrow.views.orders

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.paysky.momogrow.R
import com.paysky.momogrow.data.api.ApiClientMomo
import com.paysky.momogrow.data.api.ApiServiceMomo
import com.paysky.momogrow.data.models.momo.ProductsResponse
import com.paysky.momogrow.data.models.momo.orders.OrderDetailsResponse
import com.paysky.momogrow.data.models.momo.orders.OrderProductItem
import com.paysky.momogrow.data.models.momo.orders.OrdersItem
import com.paysky.momogrow.data.models.momo.orders.OrdersResponse
import com.paysky.momogrow.databinding.ActivityOrderDetailsBinding
import com.paysky.momogrow.databinding.FragmentOrderDetailsBinding
import com.paysky.momogrow.helper.Status
import com.paysky.momogrow.utilis.MyUtils
import com.paysky.momogrow.viewmodels.ViewModelFactoryMomo
import com.paysky.momogrow.views.bottomsheets.*
import com.paysky.momogrow.views.products.CategoriesAdapter
import com.paysky.momogrow.views.products.ProductsAdapter
import kotlinx.android.synthetic.main.activity_order_details.*
import kotlinx.android.synthetic.main.activity_order_details.view.*
import kotlinx.android.synthetic.main.custom_item_order.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_order_details.view.*
import kotlinx.android.synthetic.main.fragment_orders.view.*

class OrderDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderDetailsBinding
    private lateinit var introPagerAdapter: OrdersDetailsPagerAdapter

    companion object {
        public lateinit var lviewPager: ViewPager
        var counter: Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        lviewPager = binding.pager
        val orders = intent.getParcelableArrayListExtra<OrdersItem>("orders")
        introPagerAdapter =
            OrdersDetailsPagerAdapter(supportFragmentManager, this, orders)
        binding.pager.adapter = introPagerAdapter
        counter = intent.getIntExtra("position", 0)
        binding.pager.currentItem = counter

        var isOnFirstScreen = false
        binding.pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                counter = position
                if ((counter == 0) and !isOnFirstScreen) isOnFirstScreen = true

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }


    fun back(view: View) {
        finish()
    }
}

// Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
class OrdersDetailsPagerAdapter(
    fm: FragmentManager,
    var mContext: Context,
    var orders: List<OrdersItem>?
) :
    FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int = orders?.size!!

    interface Callback {
        fun onNextClick(nextPage: Int)
    }

    private var callback: Callback? = null

    fun setCallback(callback: Callback?) {
        this.callback = callback
    }

    override fun getItem(i: Int): Fragment {
        val fragment = OrderDetailsFragment()
        fragment.arguments = Bundle().apply {
            // Our object is just an integer :-P
            putSerializable(ARG_OBJECT, orders?.get(i))
            putInt("size", orders?.size!!)
        }
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return "OBJECT ${(position + 1)}"
    }
}

private const val ARG_OBJECT = "object"

class OrderDetailsFragment : Fragment() {

    private var adapter: OrderProductsAdapter? = null

    private val viewModel: OrdersViewModel by activityViewModels {
        ViewModelFactoryMomo(
            ApiClientMomo.apiClient().create(
                ApiServiceMomo::class.java
            )
        )
    }



    private var _binding: FragmentOrderDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOrderDetailsBinding.inflate(inflater, container, false)
        dialog = MyUtils.getDlgProgress(requireActivity())
        val view = binding.root

        val orderOBJ = arguments?.getSerializable(ARG_OBJECT) as OrdersItem
        val ordersSize = arguments?.getInt("size", 0)

        binding.ivNext.setOnClickListener {
            if (OrderDetailsActivity.counter < ordersSize!!) {
                OrderDetailsActivity.lviewPager.currentItem = ++OrderDetailsActivity.counter
            }
        }

        binding.ivBack.setOnClickListener {
            if (OrderDetailsActivity.counter > 0) {
                OrderDetailsActivity.lviewPager.currentItem = --OrderDetailsActivity.counter
            } else requireActivity().finish()
        }

        binding.tvOrderNo.text = "Order no. ${orderOBJ.id}"
        binding.tvStatus.text = orderOBJ.statusLabel
        binding.tvPrice.text = orderOBJ.orderCurrencyCode + " " + orderOBJ.grandTotal
        binding.tvDate.text = orderOBJ.createdAt
        binding.name.text = orderOBJ.customerFirstName
        binding.last.text = orderOBJ.customerLastName



        binding.btnCancel.setOnClickListener {
            showCancelOrderBottomSheet(view)
        }
        binding.btnDispatch.setOnClickListener {
            showDispatchOrderBottomSheet(view)
        }
        binding.btnRefund.setOnClickListener {
            showRefundOrderBottomSheet(view)
        }
        binding.ivAccept.setOnClickListener {
            showAcceptOrderBottomSheet(view)
        }
        binding.ivReject.setOnClickListener {
            showRejectOrderBottomSheet(view)
        }

        binding.tvStatus.text = orderOBJ.status
        when (orderOBJ.status?.lowercase()) {
            "pending" -> binding.tvStatus.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_oval_status_grey, 0, 0, 0,
            )
            "processing" -> binding.tvStatus.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_oval_status_yellow, 0, 0, 0,
            )
            "delivered" -> binding.tvStatus.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_oval_status_green, 0, 0, 0,
            )
            "canceled" -> binding.tvStatus.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_oval_status_red, 0, 0, 0,
            )
            "refund request" -> binding.tvStatus.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_oval_status_orange, 0, 0, 0,
            )
        }

        adapter = OrderProductsAdapter(requireActivity())
        view.list_products.layoutManager = LinearLayoutManager(context)
        view.list_products.adapter = adapter
        viewModel.getOrderDetails(orderOBJ.id!!).observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    dialog.dismiss()
                    val orderdata = (it.data as OrderDetailsResponse).data?.order
                    adapter?.setProducts(orderdata?.items)
                    binding.address.text = orderdata?.address?.country_name + "," + orderdata?.address?.city
                    binding.transId.text = orderdata?.id.toString()
                    binding.type.text = orderdata?.payment_title
                    binding.tvTotalAmount.text = orderdata?.grandTotal.toString()
                    binding.tvCurrency.text = orderdata?.orderCurrencyCode
                    binding.tvDelivery.text = "- " + orderdata?.formatedShippingAmount
                    binding.tvCommision.text = "- " + orderdata?.orderCurrencyCode + " " + (orderdata?.grandTotal!! * 0.05).toString()
                    binding.tvSettlment.text =  orderdata?.orderCurrencyCode + " " + (orderdata?.grandTotal - ((orderdata?.grandTotal!! * 0.05)  + orderdata?.shippingAmount!!)).toString()
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
    private lateinit var dialog: Dialog

    private fun showCancelOrderBottomSheet(v: View) {
        val modalbottomSheetFragment = CancelBottomSheet(fragmentView = v)
        modalbottomSheetFragment.show(
            requireActivity().supportFragmentManager,
            modalbottomSheetFragment.tag
        )

    }

    private fun showDispatchOrderBottomSheet(v: View) {
        val modalbottomSheetFragment = DispatchBottomSheet(fragmentView = v)
        modalbottomSheetFragment.show(
            requireActivity().supportFragmentManager,
            modalbottomSheetFragment.tag
        )
    }

    private fun showRefundOrderBottomSheet(v: View) {
        val modalbottomSheetFragment = RefundBottomSheet(fragmentView = v)
        modalbottomSheetFragment.show(
            requireActivity().supportFragmentManager,
            modalbottomSheetFragment.tag
        )
    }

    private fun showAcceptOrderBottomSheet(v: View) {
        val modalbottomSheetFragment = OrderRefundedBottomSheet(fragmentView = v)
        modalbottomSheetFragment.show(
            requireActivity().supportFragmentManager,
            modalbottomSheetFragment.tag
        )
    }

    private fun showRejectOrderBottomSheet(v: View) {
        val modalbottomSheetFragment = RejectRefundBottomSheet(fragmentView = v)
        modalbottomSheetFragment.show(
            requireActivity().supportFragmentManager,
            modalbottomSheetFragment.tag
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}