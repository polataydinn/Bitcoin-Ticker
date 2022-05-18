package com.aydinpolat.bitcointicker.presentation.fragment.coin_list

import com.aydinpolat.bitcointicker.data.remote.model.CoinListItem

data class CoinListState(
    val isLoading: Boolean? = false,
    val coins: List<CoinListItem>? = emptyList(),
    val error: String = ""
)
