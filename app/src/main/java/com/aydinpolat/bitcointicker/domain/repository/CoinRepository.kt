package com.aydinpolat.bitcointicker.domain.repository

import androidx.paging.PagingData
import com.aydinpolat.bitcointicker.data.remote.model.CoinDetail
import com.aydinpolat.bitcointicker.data.remote.model.CoinList
import com.aydinpolat.bitcointicker.data.remote.model.CoinListItem
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    suspend fun getAllCoins(): CoinList
    suspend fun getCoinById(id: String): CoinDetail
    suspend fun insertAllCoins(coinList: List<CoinListItem>)
    suspend fun getCoinsPaged(): Flow<PagingData<CoinListItem>>
}