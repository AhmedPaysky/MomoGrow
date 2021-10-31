package com.paysky.momogrow.views.bottomsheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paysky.momogrow.R
import kotlinx.android.synthetic.main.fragment_modal_bottom_sheet_share_via_email.view.*

class ShareViaEmailBottomSheet(var fragmentView: View) : BottomSheetDialogFragment() {
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
            dismiss()
            showConfirmationBottomSheet(fragmentView)
        }
        return v
    }

    private fun showConfirmationBottomSheet(fragmentView: View) {
        val modalbottomSheetFragment = ConfirmationBottomSheet(fragmentView = fragmentView)
        modalbottomSheetFragment.show(
            requireActivity().supportFragmentManager,
            modalbottomSheetFragment.tag
        )
    }
}