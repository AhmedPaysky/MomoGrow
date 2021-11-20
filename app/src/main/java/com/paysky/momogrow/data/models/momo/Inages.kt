package com.paysky.momogrow.data.models.momo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Inages(

    @field:SerializedName("path")
    val path: String? = null,

    @field:SerializedName("original_image_url")
    val originalImageUrl: String? = null,

    @field:SerializedName("large_image_url")
    val largeImageUrl: String? = null,

    @field:SerializedName("small_image_url")
    val smallImageUrl: String? = null,

    @field:SerializedName("medium_image_url")
    val mediumImageUrl: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("url")
    val url: String? = null
) : Serializable
