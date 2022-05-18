package com.aydinpolat.bitcointicker.data.remote

import com.aydinpolat.bitcointicker.data.remote.model.CoinDetail
import com.aydinpolat.bitcointicker.data.remote.model.CoinList
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinApi {
    @GET("coins/list")
    suspend fun getAllCoins(): CoinList

    @GET("coins/{id}")
    suspend fun getCoinByID(@Path("id") id: String): CoinDetail
}