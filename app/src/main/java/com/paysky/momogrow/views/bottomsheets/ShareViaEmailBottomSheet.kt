package com.paysky.momogrow.views.bottomsheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paysky.momogrow.R
import com.paysky.momogrow.utilis.MyUtils
import com.paysky.momogrow.helper.OnBottomSheetButtonClicked
import kotlinx.android.synthetic.main.fragment_modal_bottom_sheet_share_via_email.view.*

class ShareViaEmailBottomSheet(var fragmentView: View, var listener: OnBottomSheetButtonClicked) :
    BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(
            R.layout.fragment_modal_bottom_sheet_share_via_email,
            container,
            false
        )
        v.btnNext.setOnClickListener {
            if (!MyUtils.isEmailValid(v.etEmail.text.toString())) {
                v.inputEmail.error = getString(R.string.invalid_email_msg)
                return@setOnClickListener
            }
            dismiss()
            listener.onClicked(v.etEmail.text.toString(),"email")
        }
        return v
    }


}