package com.paysky.momogrow.views.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.paysky.momogrow.MyApplication
import com.paysky.momogrow.data.local.ProductEntity

class ProductViewModel() : ViewModel() {
    val dao = MyApplication.db.productDao()

    fun allProducts() = dao.getAllProducts()
    fun getProductById(id: Int) = dao.getProductById(id)

}