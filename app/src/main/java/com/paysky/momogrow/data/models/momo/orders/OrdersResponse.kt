package com.paysky.momogrow.data.models.momo.orders

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class OrdersResponse(

    @field:SerializedName("status_code")
    val statusCode: Int? = null,

    @field:SerializedName("data")
    val data: Data? = null,

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
) : Parcelable, Serializable

@Parcelize
data class OrdersItem(

    @field:SerializedName("channel_name")
    val channelName: String? = null,

    @field:SerializedName("total_qty_ordered")
    val totalQtyOrdered: Int? = null,

    @field:SerializedName("customer_first_name")
    val customerFirstName: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("total_item_count")
    val totalItemCount: Int? = null,

    @field:SerializedName("status_label")
    val statusLabel: String? = null,

    @field:SerializedName("customer_last_name")
    val customerLastName: String? = null,

    @field:SerializedName("formated_grand_total")
    val formatedGrandTotal: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("increment_id")
    val incrementId: String? = null,

    @field:SerializedName("customer_email")
    val customerEmail: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("grand_total")
    val grandTotal: Double? = null,

    @field:SerializedName("order_currency_code")
    val orderCurrencyCode: String? = null,

    @field:SerializedName("status")
    val status: String? = null
) : Parcelable, Serializable


@Parcelize
data class Data(

    @field:SerializedName("count")
    val count: Int? = null,

    @field:SerializedName("orders")
    val orders: List<OrdersItem?>? = null
) : Parcelable, Serializable

