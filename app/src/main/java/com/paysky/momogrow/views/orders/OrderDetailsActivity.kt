package com.paysky.momogrow.views.orders

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.paysky.momogrow.R
import com.paysky.momogrow.databinding.ActivityOrderDetailsBinding
import com.paysky.momogrow.databinding.FragmentOrderDetailsBinding
import com.paysky.momogrow.views.bottomsheets.*
import kotlinx.android.synthetic.main.activity_order_details.*
import kotlinx.android.synthetic.main.activity_order_details.view.*
import kotlinx.android.synthetic.main.custom_item_order.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_order_details.view.*

class OrderDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderDetailsBinding

    // When requested, this adapter returns a OrderDetailsFragment,
    // representing an object in the collection.
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
        val orders = intent.getParcelableArrayListExtra<OrdersAdapter.OrderObj>("orders")
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
    var orders: List<OrdersAdapter.OrderObj>?
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
        val view = binding.root

        val orderOBJ = arguments?.getSerializable(ARG_OBJECT) as OrdersAdapter.OrderObj
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

        binding.tvOrderNo.text = "Order no. ${orderOBJ.number}"
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
        when (orderOBJ.status) {
            "Not processed" -> {
                binding.tvStatus.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_oval_status_grey, 0, 0, 0,
                )
                binding.containerButtonsNotProcessed.visibility = View.VISIBLE
            }
            "In transit" -> {
                binding.tvStatus.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_oval_status_yellow, 0, 0, 0,
                )
            }
            "Delivered" -> {
                binding.tvStatus.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_oval_status_green, 0, 0, 0,
                )
                binding.containerDelivered.visibility = View.VISIBLE

            }
            "Cancelled" -> {
                binding.tvStatus.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_oval_status_red, 0, 0, 0,
                )
            }
            "Refund request" -> {
                binding.tvStatus.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_oval_status_orange, 0, 0, 0,
                )
                binding.containerButtonsRefundedRequest.visibility = View.VISIBLE

            }
        }
        return view
    }

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