package com.aydinpolat.bitcointicker.presentation.fragment.register

import androidx.navigation.NavDirections

sealed class RegisterUiEvent {
    data class ShowError(val errorMessageResourceId: Int) : RegisterUiEvent()
    data class Navigate(val direction: NavDirections) : RegisterUiEvent()
}