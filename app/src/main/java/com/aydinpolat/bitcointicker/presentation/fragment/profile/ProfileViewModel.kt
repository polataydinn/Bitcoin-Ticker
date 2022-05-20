package com.aydinpolat.bitcointicker.presentation.fragment.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aydinpolat.bitcointicker.domain.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    fun logOut() {
        viewModelScope.launch(Dispatchers.IO) {
            firebaseRepository.logout()
        }
    }
}