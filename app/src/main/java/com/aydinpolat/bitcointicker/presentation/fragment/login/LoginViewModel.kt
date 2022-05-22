package com.aydinpolat.bitcointicker.presentation.fragment.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aydinpolat.bitcointicker.R
import com.aydinpolat.bitcointicker.common.SingleLiveEvent
import com.aydinpolat.bitcointicker.domain.auth.LoginResult
import com.aydinpolat.bitcointicker.domain.use_case.authentication.ValidateUserUseCase
import com.aydinpolat.bitcointicker.domain.use_case.authentication.login.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateUserUseCase: ValidateUserUseCase,
    private val loginUseCase: LoginUseCase,
) : ViewModel() {
    private val _uiEvent = SingleLiveEvent<SignInUiEvent>()
    val uiEvent: LiveData<SignInUiEvent> get() = _uiEvent

    private val _state = MutableStateFlow(LoginUiState())
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

    fun onLoginClicked() {
        _state.value = _state.value.copy(isLoading = true)
        val state = _state.value
        when (validateUserUseCase(state.email, state.password)) {
            ValidateUserUseCase.ValidationUserResult.VALID -> {
                signInUser(state.email, state.password)
            }
            ValidateUserUseCase.ValidationUserResult.EMAIL_NOT_VALID -> {
                _uiEvent.value =
                    SignInUiEvent.ShowError(R.string.email_is_not_valid)
                _state.value = _state.value.copy(isLoading = false)
            }
            ValidateUserUseCase.ValidationUserResult.PASSWORD_NOT_VALID -> {
                _uiEvent.value =
                    SignInUiEvent.ShowError(R.string.password_is_not_valid)
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }

    private fun signInUser(email: String, password: String) {
        viewModelScope.launch {
            val loginResult = loginUseCase(email, password)
            _state.value = _state.value.copy(isLoading = false)
            when (loginResult) {
                LoginResult.SUCCESS_SIGN_IN -> {
                    _uiEvent.value =
                        SignInUiEvent.Navigate(LoginFragmentDirections.actionLoginFragmentToCoinListFragment())
                }
                else -> {
                    _uiEvent.value = SignInUiEvent.ShowError(loginResult.messageResourceId)
                }
            }
        }
    }

    fun onRegisterClicked() {
        _uiEvent.value =
            SignInUiEvent.Navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
    }
}