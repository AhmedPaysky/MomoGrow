package com.paysky.momogrow.data.models.momo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MainOfMainCategories(

    @field:SerializedName("status_code")
    val statusCode: Int? = null,

    @field:SerializedName("data")
    val data: MainCategoriesModel? = null,

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
) : Serializable

data class ProductsResponse(

    @field:SerializedName("status_code")
    val statusCode: Int? = null,

    @field:SerializedName("data")
    val data: AllProductsData? = null,

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
) : Serializable

data class BaseImage(

    @field:SerializedName("large_image_url")
    val largeImageUrl: String? = null,

    @field:SerializedName("original_image_url")
    val originalImageUrl: String? = null,

    @field:SerializedName("small_image_url")
    val smallImageUrl: String? = null,

    @field:SerializedName("medium_image_url")
    val mediumImageUrl: String? = null
) : Serializable

data class DataItem(

    @field:SerializedName("short_description")
    val shortDescription: String? = null,

    @field:SerializedName("featured")
    val featured: Int? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("variants")
    val variants: List<String?>? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("in_stock")
    val inStock: Boolean? = null,

    @field:SerializedName("reviews")
    val reviews: Reviews? = null,

    @field:SerializedName("is_saved")
    val isSaved: Boolean? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("price")
    val price: Int? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("categories")
    val categories: List<CatgoriesItem?>? = null,

    @field:SerializedName("sku")
    val sku: String? = null,

    @field:SerializedName("height")
    val height: String? = null,

    @field:SerializedName("formated_price")
    val formatedPrice: String? = null,

    @field:SerializedName("new")
    val jsonMemberNew: Int? = null,

    @field:SerializedName("images")
    val images: List<Inages?>? = null,

    @field:SerializedName("active")
    val active: Int? = null,

    @field:SerializedName("weight")
    val weight: String? = null,

    @field:SerializedName("url_key")
    val urlKey: String? = null,

    @field:SerializedName("base_image")
    val baseImage: BaseImage? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("width")
    val width: String? = null,

    @field:SerializedName("quantity")
    val quantity: Int? = null,

    @field:SerializedName("status")
 val status: String? = null
) : Serializable

data class Reviews(

    @field:SerializedName("total")
    val total: Int? = null,

    @field:SerializedName("percentage")
    val percentage: List<String?>? = null,

    @field:SerializedName("total_rating")
    val totalRating: Int? = null,

    @field:SerializedName("average_rating")
    val averageRating: Int? = null
) : Serializable

data class AllProductsData(

    @field:SerializedName("categories")
    val catgories: List<CatgoriesItem?>? = null,

    @field:SerializedName("products")
    val products: List<DataItem?>? = null
) : Serializable

data class MainCategoriesModel(
    @field:SerializedName("categories")
    val catgories: ArrayList<CatgoriesItem?>?,
) : Serializable


data class CatgoriesItem(

    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("display_mode")
    val displayMode: String? = null,

    @field:SerializedName("meta_title")
    val metaTitle: String? = null,

    @field:SerializedName("image_url")
    val imageUrl: String? = null,

    @field:SerializedName("additional")
    val additional: String? = null,

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
    var name: String? = null,

    @field:SerializedName("id")
    var id: Int? = null,

    @field:SerializedName("slug")
    val slug: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
) : Serializable
