package com.paysky.momogrow.data.models.momo.orders

import com.google.gson.annotations.SerializedName


data class DispatchResponse(

    @field:SerializedName("status_code")
    val statusCode: Int? = null,

    @field:SerializedName("data")
    val data: DataDispatch? = null,

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)

data class DataDispatch(
    @field:SerializedName("reference_number")
    val reference_number: String? = null,
    @field:SerializedName("status")
    val status: String? = null
)
