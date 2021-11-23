package com.paysky.momogrow.views.products

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.paysky.momogrow.MyApplication
import com.paysky.momogrow.R
import com.paysky.momogrow.R2
import com.paysky.momogrow.data.api.ApiClientMomo
import com.paysky.momogrow.data.api.ApiServiceMomo
import com.paysky.momogrow.data.local.ProductEntity
import com.paysky.momogrow.data.models.momo.DataItem
import com.paysky.momogrow.data.models.momo.SimpleResponse
import com.paysky.momogrow.databinding.FragmentProductDetailsBinding
import com.paysky.momogrow.helper.Status
import com.paysky.momogrow.utilis.MyUtils
import com.paysky.momogrow.viewmodels.ViewModelFactoryMomo
import kotlinx.android.synthetic.main.custom_item_catalog.view.*

class ProductDetailsActivity : AppCompatActivity() {
    private lateinit var dialog: Dialog
    private lateinit var binding: FragmentProductDetailsBinding
    private val viewModel: ProductViewModel by viewModels() {
        ViewModelFactoryMomo(
            ApiClientMomo.apiClient().create(
                ApiServiceMomo::class.java
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentProductDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        dialog = MyUtils.getDlgProgress(this)
        val itObj = intent?.getSerializableExtra("obj") as DataItem
        itObj.baseImage?.apply {
            Glide.with(view.context).load(originalImageUrl).placeholder(R.drawable.ic_mtn_logo)
                .into(binding.image)
        }
        binding.tvStatus.text = itObj.status
        binding.tvNameFruit.text = itObj.name
        itObj.categories?.forEach {
            binding.tvCategory.text = it?.name
        }
        binding.tvName.text = itObj.type
        binding.tvDescripyion.text = itObj.description
        binding.tvSku.text = itObj.sku
        itObj.formatedPrice.let {
            binding.tvPrice.text = it?.toString()
        }
        binding.tvWidth.text = itObj.width
        binding.tvHeight.text = itObj.height
        binding.tvWeight.text = itObj.weight
        binding.tvQuantity.text = itObj.quantity.toString()
        binding.switchFeature.isChecked = itObj.featured == 1
        binding.switchNew.isChecked = itObj.jsonMemberNew == 1
        binding.switchPublish.isChecked = itObj.active == 1

        binding.btnDelete.setOnClickListener {
            viewModel.deleteProduct(itObj.id.toString()).observe(this, Observer {
                when (it.status) {
                    Status.SUCCESS -> {
                        dialog.dismiss()
                        if ((it.data as SimpleResponse).success!!) {
                            Toast.makeText(this, it.data.message, Toast.LENGTH_LONG).show()
                            finish()
                        }
                    }
                    Status.ERROR -> {
                        dialog.dismiss()
                    }
                    Status.LOADING -> {
                        dialog.show()
                    }
                }
            })
        }
        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.btnEdit.setOnClickListener {
            MyApplication.productObj = itObj
            startActivity(Intent(this, AddProductActivity::class.java))
        }

        when (itObj.status?.lowercase()) {
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


        if (itObj.images?.size!! > 0) {
            for (i in 0..itObj.images.size) {
                if (i >= itObj.images.size){
                    break
                }
                else if (i == 0) {
                    Glide.with(view.context).load(itObj.images[i]?.mediumImageUrl).placeholder(R.drawable.ic_add_product).into(binding.ivImage1)
                }
                else if (i == 1) {
                    Glide.with(view.context).load(itObj.images[i]?.mediumImageUrl).placeholder(R.drawable.ic_add_product).into(binding.ivImage2)
                }
                else if (i == 2) {
                    Glide.with(view.context).load(itObj.images[i]?.mediumImageUrl).placeholder(R.drawable.ic_add_product).into(binding.ivImage3)
                }
                else {
                    Glide.with(view.context).load(itObj.images[i]?.mediumImageUrl).placeholder(R.drawable.ic_add_product).into(binding.ivImage4)
                }
            }
        }

    }
}