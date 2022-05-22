package com.aydinpolat.bitcointicker.presentation.fragment.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aydinpolat.bitcointicker.R
import com.aydinpolat.bitcointicker.common.SingleLiveEvent
import com.aydinpolat.bitcointicker.domain.auth.RegisterResult
import com.aydinpolat.bitcointicker.domain.use_case.authentication.ValidateUserUseCase
import com.aydinpolat.bitcointicker.domain.use_case.authentication.register.RegisterUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val validateUserUseCase: ValidateUserUseCase,
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    private val _uiEvent = SingleLiveEvent<RegisterUiEvent>()
    val uiEvent: LiveData<RegisterUiEvent> get() = _uiEvent

    private val _state = MutableStateFlow(RegisterUiState())
    val state = _state.asStateFlow()

    fun onEmailChanged(newEmail: String) {
        val oldState = _state.value
        if (oldState.email == newEmail) return
        _state.value = oldState.copy(email = newEmail)
    }

    fun onPasswordChanged(newPassword: String) {
        val oldState = _state.value
        if (oldState.password == newPassword) return
        _state.value = oldState.copy(password = newPassword)
    }

    fun onRegisterClicked() {
        _state.value = _state.value.copy(isLoading = true)
        val state = _state.value
        when (validateUserUseCase(state.email, state.password)) {
            ValidateUserUseCase.ValidationUserResult.VALID -> {
                registerUser(state.email, state.password)
            }
            ValidateUserUseCase.ValidationUserResult.EMAIL_NOT_VALID -> {
                _uiEvent.value =
                    RegisterUiEvent.ShowError(R.string.email_is_not_valid)
                _state.value = _state.value.copy(isLoading = false)
            }
            ValidateUserUseCase.ValidationUserResult.PASSWORD_NOT_VALID -> {
                _uiEvent.value =
                    RegisterUiEvent.ShowError(R.string.password_is_not_valid)
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }

    private fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            val registerResult = registerUserUseCase(email, password)
            _state.value = _state.value.copy(isLoading = false)
            when (registerResult) {
                RegisterResult.SUCCESS_REGISTER -> {
                    _uiEvent.value =
                        RegisterUiEvent.Navigate(RegisterFragmentDirections.actionRegisterFragmentToCoinListFragment())
                }
                else -> {
                    _uiEvent.value = RegisterUiEvent.ShowError(registerResult.messageResourceId)
                }
            }
        }
    }

    fun onSignInClicked() {
        _uiEvent.value =
            RegisterUiEvent.Navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
    }
}