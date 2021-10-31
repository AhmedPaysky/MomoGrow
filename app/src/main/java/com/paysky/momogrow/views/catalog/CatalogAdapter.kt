package com.paysky.momogrow.views.catalog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.paysky.momogrow.R
import kotlinx.android.synthetic.main.custom_item_catalog.view.*
import kotlinx.android.synthetic.main.custom_item_order.view.tvAmount
import kotlinx.android.synthetic.main.custom_item_order.view.tvStatus
import java.util.*
import kotlin.random.Random

class CatalogAdapter(var mContext: Context) : RecyclerView.Adapter<CatalogAdapter.MyViewHolder>() {

    companion object {
        var products = mutableListOf<ProductObj>()
        var fillListFirstTime = true
    }

    private var productsFilterList: MutableList<ProductObj>? = null
    private var listener: onItemClick? = null

    interface onItemClick {
        fun onClicked(productObj: ProductObj)
    }

    public fun setListener(onItemClick: onItemClick) {
        this.listener = onItemClick
    }

    init {
        if (fillListFirstTime) {
            for (i in 0..10) {
                products.add(ProductObj())
            }
            fillListFirstTime = false
        }
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
        holder.itemView.tvStatus.text = product.status
        holder.itemView.tvAmount.text = product.qStatus
        holder.itemView.tvName.text = product.name
        arrayOf("Out of stock", "In stock")

        when (product.status) {
            "In stock" -> holder.itemView.tvStatus.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_oval_status_green, 0, 0, 0,
            )
            "Out of stock" -> holder.itemView.tvStatus.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_oval_status_red, 0, 0, 0,
            )
        }

        when (product.name) {
            "Green Apple" -> holder.itemView.icon.setImageDrawable(
                AppCompatResources.getDrawable(
                    mContext,
                    R.drawable.applepng
                )
            )
            "Carrot" -> holder.itemView.icon.setImageDrawable(
                AppCompatResources.getDrawable(
                    mContext,
                    R.drawable.carrot
                )
            )
            "Tomato" -> holder.itemView.icon.setImageDrawable(
                AppCompatResources.getDrawable(
                    mContext,
                    R.drawable.tomato
                )
            )
            "Banana" -> holder.itemView.icon.setImageDrawable(
                AppCompatResources.getDrawable(
                    mContext,
                    R.drawable.bannana
                )
            )

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
            for (orderModel in productsFilterList!!) {
                val status = orderModel.status.toUpperCase(Locale.getDefault())
                val qStatus = orderModel.qStatus.toUpperCase(Locale.getDefault())

                if (status[0] == charText[0] && status.contains(charText)) {
                    products?.add(orderModel)
                } else if (qStatus[0] == charText[0] && qStatus.contains(charText)) {
                    products?.add(orderModel)
                }
            }
        }
        notifyDataSetChanged()
    }


    class ProductObj {
        val statusArr =
            arrayOf("In stock", "Out of stock")
        val nameArr =
            arrayOf(
                "Green Apple ",
                "Tomato",
                "Banana",
                "Carrot",
            )
        val qStatusArr =
            arrayOf("published", "unpublished", "under review")

        var name: String = nameArr[Random.nextInt(0, 4)]
        var qStatus: String = qStatusArr[Random.nextInt(0, 3)]
        var status: String = statusArr[Random.nextInt(0, 2)]

    }
}
