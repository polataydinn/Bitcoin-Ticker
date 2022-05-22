package com.aydinpolat.bitcointicker.presentation.fragment.coin_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aydinpolat.bitcointicker.common.Constants
import com.aydinpolat.bitcointicker.common.Resource
import com.aydinpolat.bitcointicker.domain.model.CoinListItem
import com.aydinpolat.bitcointicker.domain.repository.FirebaseRepository
import com.aydinpolat.bitcointicker.domain.use_case.coins.GetCoinDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val coinDetailUseCase: GetCoinDetailUseCase,
    private val firebaseRepository: FirebaseRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _coinDetailState = MutableStateFlow(CoinDetailState())
    val coinDetailState: StateFlow<CoinDetailState> = _coinDetailState

    private val _isAddedToFavorite = MutableLiveData<Boolean>()
    val isAddedToFavorite get() = _isAddedToFavorite

    private val _isFavorite = MutableLiveData(false)
    val isFavorite get() = _isFavorite

    init {
        savedStateHandle.get<String>(Constants.COIN_ID)?.let {
            getAgentDetail(it)
            checkIfFavorite(it)
        }
    }

    private fun checkIfFavorite(coinId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            firebaseRepository.isCoinFavorite(coinId) {
                isFavorite.value = it
            }
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
                        error = result.message ?: "An unexpected error occurred."
                    )
                }
                is Resource.Loading -> {
                    _coinDetailState.value = CoinDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun setFavorite() {
        val coinDetail = coinDetailState.value.coinDetail
        if (coinDetail != null) {
            val coinListItem = CoinListItem(
                id = coinDetail.id,
                name = coinDetail.name,
                symbol = coinDetail.symbol,
                isFavorite = true
            )
            viewModelScope.launch {
                firebaseRepository.setFavoriteCoin(coinListItem) {
                    checkIfFavorite(coinDetail.id)
                    _isAddedToFavorite.value = it
                }
            }
        }
    }

    fun deleteFavorite() {
        val coinDetail = coinDetailState.value.coinDetail
        if (coinDetail != null) {
            viewModelScope.launch(Dispatchers.IO) {
                firebaseRepository.removeFavoriteCoin(coinDetail.id)
                checkIfFavorite(coinDetail.id)
            }
        }
    }
}