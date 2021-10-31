package com.paysky.momogrow.views.bottomsheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paysky.momogrow.R
import kotlinx.android.synthetic.main.fragment_modal_bottom_sheet_order_canclled.view.*
import kotlinx.android.synthetic.main.fragment_order_details.view.*

class OrderRefundedBottomSheet(var fragmentView: View) : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(
            R.layout.fragment_modal_bottom_sheet_order_refunded,
            container,
            false
        )
        v.btnOk.setOnClickListener {
            dismiss()
            fragmentView.container_delivered.visibility = View.GONE
            fragmentView.container_buttons_refunded_request.visibility = View.GONE
            fragmentView.tvStatus.text = getString(R.string.refunded)
            fragmentView.tvStatus.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_oval_green,
                0,
                0,
                0
            )
        }
        return v
    }
}