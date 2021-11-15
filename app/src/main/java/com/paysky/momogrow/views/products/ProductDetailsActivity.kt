package com.paysky.momogrow.views.products

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.Observer
import com.paysky.momogrow.MyApplication
import com.paysky.momogrow.R
import com.paysky.momogrow.databinding.FragmentProductDetailsBinding
import kotlinx.android.synthetic.main.custom_item_catalog.view.*
import kotlinx.android.synthetic.main.custom_item_order.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetailsActivity : AppCompatActivity() {
    private lateinit var binding: FragmentProductDetailsBinding
    private val viewModel: ProductViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentProductDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val name = intent?.getStringExtra("name")
        val status = intent?.getStringExtra("status")
        val productId = intent?.getIntExtra("productId", 0)


//        binding.containerButtons.visibility = View.GONE


        viewModel.getProductById(productId!!)
            .observe(this@ProductDetailsActivity, Observer {
                binding.tvStatus.text = it.status
                binding.tvNameFruit.text = it.name
                binding.tvCategory.text = it.category
                binding.tvName.text = it.category
                binding.tvDescripyion.text = it.description
                binding.tvSku.text = it.sku
                binding.tvPrice.text = it.price
                binding.tvWidth.text = it.width
                binding.tvHeight.text = it.height
                binding.tvWeight.text = it.weight
                binding.tvQuantity.text = it.quantity
                binding.switchFeature.isChecked = it.featureUser
                binding.switchNew.isChecked = it.new
                binding.switchPublish.isChecked = it.publish
            })

        binding.ivBack.setOnClickListener {
            finish()
        }
        when (status) {
            "published" -> binding.tvStatus.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_oval_status_green, 0, 0, 0,
            )
            "under review" -> binding.tvStatus.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_oval_status_yellow, 0, 0, 0,
            )
            "unpublished" -> binding.tvStatus.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_oval_status_red, 0, 0, 0,
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