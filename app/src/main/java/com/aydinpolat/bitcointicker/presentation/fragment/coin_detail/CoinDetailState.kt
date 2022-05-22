package com.aydinpolat.bitcointicker.presentation.fragment.coin_detail

import com.aydinpolat.bitcointicker.data.remote.model.CoinDetail

class CoinDetailState(
    val isLoading: Boolean? = false,
    val coinDetail: CoinDetail? = null,
    val error: String = ""
)