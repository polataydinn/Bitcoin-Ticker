package com.aydinpolat.bitcointicker.data.repository

import androidx.lifecycle.LiveData
import com.aydinpolat.bitcointicker.data.local.CoinDao
import com.aydinpolat.bitcointicker.data.remote.CoinApi
import com.aydinpolat.bitcointicker.data.remote.model.CoinDetail
import com.aydinpolat.bitcointicker.data.remote.model.CoinList
import com.aydinpolat.bitcointicker.data.remote.model.CoinListItem
import com.aydinpolat.bitcointicker.domain.repository.CoinRepository
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

    override fun getSearchResult(searchQuery: String): LiveData<List<CoinListItem>> {
        return coinDao.getSearchResult("%$searchQuery%")
    }


    override fun getAllCoinsFromDb(): LiveData<List<CoinListItem>> {
        return coinDao.getAllCoins()
    }
}