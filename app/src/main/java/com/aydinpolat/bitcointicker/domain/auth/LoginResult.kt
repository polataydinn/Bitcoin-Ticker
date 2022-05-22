package com.aydinpolat.bitcointicker.domain.auth

import com.aydinpolat.bitcointicker.R

enum class LoginResult(val messageResourceId: Int) {
    SUCCESS_SIGN_IN(R.string.auth_success_login),
    WRONG_PASSWORD(R.string.auth_wrong_password),
    USER_NOT_FOUND(R.string.auth_user_not_found),
    SIGN_IN_FAILED(R.string.auth_login_failed),
}