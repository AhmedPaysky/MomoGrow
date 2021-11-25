package com.paysky.momogrow.views.orders

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.paysky.momogrow.R
import com.paysky.momogrow.data.local.ProductEntity
import com.paysky.momogrow.data.models.momo.CatgoriesItem
import com.paysky.momogrow.data.models.momo.DataItem
import com.paysky.momogrow.data.models.momo.orders.OrderProductItem
import kotlinx.android.synthetic.main.categoryitem.view.*
import kotlinx.android.synthetic.main.custom_item_catalog.view.*
import kotlinx.android.synthetic.main.custom_item_order.view.tvAmount
import kotlinx.android.synthetic.main.custom_item_order.view.tvStatus
import kotlinx.android.synthetic.main.fragment_add_product.view.*
import kotlinx.android.synthetic.main.fragment_order_details.view.*
import kotlinx.android.synthetic.main.order_product_item.view.*
import java.util.*
import kotlin.random.Random

class OrderProductsAdapter(var mContext: Context) :
    RecyclerView.Adapter<OrderProductsAdapter.MyViewHolder>() {


    fun setProducts(products: List<OrderProductItem?>?) {
        this.products = products as MutableList<OrderProductItem>
        notifyDataSetChanged()
    }

    private var products = mutableListOf<OrderProductItem>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_product_item, parent, false)
        return MyViewHolder(view)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val prod = products[position]
        holder.itemView.product_name.text = prod.name
        holder.itemView.sku.text = "SKU " + prod.sku
        holder.itemView.tvAmount.text = prod.formated_price
        holder.itemView.product_qnt.text = "x" + prod.qty_ordered.toString()
        prod.product?.baseImage?.apply {
            Glide.with(mContext).load(originalImageUrl).placeholder(R.drawable.ic_mtn_logo)
                .into(holder.itemView.product_image)
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

}
