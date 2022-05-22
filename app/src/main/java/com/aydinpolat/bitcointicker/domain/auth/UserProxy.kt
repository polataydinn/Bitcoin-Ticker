package com.aydinpolat.bitcointicker.domain.auth

import com.aydinpolat.bitcointicker.domain.model.AuthUser

interface UserProxy {
    fun getUser(): AuthUser?
}