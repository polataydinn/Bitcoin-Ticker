package com.aydinpolat.bitcointicker.domain.use_case.authentication.register

import com.aydinpolat.bitcointicker.domain.auth.Authenticator
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val authenticator: Authenticator
) {
    suspend operator fun invoke(email: String, password: String) =
        authenticator.register(email, password)
}