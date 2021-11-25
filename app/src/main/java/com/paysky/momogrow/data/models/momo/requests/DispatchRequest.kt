package com.paysky.momogrow.data.models.momo.requests

import com.google.gson.annotations.SerializedName

data class DispatchRequest(

    @field:SerializedName("shipping_method")
    var shippingMethod: String? = null,

    @field:SerializedName("order_id")
    var orderId: Int? = null
)
