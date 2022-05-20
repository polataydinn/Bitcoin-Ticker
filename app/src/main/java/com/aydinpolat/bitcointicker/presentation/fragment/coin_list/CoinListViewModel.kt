package com.aydinpolat.bitcointicker.presentation.fragment.coin_list

import androidx.lifecycle.*
import com.aydinpolat.bitcointicker.common.Resource
import com.aydinpolat.bitcointicker.data.remote.model.CoinListItem
import com.aydinpolat.bitcointicker.domain.repository.CoinRepository
import com.aydinpolat.bitcointicker.domain.repository.FirebaseRepository
import com.aydinpolat.bitcointicker.domain.use_case.get_coin.GetCoinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinUseCase: GetCoinUseCase,
    private val coinRepository: CoinRepository,
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private val _coinListState = MutableStateFlow(CoinListState())
    val coinListState: StateFlow<CoinListState> = _coinListState

    val coinList = coinRepository.getAllCoinsFromDb()

    private val _searchQuery = MutableLiveData<String>()

    private val _searchResult = _searchQuery.switchMap {
        coinRepository.getSearchResult(it)
    }

    val searchResult: LiveData<List<CoinListItem>> = _searchResult

    private val _userName = MutableLiveData<String>()
    val userName get() = _userName

    init {
        getAllCoins()
        getUserName()
    }

    private fun getUserName() {
        viewModelScope.launch(Dispatchers.IO) {
            firebaseRepository.getUserName {
                userName.value = it.fullName.substringBefore(" ")
            }
        }
    }

    private fun getAllCoins() {
        getCoinUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _coinListState.value = CoinListState(isLoading = false)
                    result.data?.let { coinRepository.insertAllCoins(it) }
                }
                is Resource.Error -> {
                    if (coinList.value.isNullOrEmpty()) {
                        _coinListState.value = CoinListState(
                            error = result.message ?: "An unexpected error occurred."
                        )
                    }
                }
                is Resource.Loading -> {
                    if (coinList.value.isNullOrEmpty()) {
                        _coinListState.value = CoinListState(isLoading = true)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }


    fun setSearchQuery(searchQuery: String) {
        _searchQuery.value = searchQuery
    }
}