package com.paysky.momogrow.views.catalog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import com.paysky.momogrow.R
import com.paysky.momogrow.databinding.ActivityAuthenticateBinding
import com.paysky.momogrow.databinding.FragmentProductDetailsBinding
import kotlinx.android.synthetic.main.custom_item_catalog.view.*
import kotlinx.android.synthetic.main.custom_item_order.view.*

class ProductDetailsActivity : AppCompatActivity() {
    private lateinit var binding: FragmentProductDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentProductDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val name = intent?.getStringExtra("name")
        val status = intent?.getStringExtra("status")

        binding.containerButtons.visibility = View.GONE
        binding.tvName.text = name
        binding.tvNameFruit.text = name
        binding.tvStatus.text = status

        binding.ivBack.setOnClickListener {
            finish()
        }
        when (status) {
            "published" -> binding.tvStatus.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_oval_status_green, 0, 0, 0,
            )
            "under review" -> binding.tvStatus.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_oval_status_red, 0, 0, 0,
            )
            "unpublished" -> binding.tvStatus.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_oval_status_yellow, 0, 0, 0,
            )
        }

        when (name) {
            "Green Apple" -> binding.image.setImageDrawable(
                AppCompatResources.getDrawable(
                    this,
                    R.drawable.applepng
                )
            )
            "Carrot" -> binding.image.setImageDrawable(
                AppCompatResources.getDrawable(
                    this,
                    R.drawable.carrot
                )
            )
            "Tomato" -> binding.image.setImageDrawable(
                AppCompatResources.getDrawable(
                    this,
                    R.drawable.tomato
                )
            )
            "Banana" -> binding.image.setImageDrawable(
                AppCompatResources.getDrawable(
                    this,
                    R.drawable.bannana
                )
            )
        }

    }
}