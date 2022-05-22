package com.aydinpolat.bitcointicker.domain.use_case.authentication

import android.util.Patterns
import javax.inject.Inject

class ValidateUserUseCase @Inject constructor() {

    operator fun invoke(email: String, password: String): ValidationUserResult {
        val isMailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        if (isMailValid.not()) {
            return ValidationUserResult.EMAIL_NOT_VALID
        }
        val isPasswordValid = password.isNotEmpty() && password.length >= 6
        if (isPasswordValid.not()) {
            return ValidationUserResult.PASSWORD_NOT_VALID
        }
        return ValidationUserResult.VALID
    }

    enum class ValidationUserResult {
        VALID,
        EMAIL_NOT_VALID,
        PASSWORD_NOT_VALID
    }
}