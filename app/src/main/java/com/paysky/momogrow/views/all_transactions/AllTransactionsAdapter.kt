package com.paysky.momogrow.views.all_transactions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paysky.momogrow.R
import com.paysky.momogrow.databinding.CustomItemTransactionBinding

class AllTransactionsAdapter : RecyclerView.Adapter<AllTransactionsAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_item_transaction, parent, false)
        return MyViewHolder(view)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = CustomItemTransactionBinding.bind(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        with(holder) {
            if (position == 0) {
                binding.tvDate.visibility = View.VISIBLE
                binding.tvDate.text = "Today"
            }
            if (position == 3) {
                binding.tvDate.visibility = View.VISIBLE
                binding.tvDate.text = "Yesterday"
            }
        }
    }

    override fun getItemCount(): Int {
        return 10
    }

}