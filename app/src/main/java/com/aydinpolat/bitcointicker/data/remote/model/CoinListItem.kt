package com.aydinpolat.bitcointicker.data.remote.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentSnapshot
import com.google.gson.annotations.SerializedName

@Entity(tableName = "coin_list")
data class CoinListItem(
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("symbol")
    val symbol: String,
    val isFavorite: Boolean = false
)

fun DocumentSnapshot.toCoinListItem(): CoinListItem? {
    val id = getString("id")
    val name = getString("name")
    val symbol = getString("symbol")
    val isFavorite = getBoolean("favorite")
    if (id.isNullOrEmpty() || name.isNullOrEmpty() || symbol.isNullOrEmpty()) {
        return null
    } else {
        return  isFavorite?.let { favorite -> CoinListItem(id, name, symbol, favorite) }
    }
}