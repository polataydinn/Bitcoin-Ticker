package com.aydinpolat.bitcointicker.data.remote.model

import com.google.gson.annotations.SerializedName

data class Description(
    @SerializedName("en")
    val en: String,
)
