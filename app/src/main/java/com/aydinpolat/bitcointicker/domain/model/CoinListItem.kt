package com.aydinpolat.bitcointicker.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coin_list")
data class CoinListItem(
    @PrimaryKey
    val id: String,
    val name: String,
    val symbol: String,
    val isFavorite: Boolean = false
)
