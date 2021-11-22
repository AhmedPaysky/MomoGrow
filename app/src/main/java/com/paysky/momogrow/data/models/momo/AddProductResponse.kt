package com.paysky.momogrow.data.models.momo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AddProductResponse(

    @field:SerializedName("status_code")
    val statusCode: Int? = null,

    @field:SerializedName("data")
    val data: Data? = null,

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
): Serializable

data class Data(
    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("new")
    val new: Int? = null,

    @field:SerializedName("active")
    val active: Int? = null,

    @field:SerializedName("featured")
    val featured: Int? = null,

    @field:SerializedName("width")
    val width: Double? = null,

    @field:SerializedName("height")
    val height: Double? = null,

    @field:SerializedName("weight")
    val weight: Double? = null,

    @field:SerializedName("formated_price")
    val formatedPrice: String? = null,

    @field:SerializedName("short_description")
    val shortDescription: String? = null,

    @field:SerializedName("images")
    val images: List<Any?>? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("variants")
    val variants: List<Any?>? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("in_stock")
    val inStock: Boolean? = null,

    @field:SerializedName("url_key")
    val urlKey: String? = null,

    @field:SerializedName("base_image")
    val baseImage: BaseImage? = null,

    @field:SerializedName("reviews")
    val reviews: Reviews? = null,

    @field:SerializedName("is_saved")
    val isSaved: Boolean? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("price")
    val price: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("categories")
    val categories: List<CatgoriesItem?>? = null,

    @field:SerializedName("sku")
    val sku: String? = null
) : Serializable
