package com.paysky.momogrow.views.all_transactions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.paysky.momogrow.R
import com.paysky.momogrow.databinding.ActivityAllTransactionsBinding
import com.paysky.momogrow.databinding.ActivityFilterTransactionsBinding

class FilterTransactionsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilterTransactionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterTransactionsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = AllTransactionsAdapter()
    }
}