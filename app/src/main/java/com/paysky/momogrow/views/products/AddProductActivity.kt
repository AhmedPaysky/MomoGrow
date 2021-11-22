package com.paysky.momogrow.views.products

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.paysky.momogrow.databinding.ActivityAddProductBinding

class AddProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}