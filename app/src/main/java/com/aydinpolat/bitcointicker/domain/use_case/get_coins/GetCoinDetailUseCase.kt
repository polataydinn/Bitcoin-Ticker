package com.aydinpolat.bitcointicker.domain.use_case.get_coins

import com.aydinpolat.bitcointicker.common.Resource
import com.aydinpolat.bitcointicker.data.remote.model.CoinDetail
import com.aydinpolat.bitcointicker.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class GetCoinDetailUseCase @Inject constructor(
    private val coinRepository: CoinRepository
) {
    operator fun invoke(coinId: String): Flow<Resource<CoinDetail>> = flow {
        try {
            emit(Resource.Loading<CoinDetail>())
            val coinDetail = coinRepository.getCoinById(coinId)
            emit(Resource.Success<CoinDetail>(coinDetail))
        } catch (e: HttpException) {
            emit(
                Resource.Error<CoinDetail>(
                    e.localizedMessage ?: "An unexpected error occurred"
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error<CoinDetail>("Couldn't reach server check your internet connection"))
        }
    }
}