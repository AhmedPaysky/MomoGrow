package com.paysky.momogrow.data.models.momo.requests

import java.io.Serializable

class AddProductRequestModel : Serializable {
    val channel: String = "emanfateen"
    val type: String = "simple"
    val locale: String = "en"
    val visible_individually: String = "1"
    val status: String = "1"
    lateinit var name: String
    lateinit var meta_title: String
    lateinit var url_key: String
    lateinit var description: String
    lateinit var short_description: String
    lateinit var meta_description: String
    lateinit var price: String
    lateinit var sku: String
    lateinit var width: String
    lateinit var height: String
    lateinit var weight: String
    var quantity: Int = 0
    var featured: Int = 0
    var new: Int = 0
    var show_on_marketplace: Int = 0
    lateinit var categories: ArrayList<Int>
    var attribute_family_id: Int = 0
    var images: HashMap<String, String>? = null
}