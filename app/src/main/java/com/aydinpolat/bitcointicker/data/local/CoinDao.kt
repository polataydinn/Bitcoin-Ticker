package com.aydinpolat.bitcointicker.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aydinpolat.bitcointicker.data.remote.model.CoinListItem

@Dao
interface CoinDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCoins(coinList: List<CoinListItem>)

    @Query("SELECT * FROM coin_list LIMIT :limit OFFSET :offset")
    suspend fun getCoinsPaged(offset: Int, limit: Int): List<CoinListItem>
}