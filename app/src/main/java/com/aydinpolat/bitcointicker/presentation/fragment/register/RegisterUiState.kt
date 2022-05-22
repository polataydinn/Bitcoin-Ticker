package com.aydinpolat.bitcointicker.presentation.fragment.register

data class RegisterUiState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
)
