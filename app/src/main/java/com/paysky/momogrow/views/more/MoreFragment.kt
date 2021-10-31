package com.paysky.momogrow.views.more

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.paysky.momogrow.databinding.FragmentMoreBinding
import com.paysky.momogrow.databinding.FragmentOrdersBinding
import com.paysky.momogrow.views.orders.OrdersAdapter
import kotlinx.android.synthetic.main.fragment_orders.view.*

import android.R
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.Button
import androidx.appcompat.widget.LinearLayoutCompat
import android.view.Gravity

import android.view.WindowManager


class MoreFragment : Fragment() {
    private var _binding: FragmentMoreBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMoreBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.tvAccountDetails.setOnClickListener {
            startActivity(Intent(requireActivity(), AccountDetailsActivity::class.java))
        }

        binding.tvLocation.setOnClickListener {
            startActivity(Intent(requireActivity(), LocationActivity::class.java))
        }

        binding.tvStore.setOnClickListener {
            launchStoreDlg()
        }

        binding.tvLogout.setOnClickListener {
        }
        return view
    }

    private fun launchStoreDlg() {
        var dialog = Dialog(requireActivity(), android.R.style.Theme_Dialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(com.paysky.momogrow.R.layout.store_dialog_laout)
        dialog.setCanceledOnTouchOutside(true)
        val btnNext: Button = dialog.findViewById(com.paysky.momogrow.R.id.btnNext) as Button
        val btnLater: Button = dialog.findViewById(com.paysky.momogrow.R.id.btnLater) as Button
        btnNext.setOnClickListener {
            dialog.dismiss()
        }
        btnLater.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setCanceledOnTouchOutside(false)
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        dialog.window?.setLayout(
            LinearLayoutCompat.LayoutParams.MATCH_PARENT,
            LinearLayoutCompat.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

        dialog.show()
    }

}