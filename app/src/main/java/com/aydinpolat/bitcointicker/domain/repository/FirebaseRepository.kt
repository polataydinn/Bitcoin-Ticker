package com.aydinpolat.bitcointicker.domain.repository

import com.aydinpolat.bitcointicker.data.model.AuthenticationResult
import com.aydinpolat.bitcointicker.data.model.User

interface FirebaseRepository {
    suspend fun login(email: String, password: String, completeEvent: (AuthenticationResult) -> Unit)
    suspend fun signUp(user: User, completeEvent: (AuthenticationResult) -> Unit)
    suspend fun saveUserToFirestore(email: String)
}