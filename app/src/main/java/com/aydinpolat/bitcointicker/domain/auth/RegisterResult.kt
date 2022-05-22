package com.aydinpolat.bitcointicker.domain.auth

import com.aydinpolat.bitcointicker.R

enum class RegisterResult(val messageResourceId: Int) {
    SUCCESS_REGISTER(R.string.auth_success_register),
    USER_ALREADY_EXISTS(R.string.auth_user_already_exists),
    FAILURE_REGISTER(R.string.auth_failure_register),
    EMAIL_INVALID(R.string.email_is_not_valid),
}