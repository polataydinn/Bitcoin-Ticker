package com.aydinpolat.bitcointicker.data.remote.model

import com.google.gson.annotations.SerializedName

data class CurrentPrice(
    @SerializedName("usd")
    val usd: Double
)