package com.aydinpolat.bitcointicker.presentation.fragment.coin_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aydinpolat.bitcointicker.common.Constants
import com.aydinpolat.bitcointicker.common.Resource
import com.aydinpolat.bitcointicker.domain.repository.FirebaseRepository
import com.aydinpolat.bitcointicker.domain.use_case.get_coins.GetCoinDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val coinDetailUseCase: GetCoinDetailUseCase,
    private val firebaseRepository: FirebaseRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _coinDetailState = MutableStateFlow(CoinDetailState())
    val coinDetailState: StateFlow<CoinDetailState> = _coinDetailState

    init {
        savedStateHandle.get<String>(Constants.COIN_ID)?.let { coinId ->
            getAgentDetail(coinId)
        }
    }

    private fun getAgentDetail(coinId: String) {
        coinDetailUseCase(coinId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _coinDetailState.value = CoinDetailState(coinDetail = result.data)
                }
                is Resource.Error -> {
                    _coinDetailState.value = CoinDetailState(
                        error = result.message ?: "An unexpected error occured."
                    )
                }
                is Resource.Loading -> {
                    _coinDetailState.value = CoinDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}