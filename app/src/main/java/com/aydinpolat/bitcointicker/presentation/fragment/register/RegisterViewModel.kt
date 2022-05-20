package com.aydinpolat.bitcointicker.presentation.fragment.register

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aydinpolat.bitcointicker.common.Constants
import com.aydinpolat.bitcointicker.data.remote.model.AuthenticationResult
import com.aydinpolat.bitcointicker.data.remote.model.UserDto
import com.aydinpolat.bitcointicker.data.remote.model.toTrimmedUser
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

    fun checkIfInputsAreValid(userDto: UserDto) {
        if (!Patterns.EMAIL_ADDRESS.matcher(userDto.email).matches()) {
            _emailFormatResult.value = Constants.MAIl_ERROR
        } else {
            if (userDto.password.length < 8) {
                _passwordFormatResult.value = Constants.PASSWORD_MIN_LENGTH_ERROR
            } else {
                register(userDto)
                _loadingResult.value = true
            }
        }
    }

    private fun register(userDto: UserDto) {
        viewModelScope.launch(Dispatchers.IO) {
            firebaseRepository.signUp(userDto) {
                _signUpResult.value = it
                if (it.isSuccessful) {
                    _loadingResult.value = false
                    saveUserToFirestore(userDto)
                }
            }
        }
    }

    private fun saveUserToFirestore(userDto: UserDto) {
        viewModelScope.launch(Dispatchers.IO) {
            firebaseRepository.saveUserToFirestore(userDto.toTrimmedUser())
        }
    }
}