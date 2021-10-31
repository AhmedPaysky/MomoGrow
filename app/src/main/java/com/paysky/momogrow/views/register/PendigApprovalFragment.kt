package com.paysky.momogrow.views.register

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.paysky.momogrow.databinding.FragmentPendigApprovalBinding
import com.paysky.momogrow.databinding.FragmentPendingApprovalProductBinding
import com.paysky.momogrow.views.home.HomeActivity
import kotlinx.android.synthetic.main.fragment_pendig_approval.*


class PendigApprovalFragment : Fragment() {

    private var _binding: FragmentPendigApprovalBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPendigApprovalBinding.inflate(inflater, container, false)
        val view = binding.root
//        object : CountDownTimer(2000, 1000) {
//            override fun onTick(p0: Long) {
//
//            }
//
//            override fun onFinish() {
//                startActivity(
//                    Intent(activity, HomeActivity::class.java)
//                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
//                )
//                requireActivity().finish()
//            }
//        }.start()

        binding.btnBack.setOnClickListener {

        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}