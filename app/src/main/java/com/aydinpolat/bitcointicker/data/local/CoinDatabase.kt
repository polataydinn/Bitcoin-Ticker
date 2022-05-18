package com.aydinpolat.bitcointicker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aydinpolat.bitcointicker.data.remote.model.CoinListItem

@Database(entities = [CoinListItem::class], version = 1)
abstract class CoinDatabase : RoomDatabase() {
    abstract fun coinDao(): CoinDao
}