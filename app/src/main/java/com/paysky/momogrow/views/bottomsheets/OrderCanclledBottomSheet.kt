package com.paysky.momogrow.views.bottomsheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paysky.momogrow.R
import com.paysky.momogrow.helper.OnConfirmationBottomSheetButtonClicked
import kotlinx.android.synthetic.main.fragment_modal_bottom_sheet_order_canclled.view.*
import kotlinx.android.synthetic.main.fragment_order_details.view.*
import javax.xml.transform.ErrorListener

class OrderCanclledBottomSheet(
    var fragmentView: View
) : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(
            R.layout.fragment_modal_bottom_sheet_order_canclled,
            container,
            false
        )
        v.btnOk.setOnClickListener {
            dismiss()
        }
        return v
    }

}