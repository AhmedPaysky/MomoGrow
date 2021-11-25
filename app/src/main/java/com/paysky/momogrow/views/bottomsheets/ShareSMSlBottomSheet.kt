package com.paysky.momogrow.views.bottomsheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paysky.momogrow.R
import com.paysky.momogrow.helper.OnBottomSheetButtonClicked
import kotlinx.android.synthetic.main.fragment_modal_bottom_sheet_share_sms.view.*

class ShareSMSlBottomSheet(var fragmentView: View, var listener: OnBottomSheetButtonClicked) :
    BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(
            R.layout.fragment_modal_bottom_sheet_share_sms,
            container,
            false
        )
        v.btnNext.setOnClickListener {
            if (v.etMobileNum.text.toString().isEmpty()) {
                v.inputMobileNum.error = getString(R.string.invalid_email_msg)
                return@setOnClickListener
            }

            dismiss()
            listener.onClicked(v.etMobileNum.text.toString(), "sms")

//            showConfirmationBottomSheet(fragmentView)
        }
        return v
    }
    
}