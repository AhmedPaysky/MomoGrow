package com.paysky.momogrow.views.bottomsheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paysky.momogrow.R
import kotlinx.android.synthetic.main.fragment_modal_bottom_sheet_dispatch.view.*

class DispatchBottomSheet(var fragmentView: View) : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(
            R.layout.fragment_modal_bottom_sheet_dispatch,
            container,
            false
        )

        v.btnNext.setOnClickListener {
            dismiss()
            showConfirmDispatchBottomSheet(fragmentView)
        }
        return v
    }

    private fun showConfirmDispatchBottomSheet(v: View) {
        val modalbottomSheetFragment = ConfirmDispatchBottomSheet(fragmentView = v)
        modalbottomSheetFragment.show(
            requireActivity().supportFragmentManager,
            modalbottomSheetFragment.tag
        )
    }
}