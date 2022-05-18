package com.aydinpolat.bitcointicker.presentation.fragment.coin_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.aydinpolat.bitcointicker.common.Resource
import com.aydinpolat.bitcointicker.data.remote.model.CoinListItem
import com.aydinpolat.bitcointicker.domain.repository.CoinRepository
import com.aydinpolat.bitcointicker.domain.use_case.get_coin.GetCoinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinUseCase: GetCoinUseCase,
    private val coinRepository: CoinRepository
) : ViewModel() {
    private val _coinListState = MutableStateFlow(CoinListState())
    val coinListState: StateFlow<CoinListState> = _coinListState

    private val _coinList: MutableLiveData<PagingData<CoinListItem>> =
        MutableLiveData()
    val coinList get() = _coinList

    init {
        getAllCoins()
        getCoins()
    }

    private fun getAllCoins() {
        getCoinUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _coinListState.value = CoinListState(isLoading = false)
                    result.data?.let { insertAllCoins(it) }
                }
                is Resource.Error -> {
                    _coinListState.value = CoinListState(
                        error = result.message ?: "An unexpected error occured."
                    )
                }
                is Resource.Loading -> {
                    _coinListState.value = CoinListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun insertAllCoins(coinList: List<CoinListItem>) {
        viewModelScope.launch(Dispatchers.IO) {
            coinRepository.insertAllCoins(coinList)
        }
    }

    private fun getCoins() {
        viewModelScope.launch {
            coinRepository.getCoinsPaged().cachedIn(viewModelScope).distinctUntilChanged()
                .collectLatest {
                    _coinList.value = it
                }
        }
    }

}