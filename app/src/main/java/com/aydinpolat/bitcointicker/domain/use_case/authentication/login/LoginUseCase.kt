package com.aydinpolat.bitcointicker.domain.use_case.authentication.login

import com.aydinpolat.bitcointicker.domain.auth.Authenticator
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authenticator: Authenticator,
) {
    suspend operator fun invoke(email: String, password: String) =
        authenticator.login(email, password)
}