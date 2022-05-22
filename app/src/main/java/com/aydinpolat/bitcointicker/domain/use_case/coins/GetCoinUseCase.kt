package com.aydinpolat.bitcointicker.domain.use_case.coins

import com.aydinpolat.bitcointicker.common.Resource
import com.aydinpolat.bitcointicker.data.remote.model.toDomain
import com.aydinpolat.bitcointicker.domain.model.CoinListItem
import com.aydinpolat.bitcointicker.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(
    private val coinRepository: CoinRepository
) {
    operator fun invoke(): Flow<Resource<List<CoinListItem>>> = flow {
        try {
            emit(Resource.Loading<List<CoinListItem>>())
            val coinList: MutableList<CoinListItem> = mutableListOf()
            coinList.addAll(coinRepository.getAllCoins().toList().map { it.toDomain() })
            emit(Resource.Success<List<CoinListItem>>(coinList))
        } catch (e: HttpException) {
            emit(
                Resource.Error<List<CoinListItem>>(
                    e.localizedMessage ?: "An unexpected error occurred"
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error<List<CoinListItem>>("Couldn't reach server check your internet connection"))
        }
    }
}