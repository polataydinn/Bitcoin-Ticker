package com.aydinpolat.bitcointicker.data.remote.model


import com.aydinpolat.bitcointicker.domain.model.CoinListItem
import com.google.firebase.firestore.DocumentSnapshot
import com.google.gson.annotations.SerializedName

data class CoinListItemDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("symbol")
    val symbol: String,
)

fun CoinListItemDto.toDomain(): CoinListItem {
    return CoinListItem(
        id = id,
        name = name,
        symbol = symbol
    )
}

fun DocumentSnapshot.toDomain(): CoinListItem? {
    val id = getString("id")
    val name = getString("name")
    val symbol = getString("symbol")
    val isFavorite = getBoolean("favorite")
    return if (id.isNullOrEmpty() || name.isNullOrEmpty() || symbol.isNullOrEmpty()) {
        null
    } else {
        isFavorite?.let { favorite -> CoinListItem(id, name, symbol, favorite) }
    }
}