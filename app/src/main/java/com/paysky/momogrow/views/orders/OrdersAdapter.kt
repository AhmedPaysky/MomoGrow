package com.paysky.momogrow.views.orders

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
import com.paysky.momogrow.views.orders.OrdersAdapter.MyViewHolder
import kotlinx.android.synthetic.main.custom_item_order.view.*
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class OrdersAdapter(var context: Context) : RecyclerView.Adapter<MyViewHolder>() {
    var orders = ArrayList<OrderObj>()
    private var ordersFilterList: MutableList<OrderObj>? = null

    init {
        for (i in 0..10) {
            orders.add(OrderObj())
        }
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

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val order = orders[position]
        holder.itemView.tvStatus.text = order.status
        holder.itemView.tvAmount.text = order.amount
        holder.itemView.tvOrderNo.text = "Order no. " + order.number
        holder.itemView.tvDate.text = order.date
        arrayOf("Not processed", "In transit", "Delivered", "Cancelled", "Refund request")

        when (order.status) {
            "Not processed" -> holder.itemView.tvStatus.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_oval_status_grey, 0, 0, 0,
            )
            "In transit" -> holder.itemView.tvStatus.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_oval_status_yellow, 0, 0, 0,
            )
            "Delivered" -> holder.itemView.tvStatus.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_oval_status_green, 0, 0, 0,
            )
            "Cancelled" -> holder.itemView.tvStatus.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_oval_status_red, 0, 0, 0,
            )
            "Refund request" -> holder.itemView.tvStatus.setCompoundDrawablesWithIntrinsicBounds(
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

    fun setOrdersList(ordersList: MutableList<OrderObj>?) {
        if (ordersList != null) {
            this.orders = ordersList as ArrayList<OrderObj>
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
                val status = orderModel.status.toUpperCase(Locale.getDefault())
                val number = orderModel.number.toUpperCase(Locale.getDefault())

                if (status[0] == charText[0] && status.contains(charText)) {
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

    class OrderObj() : Serializable, Parcelable {
        val statusArr =
            arrayOf("Not processed", "In transit", "Delivered", "Cancelled", "Refund request")
        val numberArr =
            arrayOf(
                "1234522",
                "6544212",
                "1234511",
                "4334567",
                "9034567"
            )
        val amountArr =
            arrayOf("150", "200", "120", "111", "252")

        var number: String = numberArr[Random.nextInt(0, 5)]
        var amount: String = amountArr[Random.nextInt(0, 5)]
        var date: String = "25/08/21 • 11:00 AM"
        var status: String = statusArr[Random.nextInt(0, 5)]

        constructor(parcel: Parcel) : this() {
            number = parcel.readString().toString()
            amount = parcel.readString().toString()
            date = parcel.readString().toString()
            status = parcel.readString().toString()
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(number)
            parcel.writeString(amount)
            parcel.writeString(date)
            parcel.writeString(status)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<OrderObj> {
            override fun createFromParcel(parcel: Parcel): OrderObj {
                return OrderObj(parcel)
            }

            override fun newArray(size: Int): Array<OrderObj?> {
                return arrayOfNulls(size)
            }
        }

    }
}
