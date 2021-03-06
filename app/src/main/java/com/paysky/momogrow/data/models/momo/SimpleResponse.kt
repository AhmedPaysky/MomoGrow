package com.paysky.momogrow.data.models.momo

import com.google.gson.annotations.SerializedName

data class SimpleResponse(

    @field:SerializedName("status_code")
    val statusCode: Int? = null,

    @field:SerializedName("data")
    val data: Any? = null,

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)

data class DataDispatch(
    @field:SerializedName("reference_number")
    val reference_number: Int? = null,
    @field:SerializedName("status")
    val status: Int? = null
)
