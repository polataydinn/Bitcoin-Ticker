package com.aydinpolat.bitcointicker.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.aydinpolat.bitcointicker.data.local.CoinDao
import com.aydinpolat.bitcointicker.data.remote.CoinApi
import com.aydinpolat.bitcointicker.data.remote.model.CoinDetail
import com.aydinpolat.bitcointicker.data.remote.model.CoinList
import com.aydinpolat.bitcointicker.data.remote.model.CoinListItem
import com.aydinpolat.bitcointicker.domain.repository.CoinRepository
import com.aydinpolat.bitcointicker.domain.use_case.get_paged_coins.GetPagedCoinsSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CoinRepositoryImplementation @Inject constructor(
    private val coinApi: CoinApi,
    private val coinDao: CoinDao
) : CoinRepository {
    override suspend fun getAllCoins(): CoinList {
        return coinApi.getAllCoins()
    }

    override suspend fun getCoinById(id: String): CoinDetail {
        return coinApi.getCoinByID(id)
    }

    override suspend fun insertAllCoins(coinList: List<CoinListItem>) {
        coinDao.insertAllCoins(coinList)
    }

    override suspend fun getCoinsPaged(): Flow<PagingData<CoinListItem>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 8, prefetchDistance = 8),
            pagingSourceFactory = {
                GetPagedCoinsSource(coinDao)
            }
        ).flow
    }
}