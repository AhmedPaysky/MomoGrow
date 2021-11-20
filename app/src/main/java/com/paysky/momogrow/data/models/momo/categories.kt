package com.paysky.momogrow.data.models.momo

import com.google.gson.annotations.SerializedName

data class Categories(

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
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("slug")
	val slug: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
