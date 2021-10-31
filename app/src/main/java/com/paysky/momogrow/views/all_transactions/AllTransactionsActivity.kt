package com.paysky.momogrow.views.all_transactions

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.paysky.momogrow.R
import com.paysky.momogrow.databinding.ActivityAllTransactionsBinding

class AllTransactionsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllTransactionsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllTransactionsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = AllTransactionsAdapter()
        binding.tvTransactions.setOnClickListener { p0 ->
            startActivity(Intent(this, FilterTransactionsActivity::class.java))
        }
        binding.tvOrders.setOnClickListener { p0 ->
            startActivity(Intent(this, FilterTransactionsActivity::class.java))

        }
    }

    fun finish(view: View) {finish()}
}