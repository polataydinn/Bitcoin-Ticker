package com.aydinpolat.bitcointicker.domain.repository

import androidx.lifecycle.LiveData
import com.aydinpolat.bitcointicker.data.remote.model.CoinDetail
import com.aydinpolat.bitcointicker.data.remote.model.CoinList
import com.aydinpolat.bitcointicker.data.remote.model.CoinListItemDto
import com.aydinpolat.bitcointicker.domain.model.CoinListItem

interface CoinRepository {
    suspend fun getAllCoins(): CoinList
    suspend fun getCoinById(id: String): CoinDetail
    suspend fun insertAllCoins(coinList: List<CoinListItem>)
    fun getSearchResult(searchQuery: String): LiveData<List<CoinListItem>>
    fun getAllCoinsFromDb(): LiveData<List<CoinListItem>>
}