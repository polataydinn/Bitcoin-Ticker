package com.aydinpolat.bitcointicker.presentation.fragment.favorites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aydinpolat.bitcointicker.data.remote.model.CoinListItem
import com.aydinpolat.bitcointicker.domain.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {
    private val _favoriteCoins = MutableLiveData<List<CoinListItem>>()
    val favoriteCoins get() = _favoriteCoins

    private val _isLoading = MutableLiveData(true)
    val isLoading get() = _isLoading

    private val _isError = MutableLiveData(false)
    val isError get() = _isError

    fun getAllFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            firebaseRepository.getFavoriteCoins() {
                isLoading.value = false
                if (it.isNotEmpty()) {
                    _favoriteCoins.value = it
                } else {
                    _isError.value = true
                }
            }
        }
    }
}