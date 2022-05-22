package com.aydinpolat.bitcointicker.domain.repository

import com.aydinpolat.bitcointicker.domain.model.CoinListItem

interface FirebaseRepository {
    suspend fun setFavoriteCoin(coinDetail: CoinListItem, completeEvent: (Boolean) -> Unit)
    suspend fun getFavoriteCoins(favoriteCoins: (List<CoinListItem>) -> Unit)
    suspend fun removeFavoriteCoin(id: String)
    suspend fun isCoinFavorite(id: String, checkResult: (Boolean) -> Unit)
    suspend fun logout()
}