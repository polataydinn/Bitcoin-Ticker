package com.aydinpolat.bitcointicker.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aydinpolat.bitcointicker.data.remote.model.CoinListItem

@Dao
interface CoinDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCoins(coinList: List<CoinListItem>)

    @Query("SELECT * FROM coin_list WHERE name LIKE :searchQuery OR symbol LIKE :searchQuery")
    fun getSearchResult(searchQuery: String): LiveData<List<CoinListItem>>

    @Query("SELECT * FROM coin_list")
    fun getAllCoins(): LiveData<List<CoinListItem>>


}