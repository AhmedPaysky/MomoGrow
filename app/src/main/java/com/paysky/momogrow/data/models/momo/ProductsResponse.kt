package com.paysky.momogrow.data.models.momo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProductsResponse(

    @field:SerializedName("status_code")
    val statusCode: Int? = null,

    @field:SerializedName("data")
    val data: List<DataItem?>? = null,

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: Any? = null
) : Serializable

data class CategoriesItem(

    @field:SerializedName("code")
    val code: Any? = null,

    @field:SerializedName("display_mode")
    val displayMode: String? = null,

    @field:SerializedName("meta_title")
    val metaTitle: String? = null,

    @field:SerializedName("image_url")
    val imageUrl: Any? = null,

    @field:SerializedName("additional")
    val additional: Any? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("meta_keywords")
    val metaKeywords: String? = null,

    @field:SerializedName("meta_description")
    val metaDescription: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("slug")
    val slug: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)

data class Reviews(

    @field:SerializedName("total")
    val total: Int? = null,

    @field:SerializedName("percentage")
    val percentage: List<Any?>? = null,

    @field:SerializedName("total_rating")
    val totalRating: Int? = null,

    @field:SerializedName("average_rating")
    val averageRating: Int? = null
)

data class BaseImage(

    @field:SerializedName("large_image_url")
    val largeImageUrl: String? = null,

    @field:SerializedName("original_image_url")
    val originalImageUrl: String? = null,

    @field:SerializedName("small_image_url")
    val smallImageUrl: String? = null,

    @field:SerializedName("medium_image_url")
    val mediumImageUrl: String? = null
)

data class DataItem(

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

    @field:SerializedName("status")
    val status: String? = null,

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
    val categories: List<CategoriesItem?>? = null,

    @field:SerializedName("sku")
    val sku: String? = null
) : Serializable
