package com.aydinpolat.bitcointicker.presentation.fragment.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aydinpolat.bitcointicker.domain.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private val _favoriteCoinsState = MutableStateFlow(FavoriteCoinsState())
    val favoriteCoinsState: StateFlow<FavoriteCoinsState> = _favoriteCoinsState

    fun getAllFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            firebaseRepository.getFavoriteCoins() {
                if (it.isNotEmpty()) {
                    _favoriteCoinsState.value =
                        FavoriteCoinsState(favoriteCoins = it, isLoading = false)
                } else {
                    _favoriteCoinsState.value =
                        FavoriteCoinsState(isError = true, isLoading = false)
                }
            }
        }
    }
}