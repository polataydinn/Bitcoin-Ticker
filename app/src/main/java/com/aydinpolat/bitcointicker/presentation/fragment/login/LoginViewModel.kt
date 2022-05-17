package com.aydinpolat.bitcointicker.presentation.fragment.login

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aydinpolat.bitcointicker.common.Constants
import com.aydinpolat.bitcointicker.data.model.AuthenticationResult
import com.aydinpolat.bitcointicker.domain.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {
    private val _loginResult = MutableLiveData<AuthenticationResult>()
    val loginResult get() = _loginResult

    private val _emailFormatResult = MutableLiveData<String>()
    val emailFormatResult get() = _emailFormatResult

    private val _passwordFormatResult = MutableLiveData<String>()
    val passwordFormatResult get() = _passwordFormatResult

    private val _loadingResult = MutableLiveData<Boolean>()
    val loadingResult get() = _loadingResult

    fun checkIfInputsAreValid(email: String, password: String) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailFormatResult.value = Constants.MAIl_ERROR
        } else {
            if (password.length < 8) {
                _passwordFormatResult.value = Constants.PASSWORD_MIN_LENGTH_ERROR
            } else {
                loadingResult.value = true
                login(email, password)
            }
        }
    }

    private fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            firebaseRepository.login(email, password) {
                loadingResult.value = false
                _loginResult.value = it
            }
        }
    }
}