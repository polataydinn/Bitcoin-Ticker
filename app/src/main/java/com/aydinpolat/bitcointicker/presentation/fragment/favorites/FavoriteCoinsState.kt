package com.aydinpolat.bitcointicker.presentation.fragment.favorites

import com.aydinpolat.bitcointicker.domain.model.CoinListItem

data class FavoriteCoinsState(
    val favoriteCoins: List<CoinListItem> = emptyList(),
    val isError: Boolean = false,
    val isLoading: Boolean = true
)
