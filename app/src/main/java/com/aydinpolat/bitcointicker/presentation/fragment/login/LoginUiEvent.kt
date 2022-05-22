package com.aydinpolat.bitcointicker.presentation.fragment.login

import androidx.navigation.NavDirections

sealed class SignInUiEvent {
    data class ShowError(val errorMessageResourceId: Int) : SignInUiEvent()
    data class Navigate(val direction: NavDirections) : SignInUiEvent()
}
