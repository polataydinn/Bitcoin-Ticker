package com.aydinpolat.bitcointicker.domain.auth

interface Authenticator {

    suspend fun register(email: String, password: String): RegisterResult

    suspend fun login(email: String, password: String): LoginResult

}