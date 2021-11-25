package com.paysky.momogrow.data.models.momo.orders

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.paysky.momogrow.data.models.momo.DataItem
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.sql.Array

@Parcelize
data class OrderDetailsResponse(

    @field:SerializedName("status_code")
    val statusCode: Int? = null,

    @field:SerializedName("data")
    val data: DataDetails? = null,

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
) : Parcelable, Serializable


@Parcelize
data class ShippingAddress(
    @field:SerializedName("address1")
    val address1: List<String?>? = null,
    @field:SerializedName("country_name")
    val country_name: String?,
    @field:SerializedName("city")
    val city: String?,
) : Parcelable, Serializable


@Parcelize
data class OrderDetailsItem(

    @field:SerializedName("shipping_address")
    val address: ShippingAddress?,


    @field:SerializedName("items")
    val items: ArrayList<OrderProductItem?>?,


    @field:SerializedName("shipments")
    val shipments: ArrayList<ShipmentsModel?>?,

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

    @field:SerializedName("formated_shipping_amount")
    val formatedShippingAmount: String? = null,


    @field:SerializedName("shipping_amount")
    val shippingAmount: Double? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("increment_id")
    val incrementId: String? = null,

    @field:SerializedName("customer_email")
    val customerEmail: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("payment_title")
    val payment_title: String? = null,


    @field:SerializedName("grand_total")
    val grandTotal: Double? = null,

    @field:SerializedName("order_currency_code")
    val orderCurrencyCode: String? = null,

    @field:SerializedName("status")
    val status: String? = null
) : Parcelable, Serializable


@Parcelize
data class DataDetails(
    @field:SerializedName("order")
    val order: OrderDetailsItem?
) : Parcelable, Serializable

@Parcelize
data class OrderProductItem(
    @field:SerializedName("name")
    val name: String?,

    @field:SerializedName("sku")
    val sku: String?,

    @field:SerializedName("qty_ordered")
    val qty_ordered: Int?,

    @field:SerializedName("formated_price")
    val formated_price: String?,

    @field:SerializedName("product")
    val product: DataItem?
) : Parcelable, Serializable