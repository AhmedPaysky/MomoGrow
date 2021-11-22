package com.paysky.momogrow.views.products

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.paysky.momogrow.R
import com.paysky.momogrow.data.local.ProductEntity
import com.paysky.momogrow.data.models.momo.DataItem
import kotlinx.android.synthetic.main.custom_item_catalog.view.*
import kotlinx.android.synthetic.main.custom_item_order.view.tvAmount
import kotlinx.android.synthetic.main.custom_item_order.view.tvStatus
import java.util.*
import kotlin.random.Random

class ProductsAdapter(var mContext: Context) :
    RecyclerView.Adapter<ProductsAdapter.MyViewHolder>() {

    companion object {
        var products = mutableListOf<DataItem>()
        var fillListFirstTime = true
    }

    fun setProducts(products: List<DataItem?>?) {
        this.products = products as MutableList<DataItem>
        this.productsFilterList = mutableListOf()
        products.let { this.productsFilterList?.addAll(it) }
        notifyDataSetChanged()
    }

    private var products = mutableListOf<DataItem>()
    private var productsFilterList: MutableList<DataItem>? = null
    private var listener: onItemClick? = null

    interface onItemClick {
        fun onClicked(productObj: DataItem)
    }

    public fun setListener(onItemClick: onItemClick) {
        this.listener = onItemClick
    }

    init {
//        if (fillListFirstTime) {
//            for (i in 0..10) {
//                products.add(ProductEntity())
//            }
//            fillListFirstTime = false
//        }
        this.productsFilterList = mutableListOf()
        products.let { this.productsFilterList?.addAll(it) }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_item_catalog, parent, false)
        return MyViewHolder(view)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product = products[position]
        holder.itemView.tvName.text = product.name
        holder.itemView.tvPublishStatus.text = mContext.getString(R.string.sKU) + " " + product.sku

        when (product.inStock) {
            true -> {
                holder.itemView.tvStatus.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_oval_status_green, 0, 0, 0,
                )
                holder.itemView.tvStatus.text = mContext.getString(R.string.instock)

            }
            false -> {
                holder.itemView.tvStatus.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_oval_status_red, 0, 0, 0,
                )
                holder.itemView.tvStatus.text = mContext.getString(R.string.outstock)
            }
        }
        product.baseImage?.apply {
            Glide.with(mContext).load(originalImageUrl).placeholder(R.drawable.ic_mtn_logo)
                .into(holder.itemView.icon)
        }
        holder.itemView.setOnClickListener {
            if (listener != null) {
                listener?.onClicked(product)
            }
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun filter(charText1: String) {
        val charText = charText1.toUpperCase(Locale.getDefault())
        products?.clear()
        if (charText.isEmpty() && products != null) {
            productsFilterList?.let { products?.addAll(it) }
        } else {
            for (productModel in productsFilterList!!) {
                var status = ""
                if (productModel.inStock!!) status =
                    mContext.getString(R.string.instock) else status = mContext.getString(
                    R.string.outstock
                )
                val qStatus = productModel.status?.toUpperCase(Locale.getDefault())
                if (status[0] == charText[0] && status.contains(charText)) {
                    products?.add(productModel)
                } else if (qStatus!![0] == charText[0] && qStatus.contains(charText)) {
                    products?.add(productModel)
                }
            }
        }
        notifyDataSetChanged()
    }
    }
