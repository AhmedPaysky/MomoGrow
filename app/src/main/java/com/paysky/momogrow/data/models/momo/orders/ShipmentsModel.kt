package com.paysky.momogrow.data.models.momo.orders

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class ShipmentsModel(

    @field:SerializedName("track_number")
    val trackNumber: String? = null,

    @field:SerializedName("status")
    val status: String? = null,

    ) : Parcelable, Serializable
