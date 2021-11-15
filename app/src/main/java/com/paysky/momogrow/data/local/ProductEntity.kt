package com.paysky.momogrow.data.local

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


@Entity(tableName = "product_table")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var name: String,
    var description: String,
    var price: String,
    var category: String,
    var sku: String,
    var width: String,
    var weight: String,
    var height: String,
    var quantity: String,
    var status: String,
    var qStatus: String,
    var featureUser: Boolean,
    var new: Boolean,
    var publish: Boolean
) {
    constructor() : this(
        0, "", "", "", "", "", "", "", "", "", "", "", false, false, false
    )
}
