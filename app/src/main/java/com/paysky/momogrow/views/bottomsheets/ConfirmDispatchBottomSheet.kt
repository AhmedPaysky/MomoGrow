package com.paysky.momogrow.views.bottomsheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.OnReceiveContentListener
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paysky.momogrow.R
import com.paysky.momogrow.data.api.ApiClientMomo
import com.paysky.momogrow.data.api.ApiServiceMomo
import com.paysky.momogrow.helper.OnConfirmationBottomSheetButtonClicked
import com.paysky.momogrow.viewmodels.ViewModelFactoryMomo
import com.paysky.momogrow.views.orders.OrdersViewModel
import kotlinx.android.synthetic.main.fragment_modal_bottom_sheet_confrim_dispatch.view.*

class ConfirmDispatchBottomSheet(
    var fragmentView: View,
    var listener: OnConfirmationBottomSheetButtonClicked
) : BottomSheetDialogFragment() {
    private val viewModel: OrdersViewModel by activityViewModels {
        ViewModelFactoryMomo(
            ApiClientMomo.apiClient().create(
                ApiServiceMomo::class.java
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(
            R.layout.fragment_modal_bottom_sheet_confrim_dispatch,
            container,
            false
        )

        v.etDeliveryAddress.setText(viewModel.deliveryAddress.value)
        v.etPickupInstructions.setText(viewModel.pickupInstructions.value)
        v.btnNext.setOnClickListener {
            dismiss()
            listener.onConfirmationClicked("", "dispatch")
        }
        return v
    }


}