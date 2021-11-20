package com.paysky.momogrow.data.models.momo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AttributesFamiliesMainModel(

    @field:SerializedName("status_code")
    val statusCode: Int? = null,

    @field:SerializedName("data")
    val data: AttributesFamiliesModel? = null,

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: Any? = null
) : Serializable

data class AttributesFamiliesModel(
    @field:SerializedName("families")
    val families: ArrayList<AttributesItem?>
): Serializable

data class AttributesItem(
    @field:SerializedName("id")
    val id: Int
): Serializable