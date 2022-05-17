package com.aydinpolat.bitcointicker.presentation.fragment.register

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aydinpolat.bitcointicker.common.Constants
import com.aydinpolat.bitcointicker.data.model.AuthenticationResult
import com.aydinpolat.bitcointicker.data.model.User
import com.aydinpolat.bitcointicker.domain.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {
    private val _signUpResult = MutableLiveData<AuthenticationResult>()
    val signUpResult get() = _signUpResult

    private val _emailFormatResult = MutableLiveData<String>()
    val emailFormatResult get() = _emailFormatResult

    private val _passwordFormatResult = MutableLiveData<String>()
    val passwordFormatResult get() = _passwordFormatResult

    private val _loadingResult = MutableLiveData<Boolean>()
    val loadingResult get() = _loadingResult

    fun checkIfInputsAreValid(user: User) {
        if (!Patterns.EMAIL_ADDRESS.matcher(user.email).matches()) {
            _emailFormatResult.value = Constants.MAIl_ERROR
        } else {
            if (user.password.length < 8) {
                _passwordFormatResult.value = Constants.PASSWORD_MIN_LENGTH_ERROR
            } else {
                register(user)
                _loadingResult.value = true
            }
        }
    }

    private fun register(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            firebaseRepository.signUp(user) {
                _signUpResult.value = it
                if (it.isSuccessful) {
                    _loadingResult.value = false
                    saveUserToFirestore(user.email)
                }
            }
        }
    }

    private fun saveUserToFirestore(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            firebaseRepository.saveUserToFirestore(email)
        }
    }
}