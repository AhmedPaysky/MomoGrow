package com.paysky.momogrow.views.bottomsheets

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.OnReceiveContentListener
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paysky.momogrow.R
import com.paysky.momogrow.databinding.ActivityQractivityBinding
import com.paysky.momogrow.utilis.OnPayLinkDetailsClicked
import com.paysky.momogrow.views.home.PaylinkDetailsActivity
import kotlinx.android.synthetic.main.fragment_modal_bottom_sheet_cancel.view.*
import kotlinx.android.synthetic.main.fragment_modal_bottom_sheet_confirmation.view.*
import kotlinx.android.synthetic.main.fragment_modal_bottom_sheet_share_via_email.view.*

class ConfirmationBottomSheet(
    var fragmentView: View,
    var listener: OnPayLinkDetailsClicked,
    var fromWhere: String = "normal"
) :
    BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(
            R.layout.fragment_modal_bottom_sheet_confirmation,
            container,
            false
        )
        v.btnClose.setOnClickListener {
            dismiss()
            if (fromWhere == "qr") requireActivity().finish()
        }
        v.btnDetails.setOnClickListener {
            dismiss()
            listener.onPayLinkDetailsClicked()
        }
        return v
    }
}