package com.aydinpolat.bitcointicker.presentation.fragment.login

data class LoginUiState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
)