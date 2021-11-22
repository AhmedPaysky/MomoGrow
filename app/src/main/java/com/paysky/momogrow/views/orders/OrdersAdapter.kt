package com.paysky.momogrow.views.orders

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.internal.ParcelableSparseArray
import com.paysky.momogrow.R
import com.paysky.momogrow.data.models.momo.orders.OrdersItem
import com.paysky.momogrow.data.models.momo.orders.OrdersResponse
import com.paysky.momogrow.views.orders.OrdersAdapter.MyViewHolder
import kotlinx.android.synthetic.main.custom_item_order.view.*
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class OrdersAdapter(var context: Context) : RecyclerView.Adapter<MyViewHolder>() {
    var orders = ArrayList<OrdersItem>()
    private var ordersFilterList: MutableList<OrdersItem>? = null

    init {
//        for (i in 0..10) {
//            orders.add(OrderObj())
//        }
        this.ordersFilterList = mutableListOf()
        orders.let { this.ordersFilterList?.addAll(it) }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_item_order, parent, false)
        return MyViewHolder(view)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val order = orders[position]
        holder.itemView.tvStatus.text = order.statusLabel
        holder.itemView.tvCurrency.text = order.orderCurrencyCode
        holder.itemView.tvAmount.text = order.grandTotal.toString()
        holder.itemView.tvOrderNo.text = "Order no. " + order.id
        holder.itemView.tvDate.text = order.createdAt
        arrayOf("Not processed", "In transit", "Delivered", "Cancelled", "Refund request")

        when (order.status?.lowercase()) {
            "pending" -> holder.itemView.tvStatus.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_oval_status_grey, 0, 0, 0,
            )
            "processing" -> holder.itemView.tvStatus.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_oval_status_yellow, 0, 0, 0,
            )
            "delivered" -> holder.itemView.tvStatus.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_oval_status_green, 0, 0, 0,
            )
            "canceled" -> holder.itemView.tvStatus.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_oval_status_red, 0, 0, 0,
            )
            "refund request" -> holder.itemView.tvStatus.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_oval_status_orange, 0, 0, 0,
            )

        }
        holder.itemView.setOnClickListener {
            context.startActivity(
                Intent(
                    context,
                    OrderDetailsActivity::class.java
                ).putParcelableArrayListExtra("orders", orders)
                    .putExtra("position", position)
            )
        }
    }

    fun setOrdersList(ordersList: ArrayList<OrdersItem>?) {
        if (ordersList != null) {
            this.orders = ordersList
        }
        ordersList?.let { this.ordersFilterList?.addAll(it) }
        notifyDataSetChanged()
    }

    fun filter(charText1: String) {
        val charText = charText1.toUpperCase(Locale.getDefault())
        orders?.clear()
        if (charText.isEmpty() && orders != null) {
            ordersFilterList?.let { orders?.addAll(it) }
        } else {
            for (orderModel in ordersFilterList!!) {
                val status = orderModel.status?.toUpperCase(Locale.getDefault())
                val number = orderModel.id.toString().toUpperCase(Locale.getDefault())

                if (status?.get(0) == charText[0] && status.contains(charText)) {
                    orders?.add(orderModel)
                } else if (number[0] == charText[0] && number.contains(charText)) {
                    orders?.add(orderModel)
                }
            }
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return orders.size
    }
}
